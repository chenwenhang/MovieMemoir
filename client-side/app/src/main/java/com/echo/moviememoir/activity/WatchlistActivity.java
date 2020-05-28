package com.echo.moviememoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.adapter.HomeRecyclerViewAdapter;
import com.echo.moviememoir.adapter.WatchlistRecycleViewAdapter;
import com.echo.moviememoir.database.MemoirDatabase;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.viewmodel.MemoirViewModel;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.List;

public class WatchlistActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private MemoirViewModel memoirViewModel;
    private WatchlistRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_watchlist);

        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.watchlist_title_bar);
        memoirViewModel = new ViewModelProvider(this).get(MemoirViewModel.class);
        memoirViewModel.initalizeVars(getApplication());
        recyclerView = findViewById(R.id.watchlist_recycler_view);

        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WatchlistActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        memoirViewModel.getAllMemoirs().observe(this, new Observer<List<Memoir>>() {
            @Override
            public void onChanged(@Nullable final List<Memoir> memoirs) {
                assert memoirs != null;
                for (Memoir memoir : memoirs) {
                    System.out.println(memoir.toString());
                }

                Context context = getBaseContext();
                adapter = new WatchlistRecycleViewAdapter(memoirs, context, memoirViewModel, WatchlistActivity.this);
                recyclerView.addItemDecoration(new DividerItemDecoration(WatchlistActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(WatchlistActivity.this);
                recyclerView.setLayoutManager(layoutManager);
            }
        });


    }

    public void onClick(View view) {

    }

}
