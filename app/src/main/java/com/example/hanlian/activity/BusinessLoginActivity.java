package com.example.hanlian.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.LoaderTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.hanlian.R;
import com.example.hanlian.adapter.TakePicAdapter;
import com.example.hanlian.utils.CountDownButtonHelper;
import com.example.hanlian.utils.CustomHelper;
import com.example.hanlian.utils.KeyConstance;
import com.example.hanlian.utils.ValidatorUtils;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class BusinessLoginActivity extends TakePhotoActivity {

    @BindView(R.id.et_business_account)
    EditText accountEdit;
    @BindView(R.id.tv_business_address)
    TextView selectLocation;
    @BindView(R.id.et_business_detail)
    EditText addressEdit;
    @BindView(R.id.et_business_name)
    EditText nameEdit;
    @BindView(R.id.et_business_mobile)
    EditText mobileEdit;
    @BindView(R.id.et_business_code)
    EditText codeEdit;
    @BindView(R.id.btn_business_code)
    Button codeButton;
    @BindView(R.id.et_business_id_card)
    EditText idCardEdit;
    @BindView(R.id.et_business_contact)
    EditText contactManEdit;
    @BindView(R.id.et_business_contact_phone)
    EditText contactPhoneEdit;
    @BindView(R.id.et_business_fax)
    EditText faxEdit;
    @BindView(R.id.et_business_tel)
    EditText telEdit;
    @BindView(R.id.et_business_password)
    EditText passwordEdit;
    @BindView(R.id.et_business_password_again)
    EditText passwordAgainEdit;
    @BindView(R.id.rv_business_credential)
    RecyclerView zhengShuRececlerView;
    @BindView(R.id.rv_business_id_card)
    RecyclerView idCardRecyclerView;
    @BindView(R.id.rv_business_shop)
    RecyclerView shopPicRecyclerView;
    @BindView(R.id.btn_business_register)
    Button registerButton;
    @BindView(R.id.iv_business_back)
    ImageView backImage;
    @BindView(R.id.cb_business_confirm)
    CheckBox confirmCheck;
    @BindView(R.id.ll_business_confirm)
    LinearLayout confirmSpace;

    @BindView(R.id.wenzi)
    TextView TV_WENZI ;

    private AlertView alertView;
    private CustomHelper customHelper;
    private int choosePicState = 0;                 //选择图片的状态 0-添加图片;1-更换图片
    private int choosePicRecycler = 0;              //选择图片的位置 0-证书 1-身份证 2-门店
    private int choosePicPosition = 0;              //更换图片时的index
    private TakePicAdapter zhengShuAdapter;
    private TakePicAdapter idCardAdapter;
    private TakePicAdapter shopAdapter;
    private String token;
    private boolean confirm = false;
    private Dialog loadingDialog;
    private CountDownButtonHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_business_login, null);
        setContentView(contentView);
        ButterKnife.bind(this);

        customHelper = CustomHelper.of(contentView);

        getToken();
        initListener();
    }
     //TODO  刷新token
    private void getToken() {
        OkHttpUtils.get().addParams("account", "121212").addParams("password", "121212").url(KeyConstance.AdminLoginUrl)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(BusinessLoginActivity.this, "请求Token失败 - " + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("ErrorCode");
                    if (errorCode == 0) {
                        token = jsonObject.getString("Token");
                        //   Toast.makeText(VendorLoginActivity.this, "获取Token成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BusinessLoginActivity.this, "请求成功,获取失败失败-" + errorCode,
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initListener() {

        TV_WENZI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(BusinessLoginActivity.this ,AgreeActivity.class);
                startActivity(intent);
            }
        });

        //返回图标
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取验证码
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileEdit.getText().toString().trim();
                boolean isture = ValidatorUtils.isMobile(mobile);
                if (mobile!=null && isture==true) {
                    TimeHelp();//倒计时60s
                    OkHttpUtils.get().addParams("phone", mobile).url(KeyConstance.sendApplicationCode).build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Toast.makeText(BusinessLoginActivity.this, "发送验证码失败" + e.toString(),
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        int errorCode = new JSONObject(response).getInt("ErrorCode");
                                        if (errorCode == 0) {
                                            Toast.makeText(BusinessLoginActivity.this, "验证码已发送",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(BusinessLoginActivity.this, "请求成功，然而获取验证码失败",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    showAlertView("请填写z正确的手机号码");
                }
            }
        });
        //注册
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (TextUtils.isEmpty(token)) {
                    showAlertView("登陆失败 - token为空");
                    return;
                }
                String account = accountEdit.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    showAlertView("请填写用户名");
                    accountEdit.requestFocus();
                    return;
                }
                String address = addressEdit.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    showAlertView("请填写地址");
                    addressEdit.requestFocus();
                    return;
                }
                String name = nameEdit.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showAlertView("请填写用户姓名");
                    nameEdit.requestFocus();
                    return;
                }
                String mobile = mobileEdit.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    showAlertView("请填写手机号码");
                    mobileEdit.requestFocus();
                    return;
                }
                String code = codeEdit.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    showAlertView("请填写验证码");
                    codeEdit.requestFocus();
                    return;
                }
                String idCardNumber = idCardEdit.getText().toString().trim();
                if (TextUtils.isEmpty(idCardNumber)) {
                    showAlertView("请填写身份证号码");
                    idCardEdit.requestFocus();
                    return;
                }
                String contactName = contactManEdit.getText().toString().trim();
                if (TextUtils.isEmpty(contactName)) {
                    showAlertView("请填写紧急联系人");
                    contactManEdit.requestFocus();
                    return;
                }
                String contactPhone = contactPhoneEdit.getText().toString().trim();
                if (TextUtils.isEmpty(contactPhone)) {
                    showAlertView("请填写紧急联系电话");
                    contactPhoneEdit.requestFocus();
                    return;
                }
                String fax = faxEdit.getText().toString().trim();
                if (TextUtils.isEmpty(fax)) {
                    showAlertView("请填写传真");
                    faxEdit.requestFocus();
                    return;
                }
                String tel = telEdit.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    showAlertView("请填写固话");
                    telEdit.requestFocus();
                    return;
                }
                String password = passwordEdit.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    showAlertView("请填写密码");
                    passwordEdit.requestFocus();
                    return;
                }
                String passwordAgain = passwordAgainEdit.getText().toString().trim();
                if (TextUtils.isEmpty(passwordAgain)) {
                    showAlertView("请再次填写密码");
                    passwordAgainEdit.requestFocus();
                    return;
                }
                if (!TextUtils.equals(password, passwordAgain)) {
                    showAlertView("两次密码不一致，请检查");
                    passwordAgainEdit.requestFocus();
                    return;
                }
                ArrayList<String> idCards = idCardAdapter.getItems();
                if (idCards.size() == 0) {
                    Toast.makeText(BusinessLoginActivity.this, "请上传身份证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> zhengShu = zhengShuAdapter.getItems();
                if (zhengShu.size() == 0) {
                    Toast.makeText(BusinessLoginActivity.this, "请上传营业执照", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> shops = shopAdapter.getItems();
                if (shops.size() == 0) {
                    Toast.makeText(BusinessLoginActivity.this, "请上传门店照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirm) {
                    Toast.makeText(BusinessLoginActivity.this, "请阅读并同意相关条款", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("phone", mobile);
                params.put("code", code);
                params.put("shopname", account);
                params.put("address", address);
                // TODO: 2017/4/11   待获取动态的 经纬度
                params.put("lon", "55.00");
                params.put("lat", "55.00");
                params.put("pwd", password);
                params.put("pwdagain", passwordAgain);
                params.put("cardno", idCardNumber);
                params.put("personname", name);
                params.put("contactname", contactName);
                params.put("contactphone", contactPhone);
                params.put("telephone", tel);
                params.put("pax", fax);

                HashMap<String, File> license = new HashMap<>();
                for (String path : zhengShu) {
                    File file = new File(path);
                    license.put(file.getName(), file);
                }
                HashMap<String, File> card = new HashMap<>();
                for (String path : idCards) {
                    File file = new File(path);
                    card.put(file.getName(), file);
                }
                HashMap<String, File> store = new HashMap<>();
                for (String path : shops) {
                    File file = new File(path);
                    store.put(file.getName(), file);
                }
                showLoadingView();
                OkHttpUtils.post().params(params).files("license", license).files("card", card).files("store", store)
                        .url(KeyConstance.USER_APPLICATION).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingView();
                        Toast.makeText(BusinessLoginActivity.this, "上传请求失败 - " + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int errorCode = jsonObject.getInt("ErrorCode");
                            String result = jsonObject.getString("Result");
                            if (errorCode == 0) {
                                Toast.makeText(BusinessLoginActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                //TODO
                                ShowDialog(result);
                            } else {
                                Toast.makeText(BusinessLoginActivity.this, "上传失败 - " + errorCode,
                                        Toast.LENGTH_SHORT).show();

                            }
                            hideLoadingView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        //选择位置
        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessLoginActivity.this, SearchLocationActivity.class);
                startActivityForResult(intent, KeyConstance.REQUEST_CODE_LOCATION);
            }
        });

        //选择阅读条款
        confirmSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm = !confirm;
                confirmCheck.setChecked(confirm);
            }
        });
        //设置证书选择图片recyclerView
        LinearLayoutManager zhengShuLayout = new LinearLayoutManager(this);
        zhengShuLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        zhengShuRececlerView.setLayoutManager(zhengShuLayout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_horizontal));
        zhengShuRececlerView.addItemDecoration(dividerItemDecoration);
        zhengShuAdapter = new TakePicAdapter(this);
        zhengShuAdapter.setItemClickListener(new TakePicAdapter.OnItemClickListener() {
            @Override
            public void itemClick2Add() {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 0;
                        choosePicState = 0;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }

            @Override
            public void itemClick2Change(int position) {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 0;
                        choosePicState = 1;
                        choosePicPosition = position;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }
        });
        zhengShuRececlerView.setAdapter(zhengShuAdapter);

        //设置身份证选择图片的adapter
        idCardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        idCardRecyclerView.addItemDecoration(dividerItemDecoration);
        idCardAdapter = new TakePicAdapter(this);
        idCardAdapter.setItemClickListener(new TakePicAdapter.OnItemClickListener() {
            @Override
            public void itemClick2Add() {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 1;
                        choosePicState = 0;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }

            @Override
            public void itemClick2Change(int position) {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 1;
                        choosePicState = 1;
                        choosePicPosition = position;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }
        });
        idCardRecyclerView.setAdapter(idCardAdapter);

        //设置门店选择图片的adapter
        shopPicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        shopPicRecyclerView.addItemDecoration(dividerItemDecoration);
        shopAdapter = new TakePicAdapter(this);
        shopAdapter.setItemClickListener(new TakePicAdapter.OnItemClickListener() {
            @Override
            public void itemClick2Add() {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 2;
                        choosePicState = 0;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }

            @Override
            public void itemClick2Change(int position) {
                new AlertView("上传图片", null, "取消", null, new String[]{"拍照", "从相册中选择"},
                        BusinessLoginActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        //进入选择图片页面
                        choosePicRecycler = 2;
                        choosePicState = 1;
                        choosePicPosition = position;
                        customHelper.onClick(getTakePhoto(), position, 1);
                    }
                }).show();
            }
        });
        shopPicRecyclerView.setAdapter(shopAdapter);
    }

    private void showAlertView(String message) {
        alertView = new AlertView("提示", message, "确定", null, null,
                this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                alertView.dismiss();
            }
        });
        alertView.show();
    }

    //显示登录成功提示框
    private void ShowDialog(String result) {
        final Dialog dialog = new Dialog(BusinessLoginActivity.this, R.style.MyDialog);
        View view1 = LayoutInflater.from(BusinessLoginActivity.this).inflate(R.layout.dialog_score2, null);
        dialog.setContentView(view1);
        ((TextView) view1.findViewById(R.id.tv_sure)).setText("确定");




        ((TextView) view1.findViewById(R.id.tv_account)).setText(result);
        view1.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    //TODO  成功后要做的操作

                    Intent intent= new Intent(BusinessLoginActivity.this ,LoginActivity.class);
                    startActivity(intent);

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    //显示加载框
    public void showLoadingView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_loading, null);// 得到加载view

        // 创建自定义样式dialog
        if (loadingDialog == null) {
            loadingDialog = new Dialog(this, R.style.dialog_loaing);
            loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
            loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
            loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
            /** *将显示Dialog的方法封装在这里面 */
            Window window = loadingDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setAttributes(lp);
        }
        loadingDialog.show();
    }

    //隐藏提示框
    public void hideLoadingView() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KeyConstance.REQUEST_CODE_LOCATION && resultCode == RESULT_OK) {
            String bigAddress = data.getStringExtra("bigAddress");
            String detailAddress = data.getStringExtra("detailAddress");
            selectLocation.setText(bigAddress);
            addressEdit.setText(detailAddress);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (choosePicState == 0 && choosePicRecycler == 0) {
            zhengShuAdapter.setItem(result.getImage().getOriginalPath());
        } else if (choosePicState == 1 && choosePicRecycler == 0) {
            zhengShuAdapter.updateItem(choosePicPosition, result.getImage().getOriginalPath());
        } else if (choosePicState == 0 && choosePicRecycler == 1) {
            idCardAdapter.setItem(result.getImage().getOriginalPath());
        } else if (choosePicState == 1 && choosePicRecycler == 1) {
            idCardAdapter.updateItem(choosePicPosition, result.getImage().getOriginalPath());
        } else if (choosePicState == 0 && choosePicRecycler == 2) {
            shopAdapter.setItem(result.getImage().getOriginalPath());
        } else if (choosePicState == 1 && choosePicRecycler == 2) {
            shopAdapter.updateItem(choosePicPosition, result.getImage().getOriginalPath());
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(this, "选择图片失败-" + msg, Toast.LENGTH_SHORT).show();
    }


    private void TimeHelp(){
        helper = new CountDownButtonHelper(codeButton, "已发送",60, 1);
        codeButton.setEnabled(false);

        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

            @Override
            public void finish() {
                codeButton.setEnabled(true);
                codeButton.setText("发送验证码");
            }
        });
        helper.start();

      OkHttpUtils.get().addParams("","").build().execute(new StringCallback() {
          @Override
          public void onError(Call call, Exception e, int id) {

          }

          @Override
          public void onResponse(String response, int id) {

          }
      });
    }
}