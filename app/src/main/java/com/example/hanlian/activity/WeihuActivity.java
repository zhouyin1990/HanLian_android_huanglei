package com.example.hanlian.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.hanlian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeihuActivity extends AppCompatActivity {

    @BindView(R.id.iv_weihu_back)
    ImageView img_weihu_back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weihu);
        ButterKnife.bind(this);

        initliser();
    }

    private void initliser() {
        img_weihu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if(keyCode==KeyEvent.KEYCODE_BACK)
      {
         finish();
      }
        return super.onKeyDown(keyCode, event);
    }
}
