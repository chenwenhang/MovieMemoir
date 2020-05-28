package com.echo.moviememoir.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.echo.moviememoir.dao.MemoirDao;
import com.echo.moviememoir.database.MemoirDatabase;
import com.echo.moviememoir.entity.Memoir;

import java.util.List;

public class MemoirRepository {

    private MemoirDao dao;
    private LiveData<List<Memoir>> allMemoirs;
    private LiveData<Memoir> liveMemoir;
    private Memoir memoir;

    public MemoirRepository(Application application) {
        MemoirDatabase db = MemoirDatabase.getInstance(application);
        dao = db.memoirDao();
    }

    public LiveData<List<Memoir>> getAllMemoirs() {
        allMemoirs = dao.getAll();
        return allMemoirs;
    }

    public LiveData<Memoir> findByID(final int memoirId) {
        liveMemoir = dao.findByID(memoirId);
        return liveMemoir;
    }

    public void insert(final Memoir memoir) {
        MemoirDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(memoir);
            }
        });
    }

    public void deleteAll() {
        MemoirDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void delete(final Memoir memoir) {
        MemoirDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(memoir);
            }
        });
    }

    public void insertAll(final Memoir... memoirs) {
        MemoirDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(memoirs);
            }
        });
    }

    public void updateCustomers(final Memoir... memoirs) {
        MemoirDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateCustomers(memoirs);
            }
        });
    }

    public void setMemoir(Memoir memoir) {
        this.memoir = memoir;
    }
}
