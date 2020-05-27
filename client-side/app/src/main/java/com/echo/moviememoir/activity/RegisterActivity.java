package com.echo.moviememoir.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.User;
import com.echo.moviememoir.utils.DateString;
import com.echo.moviememoir.utils.MD5Helper;
import com.echo.moviememoir.restful.RestClient;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private EditText mEtLoginactivityDob;
    private EditText mEtLoginactivityName;
    private EditText mEtLoginactivitySurname;
    private RadioGroup mEtLoginactivityGender;
    private RadioButton mBtLoginactivityGender;
    private EditText mEtLoginactivityAddress;
    private EditText mEtLoginactivityState;
    private EditText mEtLoginactivityPostcode;
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_register);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        // initialize
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mEtLoginactivityName = findViewById(R.id.et_loginactivity_name);
        mEtLoginactivitySurname = findViewById(R.id.et_loginactivity_surname);
        mEtLoginactivityGender = findViewById(R.id.et_loginactivity_gendergroup);
        mEtLoginactivityAddress = findViewById(R.id.et_loginactivity_address);
        mEtLoginactivityState = findViewById(R.id.et_loginactivity_state);
        mEtLoginactivityPostcode = findViewById(R.id.et_loginactivity_postcode);
        mEtLoginactivityDob = findViewById(R.id.et_loginactivity_Dob);
        titleBar = findViewById(R.id.title_bar);

        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        // date picker
        mEtLoginactivityDob.setFocusable(false);
        mEtLoginactivityDob.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput();
                    switch (view.getId()) {
                        case R.id.et_loginactivity_Dob:
                            showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    mEtLoginactivityDob.setText(year + "-" + (month + 1) + "-" + day);
                                }
                            }, mEtLoginactivityDob.getText().toString());
                            break;
                    }
                }
                return false;
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_loginactivity_register:
                mBtLoginactivityGender = findViewById(mEtLoginactivityGender.getCheckedRadioButtonId());

                // get input
                String username = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                String name = mEtLoginactivityName.getText().toString().trim();
                String surname = mEtLoginactivitySurname.getText().toString().trim();
                String gender = mBtLoginactivityGender.getText().toString().trim();
                String address = mEtLoginactivityAddress.getText().toString().trim();
                String state = mEtLoginactivityState.getText().toString().trim();
                String postcode = mEtLoginactivityPostcode.getText().toString().trim();
                String dob = mEtLoginactivityDob.getText().toString().trim();

                // get latest userId and latest credentialsId
                String oldUsers = RestClient.findAllUsers();
                String oldCredentials = RestClient.findAllCredentials();
                JSONArray res = null;
                int oldUserId = 0;
                int oldCredentialsId = 0;
                boolean isExist = false;
                try {
                    res = new JSONArray(oldUsers);
                    for (int i = 0; i < res.length(); i++) {
                        oldUserId = Math.max(oldUserId, (int) (res.getJSONObject(i).get("userId")));
                    }
                    res = new JSONArray(oldCredentials);
                    for (int i = 0; i < res.length(); i++) {
                        if (res.getJSONObject(i).get("username").equals(username)) {
                            isExist = true;
                            break;
                        }
                        oldCredentialsId = Math.max(oldCredentialsId, (int) (res.getJSONObject(i).get("credentialsId")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // if user name has already exist, register fail
                if (isExist) {
                    Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT).show();
                } else {
                    // insert into User
                    User user = new User();
                    JSONObject credentialsDataJson = new JSONObject();
                    user.setAddress(address);
                    try {
                        user.setDob(DateString.string2Date(dob));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    user.setGender(!"female".equals(gender));
                    user.setName(name);
                    user.setPostcode(postcode);
                    user.setState(state);
                    user.setSurname(surname);
                    user.setUserId(oldUserId + 1);
                    RestClient.createUser(user);

                    // insert into Credentials
                    Credential credential = new Credential();
                    credential.setCredentialsId(oldCredentialsId + 1);
                    credential.setUsername(username);
                    try {
                        credential.setPassword(MD5Helper.getMD5(password));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    credential.setUserId(user);
                    RestClient.createCredential(credential);

                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
        }
    }
}
