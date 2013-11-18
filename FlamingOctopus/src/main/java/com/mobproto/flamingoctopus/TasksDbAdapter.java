package com.mobproto.flamingoctopus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TasksDbAdapter {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CONTENTS = "contents";
    private static final String COLUMN_LONG_TERM = "long_term";

    private final Context context;
    private TasksDbHelper dbHelper;
    private SQLiteDatabase db;


    public TasksDbAdapter(Context context) {
        this.context = context;
    }

    public TasksDbAdapter open() {
        dbHelper = new TasksDbHelper(context);
        db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 0, 1);
        this.createTask("test task", false);
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String contents, boolean longTerm){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENTS, contents);
        values.put(COLUMN_LONG_TERM, longTerm ? 1 : 0);
        long id = db.insert(TABLE_NAME, null, values);

        return new Task(id, contents, longTerm);
    }

    public boolean deleteNote(Task task) {
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + task.getId(), null) > 0;
    }

    public Task getNote(long id){
        Cursor cursor = db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_CONTENTS, COLUMN_LONG_TERM}, COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return taskFromCursor(cursor);

    }

    public static Task taskFromCursor(Cursor cursor){
        return new Task(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CONTENTS)),
                (cursor.getInt(cursor.getColumnIndex(COLUMN_LONG_TERM)) == 1));
    }

    public Cursor getAllTasks(){
        return db.query(true, TABLE_NAME, new String[] {COLUMN_ID, COLUMN_CONTENTS, COLUMN_LONG_TERM}, null, null, null, null, null, null);
    }


    private class TasksDbHelper extends SQLiteOpenHelper {

        private final String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTENTS + " TEXT, " +
                COLUMN_LONG_TERM + " INTEGER)";

        public TasksDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
