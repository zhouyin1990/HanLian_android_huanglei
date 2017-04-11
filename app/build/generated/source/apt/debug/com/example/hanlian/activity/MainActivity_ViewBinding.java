// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.accountEdit = Utils.findRequiredViewAsType(source, R.id.et_main_account, "field 'accountEdit'", EditText.class);
    target.passwordEdit = Utils.findRequiredViewAsType(source, R.id.et_main_password, "field 'passwordEdit'", EditText.class);
    target.forgetPasswordText = Utils.findRequiredViewAsType(source, R.id.tv_forget_password, "field 'forgetPasswordText'", TextView.class);
    target.loginButton = Utils.findRequiredViewAsType(source, R.id.btn_main_login, "field 'loginButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accountEdit = null;
    target.passwordEdit = null;
    target.forgetPasswordText = null;
    target.loginButton = null;
  }
}
