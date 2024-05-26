package ru.wolfnord.task12;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase db;
    private static final Uri CONTENT_URI = Uri.parse("content://ru.wolfnord.task12.provider/movies");
    private static final String TAG = "MyContentProvider";

    @Override
    public boolean onCreate() {
        DataBase dataBase = new DataBase(getContext());
        db = dataBase.getWritableDatabase();
        return (db != null);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query request received.");
        Cursor cursor = db.query("movies", projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) {
            Log.d(TAG, "Query returned " + cursor.getCount() + " rows.");
        } else {
            Log.d(TAG, "Query returned null.");
        }
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        assert values != null;
        Log.d(TAG, "Insert request received with values: " + values);
        long rowID = db.insert("movies", "", values);
        Uri insertedUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(insertedUri, null);
        Log.d(TAG, "New row inserted at URI: " + insertedUri);
        return insertedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete("movies", selection, selectionArgs);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = db.update("movies", values, selection, selectionArgs);
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.wolfnord.movies";
    }
}

