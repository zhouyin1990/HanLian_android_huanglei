package com.example.hanlian.activity;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hanlian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenderActivity extends AppCompatActivity {


    @BindView(R.id.textView2)
    TextView TV_ZHUCE1 ;
    @BindView(R.id.textView3)
    TextView TV_WEIHU2 ;
    @BindView(R.id.imageView2)
    ImageView img_wiehu ;
    @BindView(R.id.imageView)
    ImageView IMG_zhuce ;

    @BindView(R.id.mg_businie_back)
    ImageView mg_businie_back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);
        ButterKnife.bind(this);
        initliser();

    }

    private void initliser() {
        //注册
        TV_ZHUCE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VenderActivity.this ,VendorLoginActivity.class);
                startActivity(intent );
            }
        });
        //维护
        TV_WEIHU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VenderActivity.this ,LoginActivity.class);
                startActivity(intent );

            }
        });
           //注册
        IMG_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VenderActivity.this ,VendorLoginActivity.class);
                startActivity(intent );
            }
        });
        //维护
        img_wiehu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VenderActivity.this ,LoginActivity.class);
                startActivity(intent );
            }
        });
        mg_businie_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
