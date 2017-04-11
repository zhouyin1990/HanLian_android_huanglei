// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeihuActivity_ViewBinding implements Unbinder {
  private WeihuActivity target;

  @UiThread
  public WeihuActivity_ViewBinding(WeihuActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WeihuActivity_ViewBinding(WeihuActivity target, View source) {
    this.target = target;

    target.img_weihu_back = Utils.findRequiredViewAsType(source, R.id.iv_weihu_back, "field 'img_weihu_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WeihuActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_weihu_back = null;
  }
}
