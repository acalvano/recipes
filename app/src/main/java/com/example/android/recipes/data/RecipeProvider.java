package com.example.android.recipes.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.recipes.data.RecipeContract.RecipeEntry;

public class RecipeProvider extends ContentProvider {

    public static final String LOG_TAG = RecipeProvider.class.getSimpleName();

    private static final int RECIPES = 100;

    private static final int RECIPE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        sUriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY, RecipeContract.PATH_RECIPES, RECIPES);

        sUriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY, RecipeContract.PATH_RECIPES + "/#", RECIPE_ID);
    }

    /** Database helper object */
    private RecipeDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new RecipeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                cursor = database.query(RecipeEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case RECIPE_ID:
                selection = RecipeEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(RecipeEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                return insertRecipe(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertRecipe(Uri uri, ContentValues values) {

        String name = values.getAsString(RecipeEntry.COLUMN_RECIPE_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Recipe requires a name");
        }

        Integer servings = values.getAsInteger(RecipeEntry.COLUMN_RECIPE_SERVINGS);
        if (servings != null && servings < 0) {
            throw new IllegalArgumentException("Recipe requires valid servings");
        }

        String category = values.getAsString(RecipeEntry.COLUMN_RECIPE_CATEGORY);
        if (category == null) {
            throw new IllegalArgumentException("Recipe requires a category");
        }


        Integer yield = values.getAsInteger(RecipeEntry.COLUMN_RECIPE_YIELD);
        if (yield != null && yield < 0) {
            throw new IllegalArgumentException("Recipe requires valid yield");
        }



        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(RecipeEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                return updateRecipe(uri, contentValues, selection, selectionArgs);
            case RECIPE_ID:
                selection = RecipeEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateRecipe(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateRecipe(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(RecipeEntry.COLUMN_RECIPE_NAME)) {
            String name = values.getAsString(RecipeEntry.COLUMN_RECIPE_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Recipe requires a name");
            }
        }

        if (values.containsKey(RecipeEntry.COLUMN_RECIPE_SERVINGS)) {
            Integer servings = values.getAsInteger(RecipeEntry.COLUMN_RECIPE_SERVINGS);
            if (servings != null && servings < 0) {
                throw new IllegalArgumentException("Recipe requires valid serving");
            }
        }

        if (values.containsKey(RecipeEntry.COLUMN_RECIPE_CATEGORY)) {
            String category = values.getAsString(RecipeEntry.COLUMN_RECIPE_CATEGORY);
            if (category == null) {
                throw new IllegalArgumentException("Recipe requires a name");
            }
        }

        if (values.containsKey(RecipeEntry.COLUMN_RECIPE_YIELD)) {
            Integer yield = values.getAsInteger(RecipeEntry.COLUMN_RECIPE_YIELD);
            if (yield != null && yield < 0) {
                throw new IllegalArgumentException("Recipe requires valid yield");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(RecipeEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                rowsDeleted = database.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RECIPE_ID:
                selection = RecipeEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RECIPES:
                return RecipeEntry.CONTENT_LIST_TYPE;
            case RECIPE_ID:
                return RecipeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}