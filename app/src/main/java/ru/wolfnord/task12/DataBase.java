package ru.wolfnord.task12;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу "movies"
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                "movies" + " (" +
                "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title" + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Метод вызывается, если изменяется версия базы данных
        db.execSQL("DROP TABLE IF EXISTS " + "movies");
        onCreate(db);
    }
}
