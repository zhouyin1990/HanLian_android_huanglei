// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TakePicAdapter$TakePicViewHolder_ViewBinding implements Unbinder {
  private TakePicAdapter.TakePicViewHolder target;

  @UiThread
  public TakePicAdapter$TakePicViewHolder_ViewBinding(TakePicAdapter.TakePicViewHolder target,
      View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.iv_take_pic, "field 'imageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TakePicAdapter.TakePicViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
  }
}
