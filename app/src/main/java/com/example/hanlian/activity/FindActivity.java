package com.example.hanlian.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hanlian.R;
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
    ImageView img_back ;


    private String ed_phone;
    private boolean ismobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_find, null);
        setContentView(contentView);
     // setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        intilisner();
    }

    private void intilisner() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_usename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_phone = ed_phone_find.getText().toString().trim();
                ismobile = ValidatorUtils.isMobile(ed_phone);
                if(ed_phone !=null && ismobile ==true)
                {
                      getuseCode();
                      showdilog();
                }else
                {
                    Toast.makeText(FindActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_mima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_phone !=null && ismobile ==true)
                {
                    getMimaCode();
                    showdilog();
                }else
                {
                    Toast.makeText(FindActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showdilog() {
        final Dialog dialog = new Dialog(FindActivity.this, R.style.MyDialog);
        View view1 = LayoutInflater.from(FindActivity.this).inflate(R.layout.finddialog, null);
        dialog.setContentView(view1);
        ((Button) view1.findViewById(R.id.no)).setText("取消");
        ((Button) view1.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        ((Button) view1.findViewById(R.id.yes)).setText("确定");
        ((Button) view1.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private  void getuseCode()
     {
         
         //// TODO: 2017/4/11   待添加用户名发送验证码url
       OkHttpUtils.get().addParams("phone",ed_phone).url("").build().execute(new StringCallback() {
           @Override
           public void onError(Call call, Exception e, int id) {
               Toast.makeText(FindActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(String response, int id) {
               try {
                   int errorCode = new JSONObject(response).getInt("ErrorCode");
                   if(errorCode==0)
                   {
                       Toast.makeText(FindActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                   }

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });

   }

    private void getMimaCode()
    {

        // TODO: 2017/4/11   待添加发送PASSWORD验证码url
        OkHttpUtils.get().addParams("phone",ed_phone).url(" ").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(FindActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int errorCode = new JSONObject(response).getInt("ErrorCode");
                    if(errorCode==0)
                    {
                        Toast.makeText(FindActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
