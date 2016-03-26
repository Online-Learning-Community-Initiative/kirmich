package com.rolc.kirmich.contentdb;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.Set;

public interface ContentDB {
    Set<String> getAllTags();
    ListAdapter searchTags(String[] tags);
    ListAdapter getStarred();
    void getItem(int id);
}
