package com.echo.moviememoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.adapter.MemoirRecycleViewAdapter;
import com.echo.moviememoir.entity.Cinema;
import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.google.gson.Gson;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieMemoirActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private MemoirRecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MaterialSpinner sortSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_movie_memoir);
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.moviememoir_title_bar);
        recyclerView = findViewById(R.id.moviememoir_recycler_view);
        sortSpinner = findViewById(R.id.moviememoir_spinner);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieMemoirActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // get top five memoir
        String str = RestClient.findAllMemoirs();
        JSONArray res = null;
        final List<Memoir> memoirs = new ArrayList<>();
        try {
            res = new JSONArray(str);
            for (int i = 0; i < res.length(); i++) {
                Memoir memoir = new Memoir();
                Gson gson = new Gson();
                memoir.setMovieName((String) res.getJSONObject(i).get("movieName"));
                memoir.setMovieReleaseDate(DateStringUtils.string2Date((String) res.getJSONObject(i).get("movieReleaseDate")));
                memoir.setWatchDate(DateStringUtils.string2Date((String) res.getJSONObject(i).get("watchDate")));

                memoir.setCredentialsId(gson.fromJson(res.getJSONObject(i).get("credentialsId").toString(), Credential.class));
                memoir.setMemoirId((Integer) res.getJSONObject(i).get("memoirId"));
                memoir.setCinemaId(gson.fromJson(res.getJSONObject(i).get("cinemaId").toString(), Cinema.class));
                memoir.setComment((String) res.getJSONObject(i).get("comment"));
                memoir.setScore((String) res.getJSONObject(i).get("score"));
                memoirs.add(memoir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Context context = getBaseContext();
        adapter = new MemoirRecycleViewAdapter(memoirs, context);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> items = new ArrayList<>();
        items.add("Sort by score");
        items.add("Sort by release date");
        items.add("Sort by watch date");
        sortSpinner.setItems(items);
        sortSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.equals("Sort by score")) {
                    memoirs.sort(new Comparator<Memoir>() {
                        @Override
                        public int compare(Memoir o1, Memoir o2) {
                            return o2.getScore().compareTo(o1.getScore());
                        }
                    });
                } else if (item.equals("Sort by release date")) {
                    memoirs.sort(new Comparator<Memoir>() {
                        @Override
                        public int compare(Memoir o1, Memoir o2) {
                            return o2.getMovieReleaseDate().compareTo(o1.getMovieReleaseDate());
                        }
                    });
                } else if (item.equals("Sort by watch date")) {
                    memoirs.sort(new Comparator<Memoir>() {
                        @Override
                        public int compare(Memoir o1, Memoir o2) {
                            return o2.getWatchDate().compareTo(o1.getWatchDate());
                        }
                    });
                }
                adapter = new MemoirRecycleViewAdapter(memoirs, context);
                recyclerView.addItemDecoration(new DividerItemDecoration(MovieMemoirActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(MovieMemoirActivity.this);
                recyclerView.setLayoutManager(layoutManager);
            }
        });


    }

    public void onClick(View view) {

    }
}
