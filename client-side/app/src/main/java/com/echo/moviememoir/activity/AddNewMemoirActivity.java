package com.echo.moviememoir.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.echo.moviememoir.api.ImageAPI;
import com.echo.moviememoir.entity.Cinema;
import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.Memoir;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.DateStringUtils;
import com.echo.moviememoir.utils.ImgUtils;
import com.echo.moviememoir.utils.LocalStorage;
import com.echo.moviememoir.utils.RatingStarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddNewMemoirActivity extends AppCompatActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private SuperTextView movieNameStv;
    private SuperTextView releaseDateStv;
    private MaterialEditText watchDateTime;
    private MaterialSpinner cinemaMs;
    private Button addCinemaBtn;
    private MaterialEditText comment;
    private RatingBar ratingBar;
    private Button addMemoirBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_add_memoir);

        initView();
    }

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    private void initView() {
        titleBar = findViewById(R.id.add_memoir_title_bar);
        cinemaMs = findViewById(R.id.add_watch_cinema_text);
        addCinemaBtn = findViewById(R.id.add_new_cinema);
        watchDateTime = findViewById(R.id.add_watch_date_text);
        comment = findViewById(R.id.add_watch_comment_text);
        ratingBar = findViewById(R.id.add_memoir_star);
        addMemoirBtn = findViewById(R.id.add_memoir_button);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewMemoirActivity.this, MovieMemoirActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final Memoir memoir = LocalStorage.getMemoir();
        movieNameStv = findViewById(R.id.add_movie_name);
        releaseDateStv = findViewById(R.id.add_release_date);
        movieNameStv.setCenterString(memoir.getMovieName());
        releaseDateStv.setCenterString(DateStringUtils.date2String(memoir.getMovieReleaseDate()));
        watchDateTime.setFocusable(false);
        watchDateTime.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput();
                    switch (view.getId()) {
                        case R.id.add_watch_date_text:
                            showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    watchDateTime.setText(year + "-" + (month + 1) + "-" + day);
                                }
                            }, watchDateTime.getText().toString());
                            break;
                    }
                }
                return false;
            }
        });
        final Context context = getBaseContext();
        new AsyncTask<String, Void, Bitmap>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;
                String imageUrl = memoir.getImageUrl();
                bitmap = ImageAPI.getImage(imageUrl);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                movieNameStv.setRightIcon(ImgUtils.getDrawableFormBitmap(context, bitmap));
            }
        }.execute();

        String str = RestClient.findAllCinemas();
        JSONArray res = null;
        List<Cinema> cinemas = new ArrayList<>();
        List<String> items = new ArrayList<>();
        final HashMap<String, Integer> map = new HashMap<>();
        try {
            res = new JSONArray(str);
            for (int i = 0; i < res.length(); i++) {
                Cinema cinema = new Cinema();
                cinema.setCinemaId((Integer) res.getJSONObject(i).get("cinemaId"));
                cinema.setCinemaName((String) res.getJSONObject(i).get("cinemaName"));
                cinema.setLocation((String) res.getJSONObject(i).get("location"));
                cinema.setPostcode((String) res.getJSONObject(i).get("postcode"));
                cinemas.add(cinema);
                items.add(cinema.getCinemaName());
                map.put(cinema.getCinemaName(), cinema.getCinemaId());
            }
            cinemaMs.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addCinemaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewMemoirActivity.this, AddNewCinemaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addMemoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get latest cinemaId
                String oldMemoirs = RestClient.findAllMemoirs();
                JSONArray res = null;
                int oldMemoirId = 0;
                try {
                    res = new JSONArray(oldMemoirs);
                    for (int i = 0; i < res.length(); i++) {
                        oldMemoirId = Math.max(oldMemoirId, (int) (res.getJSONObject(i).get("memoirId")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    memoir.setMemoirId(oldMemoirId + 1);
                    if (!(watchDateTime.getText().toString().trim()).equals(""))
                        memoir.setWatchDate(DateStringUtils.string2Date(watchDateTime.getText().toString().trim()));
                    else
                        memoir.setWatchDate(new Date());
                    memoir.setCinemaId(new Cinema(map.get(cinemaMs.getSelectedItem())));
                    memoir.setCredentialsId(new Credential(LocalStorage.getUser().getUserId()));
                    memoir.setComment(comment.getText().toString().trim());
                    float score = ratingBar.getRating();
                    memoir.setScore(RatingStarUtils.star2Rating(score));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                RestClient.createMemoir(memoir);

                Toast.makeText(AddNewMemoirActivity.this, "Add success", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        try {
            year = Integer.parseInt(curDate.substring(0, curDate.indexOf("-")));
            month = Integer.parseInt(curDate.substring(curDate.indexOf("-") + 1, curDate.lastIndexOf("-"))) - 1;
            day = Integer.parseInt(curDate.substring(curDate.lastIndexOf("-") + 1, curDate.length()));
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);
        datePickerDialog.show();
    }


    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
