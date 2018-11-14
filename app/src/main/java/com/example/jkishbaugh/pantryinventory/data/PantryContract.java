package com.example.jkishbaugh.pantryinventory.data;

import android.provider.BaseColumns;

public final class PantryContract {

    public static final class PantryEntry implements BaseColumns{
        public static final String TABLE_NAME= "pantry";
        public static final String COLUMN_ID="_ID";
        public static final String COLUMN_PRODUCT_NAME="Name";
        public static final String COLUMN_PRICE="Price";
        public static final String COLUMN_QUANTITY="Quantity";
        public static final String COLUMN_SUPPLIER_NAME="Supplier";
        public static final String COLUMN_SUPPLIER_PHONE="Number";


        public static final int SUPPLIER_ONE = 1;
        public static final int SUPPLIER_TWO = 2;
        public static final int SUPPLIER_THREE =3;
        public static final int SUPPLIER_OTHER=0;

        public static final String SUPPLIER_ONE_PHONE = "435-555-7575";
        public static final String SUPPLIER_TWO_PHONE = "555-555-5578";
        public static final String SUPPLIER_THREE_PHONE = "801-578-8956";
        public static final String SUPPLIER_UNKNOWN_PHONE ="Unknown";
    }
}
