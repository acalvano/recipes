package com.example.android.recipes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.recipes.data.RecipeContract.RecipeEntry;

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeDbHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_RECIPES_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + "("
                + RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecipeEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, "
                + RecipeEntry.COLUMN_RECIPE_SERVINGS + " INTEGER NOT NULL, "
                + RecipeEntry.COLUMN_RECIPE_CATEGORY + " TEXT, "
                + RecipeEntry.COLUMN_RECIPE_YIELD + " INTEGER NOT NULL DEFAULT 0, "
                + RecipeEntry.COLUMN_RECIPE_INSTRUCTIONS + " TEXT);";

        db.execSQL(SQL_CREATE_RECIPES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

