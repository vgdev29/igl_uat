package com.fieldmobility.igl.databinding;
import com.fieldmobility.igl.R;
import com.fieldmobility.igl.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMdpeApprovalBindingImpl extends ActivityMdpeApprovalBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back, 1);
        sViewsWithIds.put(R.id.header_title, 2);
        sViewsWithIds.put(R.id.tv_allo_num, 3);
        sViewsWithIds.put(R.id.tv_suballo, 4);
        sViewsWithIds.put(R.id.tv_wbs, 5);
        sViewsWithIds.put(R.id.tv_address, 6);
        sViewsWithIds.put(R.id.tv_assignment, 7);
        sViewsWithIds.put(R.id.tv_date_key, 8);
        sViewsWithIds.put(R.id.tv_assign_date, 9);
        sViewsWithIds.put(R.id.tv_tpi, 10);
        sViewsWithIds.put(R.id.tv_dpr, 11);
        sViewsWithIds.put(R.id.tv_lati, 12);
        sViewsWithIds.put(R.id.tv_longi, 13);
        sViewsWithIds.put(R.id.tv_submit, 14);
        sViewsWithIds.put(R.id.tv_category, 15);
        sViewsWithIds.put(R.id.tv_section, 16);
        sViewsWithIds.put(R.id.tv_sectionid, 17);
        sViewsWithIds.put(R.id.tv_input, 18);
        sViewsWithIds.put(R.id.tv_address_appr, 19);
        sViewsWithIds.put(R.id.image, 20);
        sViewsWithIds.put(R.id.ll_decline_remarks, 21);
        sViewsWithIds.put(R.id.et_decline_remarks, 22);
        sViewsWithIds.put(R.id.img_selfie, 23);
        sViewsWithIds.put(R.id.ll_tpiSign, 24);
        sViewsWithIds.put(R.id.signature_image, 25);
        sViewsWithIds.put(R.id.signature_button, 26);
        sViewsWithIds.put(R.id.bottomLayout, 27);
        sViewsWithIds.put(R.id.approve_button, 28);
        sViewsWithIds.put(R.id.decline_button, 29);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMdpeApprovalBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }
    private ActivityMdpeApprovalBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[28]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[27]
            , (android.widget.Button) bindings[29]
            , (android.widget.EditText) bindings[22]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[23]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.Button) bindings[26]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[5]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}