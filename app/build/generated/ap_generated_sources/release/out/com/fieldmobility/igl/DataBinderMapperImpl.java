package com.fieldmobility.igl;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.fieldmobility.igl.databinding.ActivityMdpeApprovalBindingImpl;
import com.fieldmobility.igl.databinding.ActivityMdpeSupHostBindingImpl;
import com.fieldmobility.igl.databinding.ActivityMdpeTilesBindingImpl;
import com.fieldmobility.igl.databinding.ActivityMdpeTpiHostBindingImpl;
import com.fieldmobility.igl.databinding.ActivityMdpeTpiPendingBindingImpl;
import com.fieldmobility.igl.databinding.FragmentConstructionBindingImpl;
import com.fieldmobility.igl.databinding.FragmentMCommonBindingImpl;
import com.fieldmobility.igl.databinding.FragmentPipeBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMDPEAPPROVAL = 1;

  private static final int LAYOUT_ACTIVITYMDPESUPHOST = 2;

  private static final int LAYOUT_ACTIVITYMDPETILES = 3;

  private static final int LAYOUT_ACTIVITYMDPETPIHOST = 4;

  private static final int LAYOUT_ACTIVITYMDPETPIPENDING = 5;

  private static final int LAYOUT_FRAGMENTCONSTRUCTION = 6;

  private static final int LAYOUT_FRAGMENTMCOMMON = 7;

  private static final int LAYOUT_FRAGMENTPIPE = 8;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(8);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.activity_mdpe_approval, LAYOUT_ACTIVITYMDPEAPPROVAL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.activity_mdpe_sup_host, LAYOUT_ACTIVITYMDPESUPHOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.activity_mdpe_tiles, LAYOUT_ACTIVITYMDPETILES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.activity_mdpe_tpi_host, LAYOUT_ACTIVITYMDPETPIHOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.activity_mdpe_tpi_pending, LAYOUT_ACTIVITYMDPETPIPENDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.fragment_construction, LAYOUT_FRAGMENTCONSTRUCTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.fragment_m_common, LAYOUT_FRAGMENTMCOMMON);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.fieldmobility.igl.R.layout.fragment_pipe, LAYOUT_FRAGMENTPIPE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMDPEAPPROVAL: {
          if ("layout/activity_mdpe_approval_0".equals(tag)) {
            return new ActivityMdpeApprovalBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mdpe_approval is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMDPESUPHOST: {
          if ("layout/activity_mdpe_sup_host_0".equals(tag)) {
            return new ActivityMdpeSupHostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mdpe_sup_host is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMDPETILES: {
          if ("layout/activity_mdpe_tiles_0".equals(tag)) {
            return new ActivityMdpeTilesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mdpe_tiles is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMDPETPIHOST: {
          if ("layout/activity_mdpe_tpi_host_0".equals(tag)) {
            return new ActivityMdpeTpiHostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mdpe_tpi_host is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMDPETPIPENDING: {
          if ("layout/activity_mdpe_tpi_pending_0".equals(tag)) {
            return new ActivityMdpeTpiPendingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_mdpe_tpi_pending is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCONSTRUCTION: {
          if ("layout/fragment_construction_0".equals(tag)) {
            return new FragmentConstructionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_construction is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMCOMMON: {
          if ("layout/fragment_m_common_0".equals(tag)) {
            return new FragmentMCommonBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_m_common is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPIPE: {
          if ("layout/fragment_pipe_0".equals(tag)) {
            return new FragmentPipeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_pipe is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(8);

    static {
      sKeys.put("layout/activity_mdpe_approval_0", com.fieldmobility.igl.R.layout.activity_mdpe_approval);
      sKeys.put("layout/activity_mdpe_sup_host_0", com.fieldmobility.igl.R.layout.activity_mdpe_sup_host);
      sKeys.put("layout/activity_mdpe_tiles_0", com.fieldmobility.igl.R.layout.activity_mdpe_tiles);
      sKeys.put("layout/activity_mdpe_tpi_host_0", com.fieldmobility.igl.R.layout.activity_mdpe_tpi_host);
      sKeys.put("layout/activity_mdpe_tpi_pending_0", com.fieldmobility.igl.R.layout.activity_mdpe_tpi_pending);
      sKeys.put("layout/fragment_construction_0", com.fieldmobility.igl.R.layout.fragment_construction);
      sKeys.put("layout/fragment_m_common_0", com.fieldmobility.igl.R.layout.fragment_m_common);
      sKeys.put("layout/fragment_pipe_0", com.fieldmobility.igl.R.layout.fragment_pipe);
    }
  }
}
