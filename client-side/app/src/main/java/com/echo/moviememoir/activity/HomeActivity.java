package com.echo.moviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.adapter.HomeRecyclerViewAdapter;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.google.android.material.navigation.NavigationView;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private SuperTextView dateBar;
    private HomeRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.home_title_bar);
        dateBar = findViewById(R.id.home_date);
        recyclerView = findViewById(R.id.home_recycler_view);
        navigationView = findViewById(R.id.nv_home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.menu_movieSearch:
                        intent = new Intent(HomeActivity.this, MovieSearchActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_movieMemoir:
                        intent = new Intent(HomeActivity.this, MovieMemoirActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_watchlist:
                        intent = new Intent(HomeActivity.this, WatchlistActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_reports:
                        intent = new Intent(HomeActivity.this, ReportsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_maps:
                        intent = new Intent(HomeActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // set subtitle
        if (LocalStorage.getUser() != null)
            titleBar.setSubTitle("Hello, " + LocalStorage.getUser().getName());
        titleBar.disableLeftView();

        // set data bar
        dateBar.setCenterString(DateStringUtils.date2String(new Date(System.currentTimeMillis())));

        // get top five memoir
        String str = RestClient.findFiveHighestMovieByUserId(LocalStorage.getUser().getUserId());
        JSONArray res = null;
        List<Memoir> memoirs = new ArrayList<>();
        try {
            res = new JSONArray(str);
            for (int i = 0; i < res.length(); i++) {
                Memoir memoir = new Memoir();
                memoir.setMovieName((String) res.getJSONObject(i).get("movie_name"));
                memoir.setMovieReleaseDate(DateStringUtils.string2Date((String) res.getJSONObject(i).get("release_date")));
                memoir.setScore("95");
                memoirs.add(memoir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new HomeRecyclerViewAdapter(memoirs);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    public void onClick(View view) {

    }
}
