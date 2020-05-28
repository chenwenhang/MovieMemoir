package com.echo.moviememoir.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.repository.MemoirRepository;

import java.util.List;

public class MemoirViewModel extends ViewModel {
    private MemoirRepository cRepository;

    private MutableLiveData<List<Memoir>> allMemoirs;

    public MemoirViewModel() {
        allMemoirs = new MutableLiveData<>();
    }

    public void setMemoirs(List<Memoir> memoirs) {
        allMemoirs.setValue(memoirs);
    }

    public LiveData<List<Memoir>> getAllMemoirs() {
        return cRepository.getAllMemoirs();
    }

    public LiveData<Memoir> findByID(int memoirId) {
        return cRepository.findByID(memoirId);
    }

    public void initalizeVars(Application application) {
        cRepository = new MemoirRepository(application);
    }

    public void insert(Memoir memoir) {
        cRepository.insert(memoir);
    }

    public void insertAll(Memoir... memoirs) {
        cRepository.insertAll(memoirs);
    }

    public void deleteAll() {
        cRepository.deleteAll();
    }

    public void delete(Memoir memoir) {
        cRepository.delete(memoir);
    }

    public void update(Memoir... memoirs) {
        cRepository.updateCustomers(memoirs);
    }
}
