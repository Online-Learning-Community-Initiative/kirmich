package com.rolc.kirmich.contentdb;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rolc.kirmich.R;

public class TagSearchAdapter extends CursorAdapter {
    public TagSearchAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView fileName = (TextView) view.findViewById(R.id.listItem);
        ImageButton star = (ImageButton) view.findViewById(R.id.starButton);
        String text = cursor.getString(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_FILENAME));
        boolean isStarred = cursor.getInt(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_STARRED)) > 0;
        fileName.setText(text);
        // TODO: Set star state based on isStarred
    }
}
