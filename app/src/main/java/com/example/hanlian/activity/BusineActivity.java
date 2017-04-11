package com.example.hanlian.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hanlian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusineActivity extends AppCompatActivity {

    @BindView(R.id.img_b1)
    ImageView  img_b1 ;
    @BindView(R.id.img_4)
    ImageView IMG_4 ;
    @BindView(R.id.textView4)
    TextView TV_ZHUCE ;
    @BindView(R.id.textView5)
    TextView TV_WEIHU ;

    @BindView(R.id.img_busine_back)
    ImageView IMG_BACK ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busine);
        ButterKnife.bind(this);
        initlinser();


    }

    private void initlinser() {
        //注册
        img_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BusineActivity.this ,BusinessLoginActivity.class);
                startActivity(intent );
            }
        });

      // 维护
        IMG_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BusineActivity.this ,LoginActivity.class);
                startActivity(intent );
            }
        });
      //注册
        TV_ZHUCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BusineActivity.this ,BusinessLoginActivity.class);
                startActivity(intent );
            }
        });

         //维护
        TV_WEIHU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BusineActivity.this ,LoginActivity.class);
                startActivity(intent );
            }
        });
        IMG_BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
