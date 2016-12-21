package xyz.viseator.anonymouscard.data;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by viseator on 2016/12/21.
 * Wudi
 * viseator@gmail.com
 */

public class DataPackage implements Serializable {
    final long serialVersionUID = 66666666L;
    private String ipAddress;
    private String macAddress;
    private String title;
    private String content;
    private String id;
    private ArrayList<Bitmap> contentPics;


    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = macAddress + "_" + String.valueOf(id);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Bitmap> getContentPics() {
        return contentPics;
    }

    public void setContentPics(ArrayList<Bitmap> contentPics) {
        this.contentPics = contentPics;
    }
}
