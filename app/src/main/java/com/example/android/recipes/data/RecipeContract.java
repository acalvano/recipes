package com.example.android.recipes.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public final class RecipeContract {

    private RecipeContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.recipes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RECIPES = "recipes";

    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RECIPES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPES;

        public final static String TABLE_NAME = "recipes";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_RECIPE_NAME ="name";
        public final static String COLUMN_RECIPE_CATEGORY = "category";
        public final static String COLUMN_RECIPE_SERVINGS = "servings";
        public final static String COLUMN_RECIPE_YIELD = "yield";
        public final static String COLUMN_RECIPE_INSTRUCTIONS = "edit_recipe_instructions";

        public static final int CATEGORY_UNKNOWN = 0;
        public static final int CATEGORY_SOUPS = 1;
        public static final int CATEGORY_SAUCES = 2;
        public static final int CATEGORY_EGGS = 3;
        public static final int CATEGORY_ENTREES = 4;
        public static final int CATEGORY_LUNCH = 5;
        public static final int CATEGORY_FISH = 6;
        public static final int CATEGORY_POULTRY = 7;
        public static final int CATEGORY_MEAT = 8;
        public static final int CATEGORY_VEGETABLES = 9;
        public static final int CATEGORY_COLD_BUFFET = 10;
        public static final int CATEGORY_HOT_BUFFET = 11;
        public static final int CATEGORY_DESSERTS = 12;
        public static final int CATEGORY_CAKES = 13;
        public static final int CATEGORY_PIES = 14;
        public static final int CATEGORY_BREAKFAST = 15;
        public static final int CATEGORY_SALADS = 16;
        public static final int CATEGORY_APPETIZERS = 17;
        public static final int CATEGORY_STARTERS = 18;
        public static final int CATEGORY_SIDE_DISHES = 19;
        public static final int CATEGORY_DINNER = 20;
        public static final int CATEGORY_BREADS = 21;


        public static boolean isValidCategory(int category) {
            if (category >= CATEGORY_UNKNOWN && category <= CATEGORY_BREADS ) {
                return true;
            }
            return false;
        }
    }
}