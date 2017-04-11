// Generated code from Butter Knife. Do not modify!
package com.example.hanlian.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.hanlian.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BusinessLoginActivity_ViewBinding implements Unbinder {
  private BusinessLoginActivity target;

  @UiThread
  public BusinessLoginActivity_ViewBinding(BusinessLoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BusinessLoginActivity_ViewBinding(BusinessLoginActivity target, View source) {
    this.target = target;

    target.accountEdit = Utils.findRequiredViewAsType(source, R.id.et_business_account, "field 'accountEdit'", EditText.class);
    target.selectLocation = Utils.findRequiredViewAsType(source, R.id.tv_business_address, "field 'selectLocation'", TextView.class);
    target.addressEdit = Utils.findRequiredViewAsType(source, R.id.et_business_detail, "field 'addressEdit'", EditText.class);
    target.nameEdit = Utils.findRequiredViewAsType(source, R.id.et_business_name, "field 'nameEdit'", EditText.class);
    target.mobileEdit = Utils.findRequiredViewAsType(source, R.id.et_business_mobile, "field 'mobileEdit'", EditText.class);
    target.codeEdit = Utils.findRequiredViewAsType(source, R.id.et_business_code, "field 'codeEdit'", EditText.class);
    target.codeButton = Utils.findRequiredViewAsType(source, R.id.btn_business_code, "field 'codeButton'", Button.class);
    target.idCardEdit = Utils.findRequiredViewAsType(source, R.id.et_business_id_card, "field 'idCardEdit'", EditText.class);
    target.contactManEdit = Utils.findRequiredViewAsType(source, R.id.et_business_contact, "field 'contactManEdit'", EditText.class);
    target.contactPhoneEdit = Utils.findRequiredViewAsType(source, R.id.et_business_contact_phone, "field 'contactPhoneEdit'", EditText.class);
    target.faxEdit = Utils.findRequiredViewAsType(source, R.id.et_business_fax, "field 'faxEdit'", EditText.class);
    target.telEdit = Utils.findRequiredViewAsType(source, R.id.et_business_tel, "field 'telEdit'", EditText.class);
    target.passwordEdit = Utils.findRequiredViewAsType(source, R.id.et_business_password, "field 'passwordEdit'", EditText.class);
    target.passwordAgainEdit = Utils.findRequiredViewAsType(source, R.id.et_business_password_again, "field 'passwordAgainEdit'", EditText.class);
    target.zhengShuRececlerView = Utils.findRequiredViewAsType(source, R.id.rv_business_credential, "field 'zhengShuRececlerView'", RecyclerView.class);
    target.idCardRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_business_id_card, "field 'idCardRecyclerView'", RecyclerView.class);
    target.shopPicRecyclerView = Utils.findRequiredViewAsType(source, R.id.rv_business_shop, "field 'shopPicRecyclerView'", RecyclerView.class);
    target.registerButton = Utils.findRequiredViewAsType(source, R.id.btn_business_register, "field 'registerButton'", Button.class);
    target.backImage = Utils.findRequiredViewAsType(source, R.id.iv_business_back, "field 'backImage'", ImageView.class);
    target.confirmCheck = Utils.findRequiredViewAsType(source, R.id.cb_business_confirm, "field 'confirmCheck'", CheckBox.class);
    target.confirmSpace = Utils.findRequiredViewAsType(source, R.id.ll_business_confirm, "field 'confirmSpace'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BusinessLoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accountEdit = null;
    target.selectLocation = null;
    target.addressEdit = null;
    target.nameEdit = null;
    target.mobileEdit = null;
    target.codeEdit = null;
    target.codeButton = null;
    target.idCardEdit = null;
    target.contactManEdit = null;
    target.contactPhoneEdit = null;
    target.faxEdit = null;
    target.telEdit = null;
    target.passwordEdit = null;
    target.passwordAgainEdit = null;
    target.zhengShuRececlerView = null;
    target.idCardRecyclerView = null;
    target.shopPicRecyclerView = null;
    target.registerButton = null;
    target.backImage = null;
    target.confirmCheck = null;
    target.confirmSpace = null;
  }
}
