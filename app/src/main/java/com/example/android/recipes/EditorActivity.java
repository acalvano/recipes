package com.example.android.recipes;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.recipes.data.RecipeContract.RecipeEntry;

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_RECIPE_LOADER = 0;

    private Uri mCurrentRecipeUri;

    private EditText mNameEditText;

    private EditText mServingsEditText;

    private EditText mYieldEditText;

    private EditText mInstructionsEditText;

    private Spinner mCategorySpinner;

    private Spinner mUnitSpinner;

    private int mCategory = RecipeEntry.CATEGORY_UNKNOWN;

    private int mUnit = RecipeEntry.UNIT_UNKNOWN;

    private boolean mRecipeHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mRecipeHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentRecipeUri = intent.getData();

        if (mCurrentRecipeUri == null) {

            setTitle(getString(R.string.editor_activity_title_new_recipe));
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_activity_title_edit_recipe));

            getLoaderManager().initLoader(EXISTING_RECIPE_LOADER, null, this);
        }

        mNameEditText = (EditText) findViewById(R.id.edit_recipe_name);
        mServingsEditText = (EditText) findViewById(R.id.edit_recipe_servings);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mUnitSpinner = (Spinner) findViewById(R.id.spinner_unit);
        mYieldEditText = (EditText) findViewById(R.id.edit_recipe_yield);
        mInstructionsEditText = (EditText) findViewById(R.id.edit_recipe_instructions);


        mNameEditText.setOnTouchListener(mTouchListener);
        mServingsEditText.setOnTouchListener(mTouchListener);
        mCategorySpinner.setOnTouchListener(mTouchListener);
        mUnitSpinner.setOnTouchListener(mTouchListener);
        mYieldEditText.setOnTouchListener(mTouchListener);
        mInstructionsEditText.setOnTouchListener(mTouchListener);


        setupCategorySpinner();
        setupUnitSpinner();

    }

    private void setupCategorySpinner() {

        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mCategorySpinner.setAdapter(categorySpinnerAdapter);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category_soups))) {
                        mCategory = RecipeEntry.CATEGORY_SOUPS;
                    } else if (selection.equals(getString(R.string.category_sauces))) {
                        mCategory = RecipeEntry.CATEGORY_SAUCES;
                    } else if (selection.equals(getString(R.string.category_eggs))) {
                        mCategory = RecipeEntry.CATEGORY_EGGS;
                    } else if (selection.equals(getString(R.string.category_entrees))) {
                        mCategory = RecipeEntry.CATEGORY_ENTREES;
                    } else if (selection.equals(getString(R.string.category_lunch))) {
                        mCategory = RecipeEntry.CATEGORY_LUNCH;
                    } else if (selection.equals(getString(R.string.category_fish))) {
                        mCategory = RecipeEntry.CATEGORY_FISH;
                    } else if (selection.equals(getString(R.string.category_poultry))) {
                        mCategory = RecipeEntry.CATEGORY_POULTRY;
                    } else if (selection.equals(getString(R.string.category_meat))) {
                        mCategory = RecipeEntry.CATEGORY_MEAT;
                    } else if (selection.equals(getString(R.string.category_vegetables))) {
                        mCategory = RecipeEntry.CATEGORY_VEGETABLES;
                    } else if (selection.equals(getString(R.string.category_cold_buffet))) {
                        mCategory = RecipeEntry.CATEGORY_COLD_BUFFET;
                    } else if (selection.equals(getString(R.string.category_hot_buffet))) {
                        mCategory = RecipeEntry.CATEGORY_HOT_BUFFET;
                    } else if (selection.equals(getString(R.string.category_desserts))) {
                        mCategory = RecipeEntry.CATEGORY_DESSERTS;
                    } else if (selection.equals(getString(R.string.category_cakes))) {
                        mCategory = RecipeEntry.CATEGORY_CAKES;
                    } else if (selection.equals(getString(R.string.category_pies))) {
                        mCategory = RecipeEntry.CATEGORY_PIES;
                    } else if (selection.equals(getString(R.string.category_breakfast))) {
                        mCategory = RecipeEntry.CATEGORY_BREAKFAST;
                    } else if (selection.equals(getString(R.string.category_salads))) {
                        mCategory = RecipeEntry.CATEGORY_SALADS;
                    } else if (selection.equals(getString(R.string.category_appetizers))) {
                        mCategory = RecipeEntry.CATEGORY_APPETIZERS;
                    } else if (selection.equals(getString(R.string.category_starters))) {
                        mCategory = RecipeEntry.CATEGORY_STARTERS;
                    } else if (selection.equals(getString(R.string.category_side_dishes))) {
                        mCategory = RecipeEntry.CATEGORY_SIDE_DISHES;
                    } else if (selection.equals(getString(R.string.category_dinner))) {
                        mCategory = RecipeEntry.CATEGORY_DINNER;
                    } else if (selection.equals(getString(R.string.category_breads))) {
                        mCategory = RecipeEntry.CATEGORY_BREADS;
                    } else {
                        mCategory = RecipeEntry.CATEGORY_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = RecipeEntry.CATEGORY_UNKNOWN;
            }
        });
    }

    private void setupUnitSpinner() {

        ArrayAdapter unitSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_unit_options, android.R.layout.simple_spinner_item);

        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mUnitSpinner.setAdapter(unitSpinnerAdapter);

        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.unit_teaspoon))) {
                        mUnit = RecipeEntry.UNIT_TEASPOON;
                    } else if (selection.equals(getString(R.string.unit_tablespoon))) {
                        mUnit = RecipeEntry.UNIT_TABLESPOON;
                    } else if (selection.equals(getString(R.string.unit_fluid_ounce))) {
                        mUnit = RecipeEntry.UNIT_FLUID_OUNCE;
                    } else if (selection.equals(getString(R.string.unit_gill))) {
                        mUnit = RecipeEntry.UNIT_GILL;
                    } else if (selection.equals(getString(R.string.unit_cup))) {
                        mUnit = RecipeEntry.UNIT_CUP;
                    } else if (selection.equals(getString(R.string.unit_pint))) {
                        mUnit = RecipeEntry.UNIT_PINT;
                    } else if (selection.equals(getString(R.string.unit_quart))) {
                        mUnit = RecipeEntry.UNIT_QUART;
                    } else if (selection.equals(getString(R.string.unit_gallon))) {
                        mUnit = RecipeEntry.UNIT_GALLON;
                    } else if (selection.equals(getString(R.string.unit_milliliter))) {
                        mUnit = RecipeEntry.UNIT_MILLILITER;
                    } else if (selection.equals(getString(R.string.unit_liter))) {
                        mUnit = RecipeEntry.UNIT_LITER;
                    } else if (selection.equals(getString(R.string.unit_deciliter))) {
                        mUnit = RecipeEntry.UNIT_DECILITER;
                    } else if (selection.equals(getString(R.string.unit_pound))) {
                        mUnit = RecipeEntry.UNIT_POUND;
                    } else if (selection.equals(getString(R.string.unit_ounce))) {
                        mUnit = RecipeEntry.UNIT_OUNCE;
                    } else if (selection.equals(getString(R.string.unit_milligram))) {
                        mUnit = RecipeEntry.UNIT_MILLIGRAM;
                    } else if (selection.equals(getString(R.string.unit_gram))) {
                        mUnit = RecipeEntry.UNIT_GRAM;
                    } else if (selection.equals(getString(R.string.unit_kilogram))) {
                        mUnit = RecipeEntry.UNIT_KILOGRAM;
                    } else if (selection.equals(getString(R.string.unit_millimeter))) {
                        mUnit = RecipeEntry.UNIT_MILLIMETER;
                    } else if (selection.equals(getString(R.string.unit_centimeter))) {
                        mUnit = RecipeEntry.UNIT_CENTIMETER;
                    } else if (selection.equals(getString(R.string.unit_meter))) {
                        mUnit = RecipeEntry.UNIT_METER;
                    } else if (selection.equals(getString(R.string.unit_inch))) {
                        mUnit = RecipeEntry.UNIT_INCH;
                    } else if (selection.equals(getString(R.string.unit_bunch))) {
                        mUnit = RecipeEntry.UNIT_BUNCH;
                    } else if (selection.equals(getString(R.string.unit_clove))) {
                        mUnit = RecipeEntry.UNIT_CLOVE;
                    } else if (selection.equals(getString(R.string.unit_pinch))) {
                        mUnit = RecipeEntry.UNIT_PINCH;
                    } else if (selection.equals(getString(R.string.unit_sprig))) {
                        mUnit = RecipeEntry.UNIT_SPRIG;
                    } else if (selection.equals(getString(R.string.unit_strip))) {
                        mUnit = RecipeEntry.UNIT_STRIP;
                    } else {
                        mUnit = RecipeEntry.UNIT_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mUnit = RecipeEntry.UNIT_UNKNOWN;
            }
        });
    }

    private void saveRecipe() {

        String nameString = mNameEditText.getText().toString().trim();
        String servingsString = mServingsEditText.getText().toString().trim();
        String yieldString = mYieldEditText.getText().toString().trim();
        String instructionsString = mInstructionsEditText.getText().toString().trim();

        if (mCurrentRecipeUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(servingsString) &&
                TextUtils.isEmpty(yieldString) && mCategory == RecipeEntry.CATEGORY_UNKNOWN) {
            return;
        }

        if (mCurrentRecipeUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(servingsString) &&
                TextUtils.isEmpty(yieldString) && mUnit == RecipeEntry.UNIT_UNKNOWN) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COLUMN_RECIPE_NAME, nameString);
        values.put(RecipeEntry.COLUMN_RECIPE_SERVINGS, servingsString);
        values.put(RecipeEntry.COLUMN_RECIPE_CATEGORY, mCategory);
        values.put(RecipeEntry.COLUMN_RECIPE_UNIT, mUnit);
        values.put(RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS,instructionsString);

        int yield = 0;
        if (!TextUtils.isEmpty(yieldString)) {
            yield = Integer.parseInt(yieldString);
        }
        values.put(RecipeEntry.COLUMN_RECIPE_YIELD, yield);

        if (mCurrentRecipeUri == null) {

            Uri newUri = getContentResolver().insert(RecipeEntry.CONTENT_URI, values);

            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_recipe_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_recipe_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentRecipeUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.editor_update_recipe_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_update_recipe_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentRecipeUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                saveRecipe();
                finish();
                return true;

            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;

            case android.R.id.home:

                if (!mRecipeHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mRecipeHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                RecipeEntry._ID,
                RecipeEntry.COLUMN_RECIPE_NAME,
                RecipeEntry.COLUMN_RECIPE_SERVINGS,
                RecipeEntry.COLUMN_RECIPE_CATEGORY,
                RecipeEntry.COLUMN_RECIPE_YIELD,
                RecipeEntry.COLUMN_RECIPE_UNIT,
                RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS};

        return new CursorLoader(this,
                mCurrentRecipeUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_NAME);
            int servingsColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_SERVINGS);
            int categoryColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_CATEGORY);
            int yieldColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_YIELD);
            int unitColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_UNIT);
            int instructionsColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS);

            String name = cursor.getString(nameColumnIndex);
            int servings = cursor.getInt(servingsColumnIndex);
            String category = cursor.getString(categoryColumnIndex);
            int yield = cursor.getInt(yieldColumnIndex);
            String unit = cursor.getString(unitColumnIndex);
            String instructions = cursor.getString(instructionsColumnIndex);

            mNameEditText.setText(name);
            mServingsEditText.setText(Integer.toString(servings));
            mCategory = Integer.parseInt(category);
            mUnit = Integer.parseInt(unit);
            mInstructionsEditText.setText(instructions);
            mYieldEditText.setText(Integer.toString(yield));

            setCategorySelection();

            setUnitSelection();
        }
    }

    private void setUnitSelection() {
        switch (mUnit) {
            case RecipeEntry.UNIT_TEASPOON:
                mUnitSpinner.setSelection(1);
                break;
            case RecipeEntry.UNIT_TABLESPOON:
                mUnitSpinner.setSelection(2);
                break;
            case RecipeEntry.UNIT_FLUID_OUNCE:
                mUnitSpinner.setSelection(3);
                break;
            case RecipeEntry.UNIT_GILL:
                mUnitSpinner.setSelection(4);
                break;
            case RecipeEntry.UNIT_CUP:
                mUnitSpinner.setSelection(5);
                break;
            case RecipeEntry.UNIT_PINT:
                mUnitSpinner.setSelection(6);
                break;
            case RecipeEntry.UNIT_QUART:
                mUnitSpinner.setSelection(7);
                break;
            case RecipeEntry.UNIT_GALLON:
                mUnitSpinner.setSelection(8);
                break;
            case RecipeEntry.UNIT_MILLILITER:
                mUnitSpinner.setSelection(9);
                break;
            case RecipeEntry.UNIT_LITER:
                mUnitSpinner.setSelection(10);
                break;
            case RecipeEntry.UNIT_DECILITER:
                mUnitSpinner.setSelection(11);
                break;
            case RecipeEntry.UNIT_POUND:
                mUnitSpinner.setSelection(12);
                break;
            case RecipeEntry.UNIT_OUNCE:
                mUnitSpinner.setSelection(13);
                break;
            case RecipeEntry.UNIT_MILLIGRAM:
                mUnitSpinner.setSelection(14);
                break;
            case RecipeEntry.UNIT_GRAM:
                mUnitSpinner.setSelection(15);
                break;
            case RecipeEntry.UNIT_KILOGRAM:
                mUnitSpinner.setSelection(16);
                break;
            case RecipeEntry.UNIT_MILLIMETER:
                mUnitSpinner.setSelection(17);
                break;
            case RecipeEntry.UNIT_CENTIMETER:
                mUnitSpinner.setSelection(18);
                break;
            case RecipeEntry.UNIT_METER:
                mUnitSpinner.setSelection(19);
                break;
            case RecipeEntry.UNIT_INCH:
                mUnitSpinner.setSelection(20);
                break;
            case RecipeEntry.UNIT_BUNCH:
                mUnitSpinner.setSelection(21);
                break;
            case RecipeEntry.UNIT_CLOVE:
                mUnitSpinner.setSelection(22);
                break;
            case RecipeEntry.UNIT_PINCH:
                mUnitSpinner.setSelection(23);
                break;
            case RecipeEntry.UNIT_SPRIG:
                mUnitSpinner.setSelection(24);
                break;
            case RecipeEntry.UNIT_STRIP:
                mUnitSpinner.setSelection(25);
                break;
            default:
                mUnitSpinner.setSelection(0);
                break;
        }
    }

    private void setCategorySelection() {
        switch (mCategory) {
            case RecipeEntry.CATEGORY_SOUPS:
                mCategorySpinner.setSelection(1);
                break;
            case RecipeEntry.CATEGORY_SAUCES:
                mCategorySpinner.setSelection(2);
                break;
            case RecipeEntry.CATEGORY_EGGS:
                mCategorySpinner.setSelection(3);
                break;
            case RecipeEntry.CATEGORY_ENTREES:
                mCategorySpinner.setSelection(4);
                break;
            case RecipeEntry.CATEGORY_LUNCH:
                mCategorySpinner.setSelection(5);
                break;
            case RecipeEntry.CATEGORY_FISH:
                mCategorySpinner.setSelection(6);
                break;
            case RecipeEntry.CATEGORY_POULTRY:
                mCategorySpinner.setSelection(7);
                break;
            case RecipeEntry.CATEGORY_MEAT:
                mCategorySpinner.setSelection(8);
                break;
            case RecipeEntry.CATEGORY_VEGETABLES:
                mCategorySpinner.setSelection(9);
                break;
            case RecipeEntry.CATEGORY_COLD_BUFFET:
                mCategorySpinner.setSelection(10);
                break;
            case RecipeEntry.CATEGORY_HOT_BUFFET:
                mCategorySpinner.setSelection(11);
                break;
            case RecipeEntry.CATEGORY_DESSERTS:
                mCategorySpinner.setSelection(12);
                break;
            case RecipeEntry.CATEGORY_CAKES:
                mCategorySpinner.setSelection(13);
                break;
            case RecipeEntry.CATEGORY_PIES:
                mCategorySpinner.setSelection(14);
                break;
            case RecipeEntry.CATEGORY_BREAKFAST:
                mCategorySpinner.setSelection(15);
                break;
            case RecipeEntry.CATEGORY_SALADS:
                mCategorySpinner.setSelection(16);
                break;
            case RecipeEntry.CATEGORY_APPETIZERS:
                mCategorySpinner.setSelection(17);
                break;
            case RecipeEntry.CATEGORY_STARTERS:
                mCategorySpinner.setSelection(18);
                break;
            case RecipeEntry.CATEGORY_SIDE_DISHES:
                mCategorySpinner.setSelection(19);
                break;
            case RecipeEntry.CATEGORY_DINNER:
                mCategorySpinner.setSelection(20);
                break;
            case RecipeEntry.CATEGORY_BREADS:
                mCategorySpinner.setSelection(21);
                break;
            default:
                mCategorySpinner.setSelection(0);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mServingsEditText.setText("");
        mCategorySpinner.setSelection(0); // Select "Unknown" category
        mYieldEditText.setText("");
        mUnitSpinner.setSelection(0); // Select "Unknown" unit
        mInstructionsEditText.setText("");

    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteRecipe();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteRecipe() {

        if (mCurrentRecipeUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentRecipeUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_recipe_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_recipe_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}