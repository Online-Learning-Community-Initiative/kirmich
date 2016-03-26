package com.rolc.kirmich.contentdb;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListAdapter;

import java.util.HashSet;
import java.util.Set;

public class TagDB  extends SQLiteOpenHelper implements ContentDB {
    // SharedPreferences
    private static final String MY_PREFS = "rolc-prefs";
    private static final String PREFS_ALL_TAGS = "all-tags";

    // SQL Database
    private static final String DB_NAME = "rolc.db";
    public static final String CONTENT_TABLE_NAME = "content";
    public static final String CONTENT_COLUMN_ID = "_id";
    public static final String CONTENT_COLUMN_FILENAME = "filename";
    public static final String CONTENT_COLUMN_TYPE = "type";
    public static final String CONTENT_COLUMN_SIZE = "size";
    public static final String CONTENT_COLUMN_AVAILABLE = "available";
    public static final String CONTENT_COLUMN_TAGS = "tags";
    public static final String CONTENT_COLUMN_STARRED = "favourite";

    // data members
    private Context context = null;
    private static final String QUERY_CREATE_CONTENT_TABLE = "CREATE TABLE " +
            CONTENT_TABLE_NAME + " (" + CONTENT_COLUMN_ID + " integer primary key, " +
            CONTENT_COLUMN_FILENAME + " text, " + CONTENT_COLUMN_TYPE + " varchar(255), " +
            CONTENT_COLUMN_SIZE + " integer, " + CONTENT_COLUMN_AVAILABLE + " boolean, " +
            CONTENT_COLUMN_TAGS + " text, " + CONTENT_COLUMN_STARRED + " boolean)";
    private static final String QUERY_DROP_CONTENT_TABLE = "DROP TABLE IF EXISTS " +
            CONTENT_TABLE_NAME;

    public TagDB(Context context) {
        super(context, DB_NAME , null, 1);
        this.context = context;
    }

    Set<String> fetchAllTagsFromDB() {
        return new HashSet<String>() {{
            add("addition");
            add("maths");
            add("multiplication");
        }};
    }

    public boolean insertRowContent(SQLiteDatabase db, int id, String filename, String type,
                                    int size, boolean available, String tags, boolean starred) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTENT_COLUMN_ID, id);
        contentValues.put(CONTENT_COLUMN_FILENAME, filename);
        contentValues.put(CONTENT_COLUMN_TYPE, type);
        contentValues.put(CONTENT_COLUMN_SIZE, size);
        contentValues.put(CONTENT_COLUMN_AVAILABLE, available);
        contentValues.put(CONTENT_COLUMN_TAGS, tags);
        contentValues.put(CONTENT_COLUMN_STARRED, starred);
        return db.insert(CONTENT_TABLE_NAME, null, contentValues) > 0;
    }

    public Set<String> getAllTags() {
        SharedPreferences shprefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        Set<String> all_tags = shprefs.getStringSet(PREFS_ALL_TAGS, null);
        if(all_tags == null) {
            return this.fetchAllTagsFromDB();
        } else {
            return all_tags;
        }
    }

    public ListAdapter searchTags(String[] tags) {
        String query = "SELECT * FROM " + CONTENT_TABLE_NAME + " WHERE ";
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
        Cursor cursor = db.rawQuery(query, null);
        return new TagSearchAdapter(this.context, cursor);
    }

    @Override
    public ListAdapter getStarred() {
        return null;
    }

    public void getItem(int id) {
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_CONTENT_TABLE);
        this.insertRowContent(db, 1, "adding-worksheet-animals.pdf", "pdf", 336152, true,
                " addition maths ", false);
        this.insertRowContent(db, 2, "http://www.softschools.com/math/addition/learning_addition_for_kids/",
                "flash", -1, false, " addition maths ", false);
        this.insertRowContent(db, 3, "Multiplication Made Easy.3gp", "video", 14796874, true,
                " maths multiplication ", false);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DROP_CONTENT_TABLE);
        onCreate(db);
    }
}
