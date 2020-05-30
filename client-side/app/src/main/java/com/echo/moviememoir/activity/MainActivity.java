package com.echo.moviememoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.Credential;
import com.echo.moviememoir.entity.User;
import com.echo.moviememoir.utils.LocalStorage;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // initialize
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.bt_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        titleBar = findViewById(R.id.main_title_bar);

        titleBar.disableLeftView();
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        User user = new User();
        user.setUserId(1);
        user.setName("Wenhang");
        LocalStorage.setUser(user);
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
        finish();


//        switch (view.getId()) {
//            // Jump to the registration interface
//            case R.id.bt_loginactivity_register:
//                startActivity(new Intent(this, RegisterActivity.class));
//                finish();
//                break;
//
//            case R.id.bt_loginactivity_login:
//                String name = mEtLoginactivityUsername.getText().toString().trim();
//                String password = mEtLoginactivityPassword.getText().toString().trim();
//                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
//                    boolean match = false;
//                    String str = RestClient.findByUsername(name);
//                    JSONArray res = null;
//                    String pwd_server = "";
//                    String name_server = "";
//                    try {
//                        res = new JSONArray(str);
//                        password = MD5Helper.getMD5(password);
//                        pwd_server = (String) (res.getJSONObject(0).get("password"));
//                        name_server = (String) (res.getJSONObject(0).get("username"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    match = name.equals(name_server) && password.equals(pwd_server);
//                    if (match) {
//                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
//
//                        // get user's name
//                        try {
//                            assert res != null;
//                            Gson gson = new Gson();
//                            JSONObject json = (JSONObject) res.getJSONObject(0).get("userId");
//                            String jsonString = RestClient.findByUserId((Integer) json.get("userId"));
//                            User obj = gson.fromJson(jsonString, User.class);
//                            LocalStorage.setUser(obj);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Intent intent = new Intent(this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(this, "Username or password is incorrect, please re-enter", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, "Please enter your username or password", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
    }
}
