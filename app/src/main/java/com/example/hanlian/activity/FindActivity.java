package com.example.hanlian.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanlian.R;
import com.example.hanlian.utils.KeyConstance;
import com.example.hanlian.utils.ValidatorUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class FindActivity extends AppCompatActivity {

    @BindView(R.id.ed_phone_find)
    EditText ed_phone_find;
    @BindView(R.id.btn_yonghuming)
    Button btn_usename;
    @BindView(R.id.btn_mima)
    Button btn_mima;
    @BindView(R.id.img_findpasswordback)
    ImageView img_back;

    private String ed_phone;
    private boolean ismobile;
    private int codeType = 0;//type=0 - 找回用户名;type=1 - 找回密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_find, null);
        setContentView(contentView);
        // setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_usename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeType = 0;
                ed_phone = ed_phone_find.getText().toString().trim();
                ismobile = ValidatorUtils.isMobile(ed_phone);
                if (ed_phone != null && ismobile == true) {
                    getUserCode();

                } else {
                    Toast.makeText(FindActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_mima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeType = 1;
                ed_phone = ed_phone_find.getText().toString().trim();
                ismobile = ValidatorUtils.isMobile(ed_phone);
                if (ed_phone != null && ismobile == true) {
                    getMimaCode();
                } else {
                    Toast.makeText(FindActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //输入验证码的dialog
    private void showDialog() {
        final Dialog dialog = new Dialog(FindActivity.this, R.style.MyDialog);
        View view = LayoutInflater.from(FindActivity.this).inflate(R.layout.finddialog, null);

        dialog.setContentView(view);
        final EditText codeEdit = (EditText) view.findViewById(R.id.edit_findyzm);
        ((Button) view.findViewById(R.id.no)).setText("取消");
        ((Button) view.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        ((Button) view.findViewById(R.id.yes)).setText("确定");
        ((Button) view.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String code = codeEdit.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(FindActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (codeType == 0) {
                    getUserName(code);
                } else if (codeType == 1){
                    showPasswordDialog(code);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //重置密码的dialog
    private void showPasswordDialog(final String code) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.item_resetting_password, null);
        dialog.setContentView(view);
        view.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText passwordEdit = (EditText) view.findViewById(R.id.et_password);
        final EditText passwordAgainEdit = (EditText) view.findViewById(R.id.et_password_again);
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEdit.getText().toString().trim();
                String passwordAgain = passwordAgainEdit.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(FindActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordAgain)) {
                    Toast.makeText(FindActivity.this, "请再次填写密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(password, passwordAgain)) {
                    Toast.makeText(FindActivity.this, "两次密码不一致，请检查", Toast.LENGTH_SHORT).show();
                    return;
                }
                resettingPassword(code, password, passwordAgain);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //重置密码
    private void resettingPassword(String code, String password, String passwordAgain) {
        OkHttpUtils.get().addParams("phone", ed_phone_find.getText().toString().trim()).addParams("code", code)
                .addParams("newpassword", password).addParams("confirmpassword", passwordAgain)
                .url(KeyConstance.SALE_RESETTING_PASSWORD).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(FindActivity.this, "重置密码失败 - " + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("ErrorCode");
                    if (errorCode == 0) {
                        Toast.makeText(FindActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FindActivity.this, "请求成功，重置失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取用户名
    private void getUserName(String code) {
        OkHttpUtils.get().addParams("phone", ed_phone_find.getText().toString().trim()).addParams("code", code)
                .url(KeyConstance.BACK_ACCOUNT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(FindActivity.this, "获取用户名失败" + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("ErrorCode");
                    if (errorCode == 0) {
                        String result = jsonObject.getString("Result");
                        showUserNameDialog(result);
                    } else {
                        Toast.makeText(FindActivity.this, "请求成功，获取失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //显示用户名的Dialog
    private void showUserNameDialog(String result) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_score2, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.tv_sure)).setText("确定");
        ((TextView) view.findViewById(R.id.textView1)).setText("找回成功,请牢记用户名:");
        ((TextView) view.findViewById(R.id.tv_account)).setText(result);
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    finish();
                }
            }
        });
        dialog.show();
    }

    //获取用户名的验证码
    private void getUserCode() {
        OkHttpUtils.get().addParams("phone", ed_phone).url(KeyConstance.SEND_SALE_ACCOUNT_CODE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(FindActivity.this, "获取验证码失败" + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int errorCode = new JSONObject(response).getInt("ErrorCode");
                    if (errorCode == 0) {
                        Toast.makeText(FindActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        showDialog();
                    } else {
                        Toast.makeText(FindActivity.this, "请求成功，发送失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取密码的验证码
    private void getMimaCode() {
        OkHttpUtils.get().addParams("phone", ed_phone).url(KeyConstance.SEND_SALE_PASSWORD_CODE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Toast.makeText(FindActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int errorCode = new JSONObject(response).getInt("ErrorCode");
                    if (errorCode == 0) {
                        Toast.makeText(FindActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        showDialog();
                    }else
                    {
                        Toast.makeText(FindActivity.this, "请求成功，发送失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
