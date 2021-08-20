package com.com.blog.view.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.com.blog.R;
import com.com.blog.config.SessionUser;
import com.com.blog.controller.AuthController;
import com.com.blog.controller.dto.CMRespDto;
import com.com.blog.controller.dto.LoginDto;
import com.com.blog.model.User;
import com.com.blog.view.InitActivity;
import com.com.blog.view.post.PostListActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements InitActivity {
    private static final String TAG = "LoginActivity";
    private LoginActivity mContext = LoginActivity.this;
    private MaterialButton btnLogin;
    private EditText etUsername, etPassword;
    private TextView tvJoin;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: ");
        init();
        initLr();
        initSetting();
    }

    @Override
    public void init() {
        authController = new AuthController();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvJoin = findViewById(R.id.tvJoin);
    }

    @Override
    public void initLr() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim(); //trim으로 공백제거까지 해줘야함
            String password = etPassword.getText().toString().trim();
            authController.login(new LoginDto(username,password)).enqueue(new Callback<CMRespDto<User>>() {
                @Override
                public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                   CMRespDto<User> cm = response.body();
                    Headers headers = response.headers();

                    Log.d(TAG, "onResponse: "+cm.getCode());
                    Log.d(TAG, "onResponse: "+cm.getData().getUsername());
                    Log.d(TAG, "onResponse: 토큰 "+headers.get("Authorization"));
                    SessionUser.user = cm.getData();
                    SessionUser.token = headers.get("Authorization");

                    Intent intent = new Intent(mContext, PostListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                    t.printStackTrace();
                    //토스트
                }
            });
        });
    }

    @Override
    public void initSetting() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 시작");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 다시 그려짐");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 일시정지");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 멈춤");
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 종료 ");
    }
}