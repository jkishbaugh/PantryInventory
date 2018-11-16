package com.example.jkishbaugh.pantryinventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jkishbaugh.pantryinventory.data.PantryContract.PantryEntry;
import com.example.jkishbaugh.pantryinventory.data.PantryDbHelper;


public class CatalogActivity extends AppCompatActivity {
    private PantryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);


            }
        });


        dbHelper = new PantryDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        dbHelper = new PantryDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PantryEntry._ID,
                PantryEntry.COLUMN_PRODUCT_NAME,
                PantryEntry.COLUMN_PRICE,
                PantryEntry.COLUMN_QUANTITY,
                PantryEntry.COLUMN_SUPPLIER_NAME,
                PantryEntry.COLUMN_SUPPLIER_PHONE
        };
        Cursor cursor = db.query(PantryEntry.TABLE_NAME, projection, null, null,
                null, null, null);
        TextView displayView = (TextView) findViewById(R.id.text_view_item);

        try {

            displayView.setText("The pantry table contains " + cursor.getCount() + " items.\n");
            displayView.append(PantryEntry._ID + " - " +
                    PantryEntry.COLUMN_PRODUCT_NAME + "\n");


            int idColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(PantryEntry.COLUMN_SUPPLIER_PHONE);


                while (cursor.moveToNext()) {

                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    String currentPrice = cursor.getString(priceColumnIndex);
                    String currentQuantity = cursor.getString(quantityColumnIndex);
                    String currentSupplier = cursor.getString(supplierColumnIndex);
                    String currentSupplierNumber = cursor.getString(supplierPhoneColumnIndex);

                    displayView.append(("\n" + currentID + " - " +
                            currentName + "-" + currentPrice + "-" + currentQuantity + "-" + currentSupplier + "-" + currentSupplierNumber));
                }

            } finally{
                cursor.close();
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert_data:
                insertItem();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

  private void insertItem(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(PantryEntry.COLUMN_PRODUCT_NAME, "tomato sauce");
        values.put(PantryEntry.COLUMN_PRICE, "$0.75");
        values.put(PantryEntry.COLUMN_QUANTITY, "3");
        values.put(PantryEntry.COLUMN_SUPPLIER_NAME, 1);
        values.put(PantryEntry.COLUMN_SUPPLIER_PHONE, "(435)255-4444");

        long newRowId = db.insert(PantryEntry.TABLE_NAME, null, values);

        displayDatabaseInfo();
    }
}