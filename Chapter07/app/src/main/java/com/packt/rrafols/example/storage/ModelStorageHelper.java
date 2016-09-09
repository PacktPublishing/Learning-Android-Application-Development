package com.packt.rrafols.example.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.packt.rrafols.example.model.Fields;
import com.packt.rrafols.example.model.Model;
import com.packt.rrafols.example.model.Resource;
import com.packt.rrafols.example.model.ResourceWrapper;

import java.util.LinkedList;
import java.util.List;


public class ModelStorageHelper extends SQLiteOpenHelper implements ModelStorage {
    private static final String TAG = ModelStorageHelper.class.getSimpleName();

    public ModelStorageHelper(Context context) {
        super(context, ModelContract.DB_NAME, null, ModelContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ModelContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public List<Fields> retrieveFieldList() {
        String[] projection = {
                ModelContract.ModelContractElement._ID,
                ModelContract.ModelContractElement.COLUMN_NAME_NAME,
                ModelContract.ModelContractElement.COLUMN_NAME_PRICE,
                ModelContract.ModelContractElement.COLUMN_NAME_SYMBOL,
                ModelContract.ModelContractElement.COLUMN_NAME_TS,
                ModelContract.ModelContractElement.COLUMN_NAME_TYPE,
                ModelContract.ModelContractElement.COLUMN_NAME_UTCTIME,
                ModelContract.ModelContractElement.COLUMN_NAME_VOLUME
        };

        String sortOrder = ModelContract.ModelContractElement._ID + " DESC";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ModelContract.ModelContractElement.TABLE_NAME,
                                 projection,
                                 null,
                                 null,
                                 null,
                                 null,
                                 sortOrder);

        LinkedList<Fields> fieldList = new LinkedList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Fields field = new Fields();
            field.setName(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_NAME)));
            field.setPrice(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_PRICE)));
            field.setSymbol(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_SYMBOL)));
            field.setTs(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_TS)));
            field.setType(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_TYPE)));
            field.setUtctime(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_UTCTIME)));
            field.setVolume(cursor.getString(
                    cursor.getColumnIndex(ModelContract.ModelContractElement.COLUMN_NAME_VOLUME)));

            fieldList.add(field);
            cursor.moveToNext();
        }
        cursor.close();

        return fieldList;
    }

    public void storeModel(Model model) {
        SQLiteDatabase db = getWritableDatabase();

        for(ResourceWrapper resourceWrapper : model.getList().getResources()) {
            Resource res = resourceWrapper.getResource();
            Fields fields = res.getFields();

            ContentValues values = new ContentValues();
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_NAME, fields.getName());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_PRICE, fields.getPrice());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_SYMBOL, fields.getSymbol());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_TS, fields.getTs());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_TYPE, fields.getType());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_UTCTIME, fields.getUtctime());
            values.put(ModelContract.ModelContractElement.COLUMN_NAME_VOLUME, fields.getVolume());

            long newRowId = db.insert(ModelContract.ModelContractElement.TABLE_NAME, null, values);
            Log.d(TAG, "storing rowid: " + newRowId);
        }
    }
}
