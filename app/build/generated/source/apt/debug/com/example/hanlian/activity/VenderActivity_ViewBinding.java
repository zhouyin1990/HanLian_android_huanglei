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

public class VenderActivity_ViewBinding implements Unbinder {
  private VenderActivity target;

  @UiThread
  public VenderActivity_ViewBinding(VenderActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VenderActivity_ViewBinding(VenderActivity target, View source) {
    this.target = target;

    target.TV_ZHUCE1 = Utils.findRequiredViewAsType(source, R.id.textView2, "field 'TV_ZHUCE1'", TextView.class);
    target.TV_WEIHU2 = Utils.findRequiredViewAsType(source, R.id.textView3, "field 'TV_WEIHU2'", TextView.class);
    target.img_wiehu = Utils.findRequiredViewAsType(source, R.id.imageView2, "field 'img_wiehu'", ImageView.class);
    target.IMG_zhuce = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'IMG_zhuce'", ImageView.class);
    target.mg_businie_back = Utils.findRequiredViewAsType(source, R.id.mg_businie_back, "field 'mg_businie_back'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VenderActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.TV_ZHUCE1 = null;
    target.TV_WEIHU2 = null;
    target.img_wiehu = null;
    target.IMG_zhuce = null;
    target.mg_businie_back = null;
  }
}
