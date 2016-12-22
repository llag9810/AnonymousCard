package xyz.viseator.anonymouscard.data;

import java.io.Serializable;

/**
 * Created by yanhao on 16-12-22.
 */

public class DataPackage implements Serializable {
    final long serialVersionUID = 66666666L;
    private int sign;
    private String myMac;
    private String myIp;
    private String id;
    private byte[] bitmap;
    private String content;
    private String title;

    public String getMyMac() {
        return myMac;
    }

    public void setMyMac(String myMac) {
        this.myMac = myMac;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        return id;
    }

    public void setId(int id) {
        this.id = myMac + "__" + String.valueOf(id);
    }

    public byte[] getBitmap() {
        return bitmap;
    }


    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
