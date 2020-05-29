package com.echo.moviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Cinema;
import com.echo.moviememoir.restful.RestClient;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import org.json.JSONArray;

public class AddNewCinemaActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private MaterialEditText cinemaNameText;
    private MaterialEditText locationText;
    private MaterialEditText postcodeText;
    private Button addCinemaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_add_cinema);
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.add_cinema_title_bar);
        cinemaNameText = findViewById(R.id.add_cinema_name);
        locationText = findViewById(R.id.add_cinema_location);
        postcodeText = findViewById(R.id.add_cinema_postcode);
        addCinemaBtn = findViewById(R.id.add_cinema_button);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewCinemaActivity.this, AddNewMemoirActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addCinemaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get latest cinemaId
                String oldCinemas = RestClient.findAllCinemas();
                JSONArray res = null;
                int oldCinemaId = 0;
                try {
                    res = new JSONArray(oldCinemas);
                    for (int i = 0; i < res.length(); i++) {
                        oldCinemaId = Math.max(oldCinemaId, (int) (res.getJSONObject(i).get("cinemaId")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Cinema cinema = new Cinema();
                cinema.setCinemaName(cinemaNameText.getText().toString().trim());
                cinema.setPostcode(postcodeText.getText().toString().trim());
                cinema.setLocation(locationText.getText().toString().trim());
                cinema.setCinemaId(oldCinemaId + 1);
                RestClient.createCinema(cinema);

                Toast.makeText(AddNewCinemaActivity.this, "Add success", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onClick(View view) {

    }
}
