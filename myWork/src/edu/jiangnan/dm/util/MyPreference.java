package edu.jiangnan.dm.util;

import android.content.Context;
import android.content.SharedPreferences;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.activity.MainActivity;

public class MyPreference {
    private SharedPreferences mInfo = null;
    final String PACKNAME = "edu.jiangnan.sm" ;
    private Context mContext;
    public static MyPreference myPreference;
    public MyPreference(Context context) {
        mContext = context;
        mInfo = context.getSharedPreferences(PACKNAME, 0);
    }
    public static MyPreference  getInstance() {
        if(myPreference == null) {
            myPreference = new MyPreference(MainActivity.getContext());
        }

        return myPreference;
    }

    /**
     * 保存用户姓名
     * @param name
     */
    public void storeUserName(String name) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("name", name);
        editor.commit();
    }

    /**
     *获取用户姓名
     * @return name
     */
    public String getUserName() {
        return mInfo.getString("name", "xxx");
    }

    /**
     * 保存用户性别
     * @param sex
     */
    public void storeUserSex(String sex) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("sex", sex);
        editor.commit();
    }

    /**
     *获取用户性别
     * @return sex
     */
    public String getUserSex() {
        return mInfo.getString("sex", "");
    }

    /**
     * 保存用户民族
     * @param nation
     */
    public void storeUserNation(String nation) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("nation", nation);
        editor.commit();
    }

    /**
     *获取用户民族
     * @return nation
     */
    public String getUserNation() {
        return mInfo.getString("nation", "");
    }

    /**
     * 保存用户国籍
     * @param nationality
     */
    public void storeUserNationality(String nationality) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("nationality", nationality);
        editor.commit();
    }

    /**
     *获取用户国籍
     * @return nationality
     */
    public String getUserNationality() {
        return mInfo.getString("nationality", "");
    }

    /**
     * 保存用户出生日期
     * @param birthday
     */
    public void storeUserBirthday(String birthday) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("birthday", birthday);
        editor.commit();
    }

    /**
     *获取用户出生日期
     * @return nationality
     */
    public String getUserBirthday() {
        return mInfo.getString("birthday", "");
    }

    /**
     * 保存用户电话
     * @param tel
     */
    public void storeUserTelephone(String tel) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("tel", tel);
        editor.commit();
    }

    /**
     *获取用户电话
     * @return nationality
     */
    public String getUserTelephone() {
        return mInfo.getString("tel", "");
    }


    /**
     * 保存用户邮箱
     * @param email
     */
    public void storeUserEmail(String email) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("email", email);
        editor.commit();
    }

    /**
     *获取用户邮箱
     * @return nationality
     */
    public String getUserEmail() {
        return mInfo.getString("email", "");
    }

    /**
     * 保存用户家庭住址
     * @param familyAddress
     */
    public void storeUserFamilyAddr(String familyAddress) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("family_address", familyAddress);
        editor.commit();
    }

    /**
     *获取用户家庭住址
     * @return nationality
     */
    public String getUserFamilyAddr() {
        return mInfo.getString("family_address", "");
    }

    /**
     * 保存用户当前住址
     * @param currentAddress
     */
    public void storeUserCurrentAddr(String currentAddress) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("current_address", currentAddress);
        editor.commit();
    }

    /**
     *获取用户当前住址
     * @return nationality
     */
    public String getUserCurrentAddr() {
        return mInfo.getString("current_address", "");
    }

    /**
     * 保存用户当前身份证号
     * @param idcardNum
     */
    public void storeUserIDCardNum(String idcardNum) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("idcard_num", idcardNum);
        editor.commit();
    }

    /**
     *获取用户身份证号
     * @return idcard_num
     */
    public String getUserIDCardNum() {
        return mInfo.getString("idcard_num", "");
    }

    /**
     * 保存用户当前名片样式
     * @param cardID
     */
    public void storeUserCardID(String cardID) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("cardid", cardID);
        editor.commit();
    }

    /**
     *获取用户名片样式
     * @return idcard_num
     */
    public String getUserCardID() {
        return mInfo.getString("cardid", null);
    }

    /**
     * 保存添加联系人名片样式
     * @param cardID
     */
    public void storeAddCardID(String cardID) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putString("contact_cardId", cardID);
        editor.commit();
    }

    /**
     *获取添加名片样式
     * @return idcard_num
     */
    public String getAddCardID() {
        return mInfo.getString("contact_cardId", Constants.CARD_ONE);
    }


    /**
     * 保存是否填写信息
     * @param hasAdded
     */
    public void storeInfoAdded(boolean hasAdded) {
        SharedPreferences.Editor editor = mInfo.edit();
        editor.putBoolean("has_added", hasAdded);
        editor.commit();
    }

    /**
     *获取是否填写信息
     * @return hasAdded
     */
    public boolean getInfoAdded() {
        return mInfo.getBoolean("has_added", false);
    }


}
