// Generated by data binding compiler. Do not edit!
package com.fieldmobility.igl.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.fieldmobility.igl.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMdpeTpiPendingBinding extends ViewDataBinding {
  @NonNull
  public final Button btnTryAgain;

  @NonNull
  public final EditText editTextSearch;

  @NonNull
  public final TextView listCount;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final RelativeLayout relNodata;

  @NonNull
  public final ImageView rfcFilter;

  @NonNull
  public final TextView searchTpibp;

  @NonNull
  public final SwipeRefreshLayout swipeToRefresh;

  @NonNull
  public final TextView tvNgUserListdata;

  protected ActivityMdpeTpiPendingBinding(Object _bindingComponent, View _root,
      int _localFieldCount, Button btnTryAgain, EditText editTextSearch, TextView listCount,
      RecyclerView recyclerView, RelativeLayout relNodata, ImageView rfcFilter,
      TextView searchTpibp, SwipeRefreshLayout swipeToRefresh, TextView tvNgUserListdata) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnTryAgain = btnTryAgain;
    this.editTextSearch = editTextSearch;
    this.listCount = listCount;
    this.recyclerView = recyclerView;
    this.relNodata = relNodata;
    this.rfcFilter = rfcFilter;
    this.searchTpibp = searchTpibp;
    this.swipeToRefresh = swipeToRefresh;
    this.tvNgUserListdata = tvNgUserListdata;
  }

  @NonNull
  public static ActivityMdpeTpiPendingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mdpe_tpi_pending, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMdpeTpiPendingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMdpeTpiPendingBinding>inflateInternal(inflater, R.layout.activity_mdpe_tpi_pending, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMdpeTpiPendingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mdpe_tpi_pending, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMdpeTpiPendingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMdpeTpiPendingBinding>inflateInternal(inflater, R.layout.activity_mdpe_tpi_pending, null, false, component);
  }

  public static ActivityMdpeTpiPendingBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityMdpeTpiPendingBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMdpeTpiPendingBinding)bind(component, view, R.layout.activity_mdpe_tpi_pending);
  }
}
