// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchLocationAdapter$LocationViewHolder_ViewBinding implements Unbinder {
  private SearchLocationAdapter.LocationViewHolder target;

  @UiThread
  public SearchLocationAdapter$LocationViewHolder_ViewBinding(SearchLocationAdapter.LocationViewHolder target,
      View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.address, "field 'address'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchLocationAdapter.LocationViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.address = null;
  }
}
