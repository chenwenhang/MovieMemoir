package com.echo.moviememoir.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.echo.moviememoir.dao.MemoirDao;
import com.echo.moviememoir.entity.Memoir;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Memoir.class}, version = 2, exportSchema = false)
public abstract class MemoirDatabase extends RoomDatabase {
    public abstract MemoirDao memoirDao();

    private static MemoirDatabase INSTANCE;

    //we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized MemoirDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MemoirDatabase.class, "MemoirDatabase").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}