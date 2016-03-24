package com.rolc.kirmich.contentdb;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;

public class TagDB  extends SQLiteOpenHelper implements ContentDB {
    // SharedPreferences
    public static final String MY_PREFS = "rolc-prefs";
    public static final String PREFS_ALL_TAGS = "all-tags";

    // SQL Database
    public static final String DB_NAME = "rolc.db";
    public static final String CONTENT_TABLE_NAME = "content";
    public static final String CONTENT_COLUMN_ID = "id";
    public static final String CONTENT_COLUMN_FILENAME = "filename";
    public static final String CONTENT_COLUMN_TYPE = "type";
    public static final String CONTENT_COLUMN_SIZE = "size";
    public static final String CONTENT_COLUMN_AVAILABLE = "available";
    public static final String CONTENT_COLUMN_TAGS = "tags";

    public static final String QUERY_CREATE_CONTENT_TABLE = "CREATE TABLE " +
            CONTENT_TABLE_NAME + " (id integer primary key," +
            "filename text, " + "type varchar(255), " + "size integer, " +
            "available boolean, " + "tags text)";
    public static final String QUERY_DROP_CONTENT_TABLE = "DROP TABLE IF EXISTS " +
            CONTENT_TABLE_NAME;

    public TagDB(Context context) {
        super(context, DB_NAME , null, 1);
    }

    Set<String> fetchAllTagsFromDB(Context context) {
        return new HashSet<String>() {{
            add("addition");
            add("maths");
            add("multiplication");
        }};
    }

    public boolean insertRowContent(int id, String filename, String type, int size, boolean available, String tags) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("filename", filename);
        contentValues.put("type", type);
        contentValues.put("size", size);
        contentValues.put("available", available);
        contentValues.put("tags", tags);
        db.insert(CONTENT_TABLE_NAME, null, contentValues);
        return true;
    }

    public Set<String> getAllTags(Context context) {
        SharedPreferences shprefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        Set<String> all_tags = shprefs.getStringSet(PREFS_ALL_TAGS, null);
        if(all_tags == null) {
            return this.fetchAllTagsFromDB(context);
        } else {
            return all_tags;
        }
    }

    public Cursor Search(String[] tags, int limit) {
        String query = "SELECT " + CONTENT_COLUMN_ID + ", " + CONTENT_COLUMN_FILENAME +
                " FROM " + CONTENT_TABLE_NAME + " WHERE ";
        boolean flag = true;
        for (String tag : tags) {
            if (flag) {
                query += "tags LIKE \" " + tag + " \"";
                flag = false;
            } else {
                query += " OR tags LIKE \" " + tag + " \"";
            }
        }

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void getItem(int id) {
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_CONTENT_TABLE);
        this.insertRowContent(1, "adding-worksheet-animals.pdf", "pdf", 336152, true, " addition maths ");
        this.insertRowContent(2, "http://www.softschools.com/math/addition/learning_addition_for_kids/",
                "flash", -1, false, " addition maths ");
        this.insertRowContent(3, "Multiplication Made Easy.3gp", "video", 14796874, true, " maths multiplication ");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DROP_CONTENT_TABLE);
        onCreate(db);
    }
}
