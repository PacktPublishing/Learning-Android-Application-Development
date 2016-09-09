package com.packt.rrafols.example;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import com.android.volley.Cache;
import com.android.volley.toolbox.DiskBasedCache;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImageCache {
    private static final String TAG = ImageCache.class.getName();
    private static final String DEFAULT_CACHE_DIR = "imagecache";
    private LruCache<String, Bitmap> bitmapCache;
    private DiskBasedCache diskCache;
    private final Object diskCacheLock = new Object();
    private boolean diskCacheInitialized;
    private Set<SoftReference<Bitmap>> reusableBitmaps;


    public ImageCache(Context context) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            reusableBitmaps = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        }

        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

            }
        };

        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
        diskCache = new DiskBasedCache(cacheDir);


        diskCacheInitialized = false;
        new InitializeDiskCache().execute();
    }


    class InitializeDiskCache extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            synchronized (diskCacheLock) {
                diskCache.initialize();
                diskCacheInitialized = true;
                diskCacheLock.notifyAll();
            }
            return null;
        }
    }

    class BitmapLoader extends AsyncTask<Void, Void, Bitmap> {
        private String key;
        private ImageView iv;
        private CacheNotifier notifier;

        BitmapLoader(String key, ImageView iv, CacheNotifier notifier) {
            this.key = key;
            this.iv = iv;
            this.notifier = notifier;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return getBitmapFromDisk(key);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                notifier.cacheHit(key);
                iv.setImageBitmap(bitmap);
            } else {
                notifier.cacheMiss(key);
            }
        }
    }

    class BitmapStorer extends AsyncTask<Void, Void, Void> {
        private String key;
        private Bitmap bitmap;

        BitmapStorer(String key, Bitmap bitmap) {
            this.key = key;
            this.bitmap = bitmap;
        }

        @Override
        protected Void doInBackground(Void... params) {
            synchronized (diskCacheLock) {
                diskCache.put(key, new BitmapCacheEntry(bitmap));
            }
            return null;
        }
    }

    @Nullable
    private Bitmap getBitmapFromDisk(String key) {
        synchronized (diskCacheLock) {
            while (!diskCacheInitialized) {
                try {
                    diskCacheLock.wait();
                } catch (InterruptedException e) {}
            }

            BitmapCacheEntry cacheEntry = (BitmapCacheEntry) diskCache.get(key);
            if(cacheEntry != null) {
                return cacheEntry.getBitmap();
            }
            return null;
        }
    }


    class BitmapCacheEntry extends Cache.Entry {
        public BitmapCacheEntry(Bitmap bitmap) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
            data = baos.toByteArray();
        }

        public Bitmap getBitmap() {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
    }



    public void putImage(String key, Bitmap bitmap) {
        bitmapCache.put(key, bitmap);
        new BitmapStorer(key, bitmap).execute();
    }

    public void getImage(String key, ImageView iv, CacheNotifier notifier) {
        final Bitmap bitmap = bitmapCache.get(key);
        if (bitmap != null) {
            notifier.cacheHit(key);
            iv.setImageBitmap(bitmap);
        } else {
            new BitmapLoader(key, iv, notifier).execute();
        }
    }

    public interface CacheNotifier {
        void cacheMiss(String key);
        void cacheHit(String key);
    }
}
