package com.echo.moviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class MovieMemoirActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;

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
        titleBar = findViewById(R.id.memoir_title_bar);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieMemoirActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClick(View view) {

    }
}
