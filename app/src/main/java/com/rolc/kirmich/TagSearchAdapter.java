package com.rolc.kirmich;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class TagSearchAdapter extends CursorAdapter {
    private TagDB tagdb;

    public TagSearchAdapter(TagDB tagdb, Cursor cursor) {
        super(tagdb.context, cursor, 0);
        this.tagdb = tagdb;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView fileName = (TextView) view.findViewById(R.id.listItem);
        ImageButton star = (ImageButton) view.findViewById(R.id.starButton);
        String text = cursor.getString(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_FILENAME));
        boolean isStarred = cursor.getInt(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_STARRED)) > 0;
        fileName.setText(text);
        star.setSelected(isStarred);
        final String id = cursor.getString(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_ID));

        fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // TODO: detailIntent.putExtra();
                context.startActivity(detailIntent);
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton star = (ImageButton) v.findViewById(R.id.starButton);
                star.setSelected(!star.isSelected());
                tagdb.setStar(id, star.isSelected());
            }
        });

    }
}
