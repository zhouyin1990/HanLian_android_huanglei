// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BusineActivity_ViewBinding implements Unbinder {
  private BusineActivity target;

  @UiThread
  public BusineActivity_ViewBinding(BusineActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BusineActivity_ViewBinding(BusineActivity target, View source) {
    this.target = target;

    target.img_b1 = Utils.findRequiredViewAsType(source, R.id.img_b1, "field 'img_b1'", ImageView.class);
    target.IMG_4 = Utils.findRequiredViewAsType(source, R.id.img_4, "field 'IMG_4'", ImageView.class);
    target.TV_ZHUCE = Utils.findRequiredViewAsType(source, R.id.textView4, "field 'TV_ZHUCE'", TextView.class);
    target.TV_WEIHU = Utils.findRequiredViewAsType(source, R.id.textView5, "field 'TV_WEIHU'", TextView.class);
    target.IMG_BACK = Utils.findRequiredViewAsType(source, R.id.img_busine_back, "field 'IMG_BACK'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BusineActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_b1 = null;
    target.IMG_4 = null;
    target.TV_ZHUCE = null;
    target.TV_WEIHU = null;
    target.IMG_BACK = null;
  }
}
