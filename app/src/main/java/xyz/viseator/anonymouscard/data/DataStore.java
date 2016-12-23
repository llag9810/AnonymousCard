package xyz.viseator.anonymouscard.data;

import java.util.ArrayList;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class DataStore {
    private ArrayList<DataPackage> dataPackages;

    public ArrayList<DataPackage> getDataPackages() {
        return dataPackages;
    }

    public void setDataPackages(ArrayList<DataPackage> dataPackages) {
        this.dataPackages = dataPackages;
    }

    public DataPackage getDataById(String id) {
        for (DataPackage dataPackage : dataPackages) {
            if (dataPackage.getId() == id) {
                return dataPackage;
            }
        }
        return null;
    }
}
