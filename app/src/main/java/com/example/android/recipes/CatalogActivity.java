package com.example.android.recipes;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.recipes.data.RecipeContract.RecipeEntry;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int RECIPE_LOADER = 0;

    RecipeCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView recipeListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        recipeListView.setEmptyView(emptyView);

        mCursorAdapter = new RecipeCursorAdapter(this, null);
        recipeListView.setAdapter(mCursorAdapter);

        recipeListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

            Uri currentRecipeUri = ContentUris.withAppendedId(RecipeEntry.CONTENT_URI, id);

            intent.setData(currentRecipeUri);

            startActivity(intent);
        });

        getLoaderManager().initLoader(RECIPE_LOADER, null, this);
    }

    private void insertRecipe() {

        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COLUMN_RECIPE_NAME, "Grilled Cheese");
        values.put(RecipeEntry.COLUMN_RECIPE_SERVINGS, 1);
        values.put(RecipeEntry.COLUMN_RECIPE_CATEGORY, "5");
        values.put(RecipeEntry.COLUMN_RECIPE_YIELD, 0);
        values.put(RecipeEntry.COLUMN_RECIPE_UNIT, "0");
        values.put(RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS, "1 Place the cheese between two pieces of bread\n\n" +
            "2 Butter the outside of one piece of bread and place in a pan over low heat with the buttered side down\n\n" +
            "3 Butter the top piece of bread\n\n" +
            "4 Cook until browned on the first side, flip and cook the second side until the cheese is melted and the bread is browned nicely\n\n" +
            "5 Remove from pan and slice diagonally\n\n");

        Uri newUri = getContentResolver().insert(RecipeEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_insert_dummy_data:
                insertRecipe();
                return true;

            case R.id.action_delete_all_entries:
                deleteAllRecipes();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS,
        };

        return new CursorLoader(this,
                RecipeEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }

    private void deleteAllRecipes() {
        int rowsDeleted = getContentResolver().delete(RecipeEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from recipe database");
    }
}