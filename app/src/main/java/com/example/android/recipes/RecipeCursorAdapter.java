package com.example.android.recipes;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.recipes.data.RecipeContract.RecipeEntry;

public class RecipeCursorAdapter extends CursorAdapter {

    public RecipeCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView categoryTextView = (TextView) view.findViewById(R.id.category);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_NAME);
        int servingsColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_SERVINGS);
        int categoryColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_CATEGORY);
        int yieldColumnIndex = cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_YIELD);

        String recipeName = cursor.getString(nameColumnIndex);
        String recipeServings = cursor.getString(servingsColumnIndex);
        String recipeCategory = cursor.getString(categoryColumnIndex);
        int categoryIndex = Integer.parseInt(recipeCategory);
        Resources res = context.getResources();
        recipeCategory = res.getStringArray(R.array.array_category_options)[categoryIndex];
        String recipeYield = cursor.getString(yieldColumnIndex);
        String summaryString = "Servings: " + recipeServings + "  Yield: " + recipeYield;

        if (TextUtils.isEmpty(recipeCategory)) {
            recipeCategory = context.getString(R.string.unknown_category);
        }

        nameTextView.setText(recipeName);
        categoryTextView.setText(recipeCategory);
        summaryTextView.setText(summaryString);
    }
}