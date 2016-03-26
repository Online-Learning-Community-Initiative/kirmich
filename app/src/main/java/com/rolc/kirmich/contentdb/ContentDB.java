package com.rolc.kirmich.contentdb;

import android.content.Context;
import android.database.Cursor;

import java.util.Set;

public interface ContentDB {
    Set<String> getAllTags(Context context);
    Cursor Search(String[] tags, int limit);
    void getItem(int id);
}
