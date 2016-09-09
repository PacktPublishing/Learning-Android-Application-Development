package com.packt.rrafols.example.storage;

import android.provider.BaseColumns;

public final class ModelContract {
    public static final String DB_NAME = "model.db";
    public static final int DB_VERSION = 1;

    private ModelContract() {}

    public static abstract class ModelContractElement implements BaseColumns {
        public static final String TABLE_NAME = "element";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SYMBOL = "symbol";
        public static final String COLUMN_NAME_TS = "ts";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_UTCTIME = "time";
        public static final String COLUMN_NAME_VOLUME = "volume";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ModelContractElement.TABLE_NAME + " (" +
                    ModelContractElement._ID + " INTEGER PRIMARY KEY," +
                    ModelContractElement.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_SYMBOL + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_TS + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_UTCTIME + TEXT_TYPE + COMMA_SEP +
                    ModelContractElement.COLUMN_NAME_VOLUME + TEXT_TYPE +
                    " )";
}




