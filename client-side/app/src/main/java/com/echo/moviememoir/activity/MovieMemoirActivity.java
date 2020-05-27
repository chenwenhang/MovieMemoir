package com.echo.moviememoir.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.echo.moviememoir.api.TheMovieDBAPI;
import com.echo.moviememoir.database.MemoirDatabase;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateString;
import com.echo.moviememoir.utils.LocalStorage;
import com.echo.moviememoir.api.SearchGoogleAPI;
import com.echo.moviememoir.utils.RatingStar;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class MovieMemoirActivity extends AppCompatActivity implements View.OnClickListener {
    MemoirDatabase db = null;
    private TitleBar titleBar;
    private SuperTextView movieName;
    private SuperTextView movieDescription;
    private SuperTextView movieReleaseDate;
    private Button addWatchlistBtn;
    private Button addMemoirBtn;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_movie_memoir);
        db = MemoirDatabase.getInstance(this);

        initView();
    }

    private void initView() {
        movieName = findViewById(R.id.memoir_movie_name);
        movieDescription = findViewById(R.id.memoir_description);
        movieReleaseDate = findViewById(R.id.movie_release_date);
        titleBar = findViewById(R.id.memoir_title_bar);
        ratingBar = findViewById(R.id.memoir_star);
        addWatchlistBtn = findViewById(R.id.memoir_add_watchlist);
        addMemoirBtn = findViewById(R.id.memoir_add_memoir);

        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieMemoirActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        final Memoir memoir = LocalStorage.getMemoir();

        movieDescription.setCenterString(memoir.getDescription());
        movieName.setCenterString(memoir.getMovieName());
        movieReleaseDate.setCenterString(DateString.date2String(memoir.getMovieReleaseDate()));

        ratingBar.setRating(RatingStar.rating2Star(memoir.getScore()));

        addWatchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] details = new String[]{
                        "" + memoir.getMemoirId(), memoir.getMovieName()
                };
                InsertDatabase insertDatabase = new InsertDatabase();
                insertDatabase.execute(details);
            }
        });

        addMemoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieMemoirActivity.this, AddToMemoirActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View view) {

    }

    private class InsertDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Memoir memoir = new Memoir();
            memoir.setMemoirId(Integer.parseInt(params[0]));
            memoir.setMovieName(params[1]);
            Memoir existMemoir = db.memoirDao().findByID(memoir.getMemoirId());
            if (existMemoir == null) {
                long id = db.memoirDao().insert(memoir);
                return (id + " " + params[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String details) {
            if (details != null)
                Toast.makeText(MovieMemoirActivity.this, "Add to watchlist success", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MovieMemoirActivity.this, "Movie has existed", Toast.LENGTH_SHORT).show();
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Memoir> memoirs = db.memoirDao().getAll();
            if (!memoirs.isEmpty()) {
                String allMemoirs = "";
                for (Memoir memoir : memoirs) {
                    allMemoirs = memoir.getMemoirId() + memoir.getMovieName();
                }
                return allMemoirs;
            } else return "";
        }

        @Override
        protected void onPostExecute(String details) {
            System.out.println(details);
        }
    }
}