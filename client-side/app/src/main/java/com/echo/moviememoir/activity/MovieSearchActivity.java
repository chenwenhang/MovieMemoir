package com.echo.moviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.adapter.SearchRecycleViewAdapter;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.restful.RestClient;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private SearchRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_movie_search);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.search_recycler_view);
        searchView = findViewById(R.id.search_view);
        titleBar = findViewById(R.id.search_title_bar);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieSearchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // get results
                String str = RestClient.findByMovieName(query);
                JSONArray res = null;
                List<Memoir> memoirs = new ArrayList<>();
                try {
                    res = new JSONArray(str);
                    for (int i = 0; i < res.length(); i++) {
                        Memoir memoir = new Memoir();
                        memoir.setMemoirId((Integer) res.getJSONObject(i).get("memoirId"));
                        memoir.setMovieName((String) res.getJSONObject(i).get("movieName"));
                        memoir.setMovieReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse((String) res.getJSONObject(i).get("movieReleaseDate")));
                        memoirs.add(memoir);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter = new SearchRecycleViewAdapter(memoirs);
                recyclerView.addItemDecoration(new DividerItemDecoration(MovieSearchActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(MovieSearchActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    public void onClick(View view) {

    }
}
