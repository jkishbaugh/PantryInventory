/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jkishbaugh.pantryinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jkishbaugh.pantryinventory.data.PantryContract.PantryEntry;
import com.example.jkishbaugh.pantryinventory.data.PantryDbHelper;


public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private Spinner mSupplierSpinner;
    private int mSupplier = PantryEntry.SUPPLIER_ONE;
    private String mSupplierPhoneNumber;
    private PantryDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mSupplierSpinner = (Spinner) findViewById(R.id.supplier_name);

        setupSpinner();
    }


    private void setupSpinner() {
        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_options, android.R.layout.simple_spinner_item);

        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSupplierSpinner.setAdapter(supplierSpinnerAdapter);

        mSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_one))) {
                        mSupplier = PantryEntry.SUPPLIER_ONE;
                        mSupplierPhoneNumber= PantryEntry.SUPPLIER_ONE_PHONE;
                    } else if (selection.equals(getString(R.string.supplier_two))) {
                        mSupplier = PantryEntry.SUPPLIER_TWO;
                        mSupplierPhoneNumber = PantryEntry.SUPPLIER_TWO_PHONE;
                    }else if(selection.equals(getString(R.string.supplier_three))){
                        mSupplier = PantryEntry.SUPPLIER_THREE;
                        mSupplierPhoneNumber = PantryEntry.SUPPLIER_THREE_PHONE;
                    }
                    else {
                        mSupplier = PantryEntry.SUPPLIER_OTHER;
                        mSupplierPhoneNumber = PantryEntry.SUPPLIER_UNKNOWN_PHONE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplier = PantryEntry.SUPPLIER_OTHER;
                mSupplierPhoneNumber = PantryEntry.SUPPLIER_UNKNOWN_PHONE;
            }
        });
    }
    private void insertItem(){
        dbHelper= new PantryDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        int quantity = Integer.parseInt(mQuantityEditText.getText().toString());
        int supplier = mSupplier;
        String phone = mSupplierPhoneNumber;


        ContentValues values = new ContentValues();
        values.put(PantryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(PantryEntry.COLUMN_PRICE, priceString);
        values.put(PantryEntry.COLUMN_QUANTITY, quantity);
        values.put(PantryEntry.COLUMN_SUPPLIER_NAME, supplier);
        values.put(PantryEntry.COLUMN_SUPPLIER_PHONE, phone);

        Log.e("DEBUG", "insertItem: "+ values);

        long newRowId = db.insert(PantryEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving item", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Item added with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
              insertItem();
              finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}