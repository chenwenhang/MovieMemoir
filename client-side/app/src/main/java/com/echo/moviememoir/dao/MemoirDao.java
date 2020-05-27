package com.echo.moviememoir.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.echo.moviememoir.entity.Memoir;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MemoirDao {
    @Query("SELECT * FROM memoir")
    List<Memoir> getAll();

    @Query("SELECT * FROM memoir WHERE memoirId = :memoirId LIMIT 1")
    Memoir findByID(int memoirId);

    @Insert
    void insertAll(Memoir... memoirs);

    @Insert
    long insert(Memoir memoir);

    @Delete
    void delete(Memoir memoirs);

    @Update(onConflict = REPLACE)
    void updateCustomers(Memoir... memoirs);

    @Query("DELETE FROM memoir")
    void deleteAll();

}
