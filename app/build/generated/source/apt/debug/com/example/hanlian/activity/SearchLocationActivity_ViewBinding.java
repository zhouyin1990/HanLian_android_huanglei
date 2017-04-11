// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.amap.api.maps.MapView;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchLocationActivity_ViewBinding implements Unbinder {
  private SearchLocationActivity target;

  @UiThread
  public SearchLocationActivity_ViewBinding(SearchLocationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchLocationActivity_ViewBinding(SearchLocationActivity target, View source) {
    this.target = target;

    target.back = Utils.findRequiredViewAsType(source, R.id.iv_search_location_back, "field 'back'", ImageView.class);
    target.searchEdit = Utils.findRequiredViewAsType(source, R.id.et_search_location, "field 'searchEdit'", EditText.class);
    target.searchImage = Utils.findRequiredViewAsType(source, R.id.iv_search_location_search, "field 'searchImage'", ImageView.class);
    target.map = Utils.findRequiredViewAsType(source, R.id.map_search_location, "field 'map'", MapView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.rv_ssearch_location, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchLocationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
    target.searchEdit = null;
    target.searchImage = null;
    target.map = null;
    target.recyclerView = null;
  }
}
