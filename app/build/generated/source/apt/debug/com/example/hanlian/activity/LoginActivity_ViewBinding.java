// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.rg_register_group, "field 'radioGroup'", RadioGroup.class);
    target.vendorView = Utils.findRequiredView(source, R.id.view_vendor, "field 'vendorView'");
    target.vendorButton = Utils.findRequiredViewAsType(source, R.id.rb_vendor, "field 'vendorButton'", RadioButton.class);
    target.merchantView = Utils.findRequiredView(source, R.id.view_merchant, "field 'merchantView'");
    target.merchantButton = Utils.findRequiredViewAsType(source, R.id.rb_merchant, "field 'merchantButton'", RadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioGroup = null;
    target.vendorView = null;
    target.vendorButton = null;
    target.merchantView = null;
    target.merchantButton = null;
  }
}
