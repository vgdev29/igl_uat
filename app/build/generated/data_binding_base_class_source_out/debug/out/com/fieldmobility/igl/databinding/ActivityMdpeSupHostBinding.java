// Generated by data binding compiler. Do not edit!
package com.fieldmobility.igl.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.fieldmobility.igl.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMdpeSupHostBinding extends ViewDataBinding {
  @NonNull
  public final ImageView back;

  @NonNull
  public final TextView headerTitle;

  @NonNull
  public final ImageView newRegestration;

  @NonNull
  public final FrameLayout tabcontent;

  @NonNull
  public final TabHost tabhost;

  @NonNull
  public final TabWidget tabs;

  protected ActivityMdpeSupHostBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView back, TextView headerTitle, ImageView newRegestration, FrameLayout tabcontent,
      TabHost tabhost, TabWidget tabs) {
    super(_bindingComponent, _root, _localFieldCount);
    this.back = back;
    this.headerTitle = headerTitle;
    this.newRegestration = newRegestration;
    this.tabcontent = tabcontent;
    this.tabhost = tabhost;
    this.tabs = tabs;
  }

  @NonNull
  public static ActivityMdpeSupHostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mdpe_sup_host, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMdpeSupHostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMdpeSupHostBinding>inflateInternal(inflater, R.layout.activity_mdpe_sup_host, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMdpeSupHostBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_mdpe_sup_host, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMdpeSupHostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMdpeSupHostBinding>inflateInternal(inflater, R.layout.activity_mdpe_sup_host, null, false, component);
  }

  public static ActivityMdpeSupHostBinding bind(@NonNull View view) {
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
  public static ActivityMdpeSupHostBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMdpeSupHostBinding)bind(component, view, R.layout.activity_mdpe_sup_host);
  }
}