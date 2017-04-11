// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FindActivity_ViewBinding implements Unbinder {
  private FindActivity target;

  @UiThread
  public FindActivity_ViewBinding(FindActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FindActivity_ViewBinding(FindActivity target, View source) {
    this.target = target;

    target.ed_phone_find = Utils.findRequiredViewAsType(source, R.id.ed_phone_find, "field 'ed_phone_find'", EditText.class);
    target.btn_usename = Utils.findRequiredViewAsType(source, R.id.btn_yonghuming, "field 'btn_usename'", Button.class);
    target.btn_mima = Utils.findRequiredViewAsType(source, R.id.btn_mima, "field 'btn_mima'", Button.class);
    target.img_back = Utils.findRequiredViewAsType(source, R.id.img_findpasswordback, "field 'img_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FindActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ed_phone_find = null;
    target.btn_usename = null;
    target.btn_mima = null;
    target.img_back = null;
  }
}
