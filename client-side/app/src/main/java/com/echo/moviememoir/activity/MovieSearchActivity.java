package com.echo.moviememoir.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.echo.moviememoir.api.TheMovieDBAPI;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.DateString;
import com.echo.moviememoir.utils.LocalStorage;
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
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onQueryTextSubmit(final String query) {

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return TheMovieDBAPI.searchMovie(query);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        List<Memoir> memoirs = new ArrayList<>();
                        JSONArray res = TheMovieDBAPI.getSnippet(result, "results");
                        try {
                            for (int i = 0; i < res.length(); i++) {
                                Memoir memoir = new Memoir();
                                memoir.setMovieName((String) res.getJSONObject(i).get("title"));
                                String voteAverage = String.valueOf(res.getJSONObject(i).get("vote_average"));
                                memoir.setScore(String.valueOf((Double.parseDouble(voteAverage)) * 10));
                                memoir.setDescription((String) res.getJSONObject(i).get("overview"));
                                memoir.setMemoirId((Integer) res.getJSONObject(i).get("id"));
                                if (res.getJSONObject(i).has("release_date") && !((String) res.getJSONObject(i).get("release_date")).equals(""))
                                    memoir.setMovieReleaseDate(DateString.string2Date((String) res.getJSONObject(i).get("release_date")));
                                memoirs.add(memoir);
                            }

                            Context context = getBaseContext();
                            adapter = new SearchRecycleViewAdapter(memoirs, context);
                            recyclerView.addItemDecoration(new DividerItemDecoration(MovieSearchActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(adapter);
                            layoutManager = new LinearLayoutManager(MovieSearchActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();

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
