package com.packt.rrafols.example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.packt.rrafols.example.model.Model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class MainAdapterActivity extends AppCompatActivity {
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; MotoE2(4G-LTE)" +
            " Build/MPI24.65-39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36";
    private static final String TAG = MainAdapterActivity.class.getName();
    private static final int PERMISSION_REQUEST_INTERNET = 1;
    private static final int PERMISSION_READ_CONTACTS = 2;

    private final String[] fruits = new String[] {"a","b","c","d"};

    private int index;
    private ExampleBaseAdapter adapter;
    private ImageView imageViewLeft;
    private ImageView imageViewRight;

    private RequestQueue requestQueue;
    private HashMap<String, ImageView> imageViewByUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new ExampleBaseAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        findViewById(R.id.addline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < fruits.length) {
                    adapter.addItem(fruits[index++]);
                }
            }
        });

        findViewById(R.id.delline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount() > 0) {
                    int i = (int) (Math.random() * adapter.getItemCount());
                    adapter.removeItem(i);
                }
            }
        });

        imageViewLeft = (ImageView) findViewById(R.id.iv1);
        imageViewRight = (ImageView) findViewById(R.id.iv2);

        imageViewByUrl = new HashMap<>();
        requestQueue = Volley.newRequestQueue(this);
    }

    private void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted: " + permission);
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Log.i(TAG, "Show rationale " + permission);
            } else {
                Log.i(TAG, "No rationale required, requesting permission directly " + permission);
                ActivityCompat.requestPermissions(this, new String[] {permission}, requestCode);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        /// permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Read contacts permission granted", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Explain why we need this permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSION_READ_CONTACTS);
            }
        }


        /// retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://finance.yahoo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YahooFinanceService symbols = retrofit.create(YahooFinanceService.class);

        Call<Model> symbolsCall = symbols.getQuote("YHOO,GOOG,AAPL");
        symbolsCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                if(response.isSuccessful()) {

                    Model model = response.body();
                    //..

                } else {
                    Toast.makeText(MainAdapterActivity.this, "Request not successful: " +
                            response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(MainAdapterActivity.this, "Error processing request: " +
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void yahooFinanceAPIRequest() {
        String url = "http://finance.yahoo.com/webservice/v1/symbols/YHOO,GOOG,AAPL/quote?format=json";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainAdapterActivity.this, "Error processing json: " +
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /**
             * Yahoo Finance API recently changed and will not work if not using a mobile user-agent
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-agent", MOBILE_USER_AGENT);
                return headers;
            }
        };

        requestQueue.add(request);
    }

    private void yahooFinanceAPIGSONRequest() {
        String url = "http://finance.yahoo.com/webservice/v1/symbols/YHOO,GOOG,AAPL/quote?format=json";

        GsonRequest<Model> request = new GsonRequest<Model>(url, Model.class, null,
                new Response.Listener<Model>() {
                    @Override
                    public void onResponse(Model response) {
                        Log.i(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainAdapterActivity.this, "Error processing json: " +
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /**
             * Yahoo Finance API recently changed and will not work if not using a mobile user-agent
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-agent", MOBILE_USER_AGENT);
                return headers;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_READ_CONTACTS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                Log.e(TAG, "Wrong permission request code: " + requestCode);
        }
    }

    private void loadImage_thread(final ImageView imageView, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap b = loadImageFromNetwork(url);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(b);
                    }
                });
            }
        });
    }

    private void loadImage_asynctask(final ImageView imageView, final String url) {
        new ImageLoader(imageView).execute(url);
    }

    private void loadImage_asynctaskpool(final ImageView imageView, final String url) {
        new ImageLoader(imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    private void loadImage_intentservice(final ImageView imageView, final String url) {
        imageViewByUrl.put(url, imageView);
        Intent intent = new Intent(this, DownloadService.class);
        intent.setData(Uri.parse(url));
        intent.putExtra(DownloadService.PARAM_RECEIVER, imageReceiver);
        startService(intent);
    }

    private void loadImage_volley(final ImageView imageView, final String url) {
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(R.drawable.placeholder);
                    }
                });

        requestQueue.add(request);
    }

    private static class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        private ImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            imageView.setImageResource(R.drawable.placeholder);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImageFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageView != null) imageView.setImageBitmap(bitmap);
        }
    }

    private DownloadReceiver imageReceiver = new DownloadReceiver(null) {
        @Override
        public void success(String url, byte[] data) {
            final ImageView iv = imageViewByUrl.get(url);
            if(iv != null) {
                final Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                if(bm != null) {
                    iv.post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bm);
                        }
                    });
                }
                imageViewByUrl.remove(url);
            }
        }

        @Override
        public void failure(String url) {
            imageViewByUrl.remove(url);

            Toast.makeText(MainAdapterActivity.this, "Error loading image: " + url,
                    Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        imageViewByUrl.clear();

        super.onDestroy();
    }

    private static Bitmap loadImageFromNetwork(String url) {
        byte[] data = NetworkUtils.loadDataFromUrl(url);
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }
}