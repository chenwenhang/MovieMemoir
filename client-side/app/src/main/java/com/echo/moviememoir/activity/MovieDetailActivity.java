package com.echo.moviememoir.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.echo.moviememoir.utils.RatingStarUtils;
import com.echo.moviememoir.viewmodel.MemoirViewModel;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private SuperTextView movieName;
    private SuperTextView movieDescription;
    private SuperTextView movieReleaseDate;
    private Button addWatchlistBtn;
    private Button addMemoirBtn;
    private Button postToQQZone;
    private RatingBar ratingBar;
    MemoirViewModel memoirViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_movie_detail);

        initView();
    }

    private void initView() {
        memoirViewModel = new ViewModelProvider(this).get(MemoirViewModel.class);
        memoirViewModel.initalizeVars(getApplication());
        movieName = findViewById(R.id.memoir_movie_name);
        movieDescription = findViewById(R.id.memoir_description);
        movieReleaseDate = findViewById(R.id.movie_release_date);
        titleBar = findViewById(R.id.memoir_title_bar);
        ratingBar = findViewById(R.id.memoir_star);
        addWatchlistBtn = findViewById(R.id.memoir_add_watchlist);
        addMemoirBtn = findViewById(R.id.memoir_add_memoir);
        postToQQZone = findViewById(R.id.memoir_post_qq);

        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        final Memoir memoir = LocalStorage.getMemoir();

        movieDescription.setCenterString(memoir.getDescription());
        movieName.setCenterString(memoir.getMovieName());
        movieReleaseDate.setCenterString(DateStringUtils.date2String(memoir.getMovieReleaseDate()));

        ratingBar.setRating(RatingStarUtils.rating2Star(memoir.getScore()));

        addWatchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoir.setAddDateTime(new Date(System.currentTimeMillis()));

                // The findById here is an asynchronous operation and needs to be monitored
                memoirViewModel.findByID(memoir.getMemoirId()).observe(MovieDetailActivity.this, new Observer<Memoir>() {
                    @Override
                    public void onChanged(@Nullable final Memoir existMemoir) {
                        if (existMemoir == null) {
                            memoirViewModel.insert(memoir);
                            Toast.makeText(MovieDetailActivity.this, "Add to watchlist success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MovieDetailActivity.this, "Movie has existed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        if (getIntent().getStringExtra("disableAdd") != null) {
            addWatchlistBtn.setEnabled(!(getIntent().getStringExtra("disableAdd")).equals("disable"));
        }

        addMemoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, AddNewMemoirActivity.class);
                startActivity(intent);
            }
        });

        postToQQZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = memoir.getMovieName();
                String summary = memoir.getMovieName() + " " + memoir.getDescription() + " I love this movie very much!";
                String url = "https://h5.qzone.qq.com/q/qzs/open/connect/widget/mobile/qzshare/index.html?page=qzshare.html&loginpage=loginindex.html&logintype=qzone&url=movieMemoir.com&sharesource=qzone&title="
                        + title + "&summary=" + summary;
                LocalStorage.setPostUrl(url);
                Intent intent = new Intent(MovieDetailActivity.this, PostToQQZoneActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View view) {

    }
}