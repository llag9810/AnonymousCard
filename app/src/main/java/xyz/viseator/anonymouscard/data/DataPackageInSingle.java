package xyz.viseator.anonymouscard.data;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by yanhao on 16-12-22.
 */

public class DataPackageInSingle implements Serializable{
    final long serialVersionUID = 66666666L;
    private int sign;
    private String myIp;
    private String Id;
    private Bitmap bitmap;
    private String content;

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getMyIp() {
        return myIp;
    }

    public void setMyIp(String myIp) {
        this.myIp = myIp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
