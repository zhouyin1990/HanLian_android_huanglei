package com.example.hanlian.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.hanlian.R;
import com.example.hanlian.utils.KeyConstance;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_main_account)
    EditText accountEdit;
    @BindView(R.id.et_main_password)
    EditText passwordEdit;
    @BindView(R.id.tv_forget_password)
    TextView forgetPasswordText;
    @BindView(R.id.btn_main_login)
    Button loginButton;

    private AlertView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
    }
    private void initListener() {

        //设置下划线
        forgetPasswordText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //找回用户名和密码
                Intent intent =new Intent(MainActivity.this , FindActivity.class);
                startActivity(intent);

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    showAlertView("请填写用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showAlertView("请填写密码");
                    return;
                }

                //登录请求
                OkHttpUtils.get().addParams("account", account).addParams("password", password).url(KeyConstance.AdminLoginUrl)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "登陆失败-" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.getString("Token");
                            int errorCode = new JSONObject(response).getInt("ErrorCode");
                            if (errorCode == 0) {
                                Toast.makeText(MainActivity.this,
                                        "登录成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("hanlian", MODE_PRIVATE);
                                sp.edit().putString("token", token).commit();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void showAlertView(String message) {
        alertView = new AlertView("提示", message, "确定", null, null,
                MainActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                alertView.dismiss();
            }
        });
        alertView.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (alertView != null && alertView.isShowing()) {
            alertView.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}