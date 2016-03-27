package com.rolc.kirmich;

import android.widget.ListAdapter;

import java.util.Set;

public interface ContentDB {
    Set<String> getAllTags();
    ListAdapter searchTags(String[] tags);
    ListAdapter getStarred();
    void getItem(int id);
    void setStar(String id, boolean isSelected);
}
