package com.rolc.kirmich;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by adityavishwanath on 3/24/16.
 */
public class SearchAdapter extends CursorAdapter {
    public SearchAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView fileName = (TextView) view.findViewById(R.id.listItem);
        ImageButton star = (ImageButton) view.findViewById(R.id.starButton);
        String text = cursor.getString(cursor.getColumnIndex("filename"));
        //TODO: Replace the next line with the 'isStarred' field.
        boolean isStarred = cursor.getInt(cursor.getColumnIndex("available")) > 0;
        fileName.setText(text);
        //TODO: Set star state based on isStarred
    }
}
