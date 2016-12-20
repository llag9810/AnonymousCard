package xyz.viseator.anonymouscard.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanhao on 16-12-20.
 */

public class UserInfoList {
    public static List<UserInfo> userInfos=new ArrayList<UserInfo>();
    public static void addUserToList(UserInfo userInfo){
        userInfos.add(userInfo);
    }
}
