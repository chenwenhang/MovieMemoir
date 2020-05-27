package com.echo.moviememoir.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.echo.moviememoir.dao.MemoirDao;
import com.echo.moviememoir.entity.Memoir;

@Database(entities = {Memoir.class}, version = 2, exportSchema = false)
public abstract class MemoirDatabase extends RoomDatabase {
    public abstract MemoirDao memoirDao();

    private static MemoirDatabase INSTANCE;

    public static synchronized MemoirDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MemoirDatabase.class, "MemoirDatabase").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}