package com.rolc.kirmich;

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
    public Context context = null;
    private static final String QUERY_CREATE_CONTENT_TABLE = "CREATE TABLE " +
            CONTENT_TABLE_NAME + " (" + CONTENT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CONTENT_COLUMN_FILENAME + " TEXT, " + CONTENT_COLUMN_TYPE + " TEXT, " +
            CONTENT_COLUMN_SIZE + " INTEGER, " + CONTENT_COLUMN_AVAILABLE + " INTEGER, " +
            CONTENT_COLUMN_TAGS + " TEXT, " + CONTENT_COLUMN_STARRED + " INTEGER)";
    private static final String QUERY_DROP_CONTENT_TABLE = "DROP TABLE IF EXISTS " +
            CONTENT_TABLE_NAME;

    public TagDB(Context context) {
        super(context, DB_NAME , null, 1);
        this.context = context;
    }

    Set<String> fetchAllTagsFromDB(SQLiteDatabase db) {
        String query = "SELECT " + CONTENT_COLUMN_TAGS + " FROM " + CONTENT_TABLE_NAME;
        Set all_tags = new HashSet<String>();
        Cursor cursor = db.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                String text = cursor.getString(cursor.getColumnIndex(TagDB.CONTENT_COLUMN_TAGS));
                String[] tags = text.split(" ");
                for (String tag: tags ) {
                    all_tags.add(tag.trim());
                }
            }
        } finally {
            cursor.close();
        }

        SharedPreferences shprefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shprefs.edit();
        editor.putStringSet(PREFS_ALL_TAGS, all_tags);
        editor.commit();
        return all_tags;
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
            SQLiteDatabase db = this.getReadableDatabase();
            return this.fetchAllTagsFromDB(db);
        } else {
            return all_tags;
        }
    }

    public ListAdapter searchTags(String[] tags) {
        String query = "SELECT * FROM " + CONTENT_TABLE_NAME + " WHERE ";
        boolean flag = true;
        for (String tag : tags) {
            if (flag) {
                query += CONTENT_COLUMN_TAGS + " LIKE \"% " + tag + " %\"";
                flag = false;
            } else {
                query += " OR " + CONTENT_COLUMN_TAGS + " LIKE \"% " + tag + " %\"";
            }
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return new TagSearchAdapter(this, cursor);
    }

    @Override
    public ListAdapter getStarred() {
        String query = "SELECT * FROM " + CONTENT_TABLE_NAME + " WHERE " +
                CONTENT_COLUMN_STARRED + "=1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return new TagSearchAdapter(this, cursor);
    }

    public void getItem(int id) {
    }

    @Override
    public void setStar(String id, boolean isSelected) {
        String strFilter = CONTENT_COLUMN_ID + "=" + id;
        ContentValues args = new ContentValues();
        args.put(CONTENT_COLUMN_STARRED, isSelected);
        SQLiteDatabase db = getWritableDatabase();
        db.update(CONTENT_TABLE_NAME, args, strFilter, null);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_CONTENT_TABLE);
        this.insertRowContent(db, 1, "adding-worksheet-animals.pdf", "pdf", 336152, true,
                " addition maths ", false);
        this.insertRowContent(db, 2, "http://www.softschools.com/math/addition/learning_addition_for_kids/",
                "flash", -1, false, " addition maths ", false);
        this.insertRowContent(db, 3, "5_ways_to_learn_addition.txt",
                "txt", 4228, false, " addition maths ", false);
        this.insertRowContent(db, 4, "Enjoy Learning Addition puzzle",
                "android", 2621440, false, " addition maths ", false);
        this.insertRowContent(db, 5, "http://www.learn-with-math-games.com/",
                "html", -1, false, " maths ", false);
        this.insertRowContent(db, 6, "http://www.multiplication.com/learn/fact-navigator",
                "html", -1, false, " maths multiplication ", false);
        this.insertRowContent(db, 7, "Multiplication Made Easy.3gp",
                "video", 14796874, true, " maths multiplication ", false);
        this.insertRowContent(db, 8, "Basic Addition for Kids.mp4",
                "video", 3920312, true, " maths addition ", false);
        this.insertRowContent(db, 9, "https://www.mathsisfun.com/numbers/division.html",
                "html", -1, false, " maths division ", false);
        this.insertRowContent(db, 10, "https://www.mathsisfun.com/definitions/multiplication.html",
                "html", -1, false, " maths multiplication ", false);
        this.insertRowContent(db, 11, "http://www.coolmath4kids.com/fractions/fractions-01-what-are-they-01.html",
                "html", -1, false, " maths fraction ", false);
        this.insertRowContent(db, 12, "http://math.tutorvista.com/number-system/like-fractions.html",
                "html", -1, false, " maths fraction addition subtract ", false);
        this.insertRowContent(db, 13, "https://www.mathsisfun.com/percentage.html",
                "html", -1, false, " maths percentage ", false);
        this.insertRowContent(db, 14, "https://www.mathsisfun.com/algebra/proportions.html",
                "html", -1, false, " maths proportion ", false);
        this.insertRowContent(db, 15, "https://quizlet.com/16102523/basic-geometry-terms-with-pictures-flash-cards/",
                "html", -1, false, " geometry ", false);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SharedPreferences shprefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shprefs.edit();
        editor.remove(PREFS_ALL_TAGS);
        editor.apply();
        db.execSQL(QUERY_DROP_CONTENT_TABLE);
        onCreate(db);
    }
}
