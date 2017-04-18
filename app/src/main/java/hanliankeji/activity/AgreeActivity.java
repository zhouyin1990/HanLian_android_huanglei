package hanliankeji.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import hanliankeji.R;
import hanliankeji.utils.KeyConstance;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreeActivity extends AppCompatActivity {

    @BindView(R.id.webView1)
     WebView  webview ;
    @BindView(R.id.img_back)
    ImageView img_back ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        ButterKnife.bind(this);
        initlisener();
        intiwebview();

    }

    private void intiwebview() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //缩放
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.loadUrl(KeyConstance.weburl);

    }

    private void initlisener() {
      img_back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });



    }
}
