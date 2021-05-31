package com.fieldmobility.igl.interfaces;

import java.util.ArrayList;

public interface ListDataPasser<T> {

    public void notifyDataPasser(ArrayList<T> ngList);

    public void notifyFailurePasser(ArrayList<T> ngList);

    public void notifyLowNetworkPasser(ArrayList<T> ngList);


}
