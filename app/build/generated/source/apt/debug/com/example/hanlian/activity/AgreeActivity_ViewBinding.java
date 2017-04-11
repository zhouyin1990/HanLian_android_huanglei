// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AgreeActivity_ViewBinding implements Unbinder {
  private AgreeActivity target;

  @UiThread
  public AgreeActivity_ViewBinding(AgreeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AgreeActivity_ViewBinding(AgreeActivity target, View source) {
    this.target = target;

    target.webview = Utils.findRequiredViewAsType(source, R.id.webView1, "field 'webview'", WebView.class);
    target.img_back = Utils.findRequiredViewAsType(source, R.id.img_back, "field 'img_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AgreeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.webview = null;
    target.img_back = null;
  }
}
