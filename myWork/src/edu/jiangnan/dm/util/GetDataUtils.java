package edu.jiangnan.dm.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.db.MySqlHelper;
import edu.jiangnan.dm.db.TContactColumn;
import edu.jiangnan.dm.db.TUserInfoColumn;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly Joe on 2015/3/19.
 */
public class GetDataUtils {
    /**
     *从数据库获取用户信息
     * @param context
     * @return
     */

    public static infoModel getDataFromDB(Context context) {
        MySqlHelper mySqlHelper = new MySqlHelper(context, "user_info.db",  null, 1);
        infoModel mInfo = new infoModel();
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
             cursor = db.rawQuery("select * from "+ Constants.USERINFO_TABLE_NAME, null);
            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                mInfo.setName(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NAME)));
                mInfo.setSex(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_SEX)));
                mInfo.setNation(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NATION)));
                mInfo.setNationality(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_NATIONALITY)));
                mInfo.setBirthday(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_BIRTHDAY)));
                mInfo.setIDCardNum(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_IDCARD_NUM)));
                mInfo.setTelephone(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_TELEPHONE)));
                mInfo.setEmail(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_EMAIL)));
                mInfo.setFamilyAddress(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_FAMILY_ADDRESS)));
                mInfo.setCurrentAddress(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_CURRENT_ADDRESS)));
                mInfo.setImgBytes(cursor.getBlob(cursor.getColumnIndex(TUserInfoColumn.USER_IMAGE)));
//                mInfo.setQrcodeByte(cursor.getBlob(cursor.getColumnIndex(TUserInfoColumn.USER_QRCODE)));
                mInfo.setJob(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_JOB)));
                mInfo.setOrganization(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_ORGANIZATION)));
                mInfo.setOrganAddress(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_ORGAN_ADDRESS)));
                mInfo.setPoliticalStatue(cursor.getString(cursor.getColumnIndex(TUserInfoColumn.USER_POLITICAL_STATUE)));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return mInfo;
    }

    public static JSONObject getJsonObject( infoModel model) {
        JSONObject json = new JSONObject();
        try{
            if (model != null) {
                json.put(TUserInfoColumn.USER_NAME, model.getName());
                json.put(TUserInfoColumn.USER_SEX, model.getSex());
                json.put(TUserInfoColumn.USER_NATION, model.getNation());
                json.put(TUserInfoColumn.USER_NATIONALITY, model.getNationality());
                json.put(TUserInfoColumn.USER_BIRTHDAY, model.getBirthday());
                json.put(TUserInfoColumn.USER_TELEPHONE, model.getTelephone());
                json.put(TUserInfoColumn.USER_EMAIL, model.getEmail());
                json.put(TUserInfoColumn.USER_FAMILY_ADDRESS, model.getFamilyAddress());
                json.put(TUserInfoColumn.USER_CURRENT_ADDRESS, model.getCurrentAddress());
                json.put(TUserInfoColumn.USER_IDCARD_NUM, model.getIDCardNum());
                json.put(TUserInfoColumn.USER_JOB, model.getJob());
                json.put(TUserInfoColumn.USER_ORGANIZATION, model.getOrganization());
                json.put(TUserInfoColumn.USER_ORGAN_ADDRESS, model.getOrganAddress());
                json.put(TUserInfoColumn.USER_POLITICAL_STATUE, model.getPoliticalStatue());
            } else {
                json.put(TUserInfoColumn.USER_NAME, "未填写");
                json.put(TUserInfoColumn.USER_SEX, "未填写");
                json.put(TUserInfoColumn.USER_NATION, "未填写");
                json.put(TUserInfoColumn.USER_NATIONALITY, "未填写");
                json.put(TUserInfoColumn.USER_BIRTHDAY, "未填写");
                json.put(TUserInfoColumn.USER_TELEPHONE, "未填写");
                json.put(TUserInfoColumn.USER_EMAIL, "未填写");
                json.put(TUserInfoColumn.USER_FAMILY_ADDRESS, "未填写");
                json.put(TUserInfoColumn.USER_CURRENT_ADDRESS, "未填写");
                json.put(TUserInfoColumn.USER_IDCARD_NUM, "未填写");
                json.put(TUserInfoColumn.USER_JOB, "未填写");
                json.put(TUserInfoColumn.USER_ORGANIZATION, "未填写");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
            return json;
    }

    /**
     *从数据库获取联系人信息
     * @param context
     * @return
     */
    public static List<ContactModel> getContactDataFromDB(Context context) {
        MySqlHelper mySqlHelper = new MySqlHelper(context, "user_info.db",  null, 1);
        List<ContactModel> list = new ArrayList<ContactModel>();
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from "+ Constants.CONTACT_TABLE_NAME, null);
            if(cursor != null && cursor.getCount() > 0) {
                int count = cursor.getCount();
                for(int i = 0;i < count; i++) {
                    cursor.moveToNext();
                    ContactModel model = new ContactModel();
                    model.setName(cursor.getString(cursor.getColumnIndex(TContactColumn.NAME)));
                    model.setJob(cursor.getString(cursor.getColumnIndex(TContactColumn.JOB)));
                    model.setTelephone(cursor.getString(cursor.getColumnIndex(TContactColumn.TELEPHONE)));
                    model.setEmail(cursor.getString(cursor.getColumnIndex(TContactColumn.EMAIL)));
                    model.setOrganization(cursor.getString(cursor.getColumnIndex(TContactColumn.ORGANIZATION)));
                    model.setAddress(cursor.getString(cursor.getColumnIndex(TContactColumn.ADDRESS)));
                    model.setQrcode(cursor.getBlob(cursor.getColumnIndex(TContactColumn.QRCODE)));
                    model.setCardId(cursor.getString(cursor.getColumnIndex(TContactColumn.CARDID)));
                    list.add(model);
                }

            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     *从数据库获取条件搜索的联系人信息
     * @param context
     * @return
     */
    public static List<ContactModel> getContactDataBySearch(Context context, String search) {
        Log.e("SQL=============", "sq; is :" + search);
        if ( search == null ) {
            return  null;
        }
        MySqlHelper mySqlHelper = new MySqlHelper(context, "user_info.db",  null, 1);
        List<ContactModel> list = new ArrayList<ContactModel>();
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        String sql = "select * from "+ Constants.CONTACT_TABLE_NAME + " where "
                + TContactColumn.NAME + " like '%" + search +"%'"
                + " or " + TContactColumn.JOB + " like '%" + search +"%'"
                + " or " + TContactColumn.TELEPHONE + " like '%" + search +"%'"
                + " or " + TContactColumn.EMAIL + " like '%" + search +"%'"
                + " or " + TContactColumn.ADDRESS + " like '%" + search +"%'"
                + " or " + TContactColumn.ORGANIZATION + " like '%" + search +"%'";
        Log.e("SQL=============", "sq; is :" + sql);
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if(cursor != null && cursor.getCount() > 0) {
                int count = cursor.getCount();
                for(int i = 0;i < count; i++) {
                    cursor.moveToNext();
                    ContactModel model = new ContactModel();
                    model.setName(cursor.getString(cursor.getColumnIndex(TContactColumn.NAME)));
                    model.setJob(cursor.getString(cursor.getColumnIndex(TContactColumn.JOB)));
                    model.setTelephone(cursor.getString(cursor.getColumnIndex(TContactColumn.TELEPHONE)));
                    model.setEmail(cursor.getString(cursor.getColumnIndex(TContactColumn.EMAIL)));
                    model.setOrganization(cursor.getString(cursor.getColumnIndex(TContactColumn.ORGANIZATION)));
                    model.setAddress(cursor.getString(cursor.getColumnIndex(TContactColumn.ADDRESS)));
                    model.setQrcode(cursor.getBlob(cursor.getColumnIndex(TContactColumn.QRCODE)));
                    model.setCardId(cursor.getString(cursor.getColumnIndex(TContactColumn.CARDID)));
                    list.add(model);
                }

            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 获取联系人
     * @param model
     * @return
     */
    public static JSONObject getContactJsonObject( ContactModel model) {
        JSONObject json = new JSONObject();
        try{
            if (model != null) {
                json.put(TContactColumn.NAME, model.getName());
                json.put(TContactColumn.JOB, model.getJob());
                json.put(TContactColumn.TELEPHONE, model.getTelephone());
                json.put(TContactColumn.EMAIL, model.getEmail());
                json.put(TContactColumn.ORGANIZATION, model.getOrganization());
                json.put(TContactColumn.ADDRESS, model.getAddress());
                json.put(TContactColumn.CARDID, model.getCardId());

            } else {
                json.put(TContactColumn.NAME, "未填写");
                json.put(TContactColumn.JOB, "未填写");
                json.put(TContactColumn.TELEPHONE, "未填写");
                json.put(TContactColumn.EMAIL, "未填写");
                json.put(TContactColumn.ORGANIZATION,"未填写");
                json.put(TContactColumn.ADDRESS, "未填写");
                json.put(TContactColumn.CARDID, "未填写");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static int checkContactModel(ContactModel contactModel) {
        int state = Constants.STATE_OK;
        if (contactModel == null) {
            state = Constants.STATE_NO_DATA;
            return state;
        }
        if(contactModel.getName() == null || contactModel.getName().equals("")) {
            state =Constants.STATE_NAME_ERRO;
            return state;
        }
        if(contactModel.getJob() == null || contactModel.getJob().equals("")) {
            state =Constants.STATE_JOB_ERRO;
            return state;
        }
        if(contactModel.getTelephone() == null || contactModel.getTelephone().length() != 11) {
            state =Constants.STATE_TELEPHONE_ERRO;
            return state;
        }
        if(contactModel.getEmail() == null || contactModel.getEmail().equals("")) {
            state =Constants.STATE_EMAIL_ERRO;
            return state;
        }
        if(contactModel.getOrganization() == null || contactModel.getOrganization().equals("")) {
            state =Constants.STATE_ORGANIZATION_ERRO;
            return state;
        }
        if(contactModel.getAddress() == null || contactModel.getAddress().equals("")) {
            state =Constants.STATE_ADDRESS_ERRO;
            return state;
        }
//        if(contactModel.getCardId() == null) {
//            state =Constants.STATE_CARDID_ERRO;
//            return state;
//        }

        return state;
    }

    /**
     * 将个人信息转成名片信息
     * @param model
     * @return
     */
    public static ContactModel infoModel2ContactModel(infoModel model) {
        ContactModel contactModel = new ContactModel();
        if (model != null) {
            contactModel.setName(model.getName());
            contactModel.setJob(model.getJob());
            contactModel.setTelephone(model.getTelephone());
            contactModel.setEmail(model.getEmail());
            contactModel.setOrganization(model.getOrganization());
            contactModel.setAddress(model.getCurrentAddress());
            contactModel.setCardId(MyPreference.getInstance().getUserCardID());
        }
        return contactModel;
    }

    /**
     *删除指定位置的数据
     * @param context
     * @return
     */
    public static void deleteContactData(Context context, String tel) {
        MySqlHelper mySqlHelper = new MySqlHelper(context, "user_info.db", null, 1);
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        db.delete(Constants.CONTACT_TABLE_NAME, TContactColumn.TELEPHONE + " = " + tel, null);
        Log.e("DEEELLLEEET", "--========  OK");
    }

    /**
     * 将名片信息存到数据库
     * @param context
     * @param model
     */
    public static void putData2DB(Activity context, ContactModel model) {
        MySqlHelper mySqlHelper = new MySqlHelper(context,
                "user_info.db", null, 1);
        SQLiteDatabase db = mySqlHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TContactColumn.NAME, model.getName());
        values.put(TContactColumn.JOB, model.getJob());
        values.put(TContactColumn.TELEPHONE, model.getTelephone());
        values.put(TContactColumn.EMAIL, model.getEmail());
        values.put(TContactColumn.ORGANIZATION, model.getOrganization());
        values.put(TContactColumn.ADDRESS, model.getAddress());
        values.put(TContactColumn.CARDID, model.getCardId());

        try{
            db.insert(Constants.CONTACT_TABLE_NAME, null, values);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    /**
     * 从json包中获取名片信息
     * @param jsonStr
     * @return
     */
    public static ContactModel getContactModelFromJsonStr(String jsonStr) {
        ContactModel contactModel = new ContactModel();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            contactModel.setName(jsonObject.getString(TContactColumn.NAME));
            contactModel.setJob(jsonObject.getString(TContactColumn.JOB));
            contactModel.setTelephone(jsonObject.getString(TContactColumn.TELEPHONE));
            contactModel.setEmail(jsonObject.getString(TContactColumn.EMAIL));
            contactModel.setOrganization(jsonObject.getString(TContactColumn.ORGANIZATION));
            contactModel.setAddress(jsonObject.getString(TContactColumn.ADDRESS));
            contactModel.setCardId(jsonObject.getString(TContactColumn.CARDID));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return contactModel;
    }

    /**
     *从数据库获取条件搜索的联系人信息
     * @param context
     * @return
     */
    public static boolean isContactExist(Context context, String telephone) {
        if ( telephone == null ) {
            return  false;
        }
        boolean isExist = false;
        MySqlHelper mySqlHelper = new MySqlHelper(context, "user_info.db",  null, 1);
        List<ContactModel> list = new ArrayList<ContactModel>();
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        String sql = "select * from "+ Constants.CONTACT_TABLE_NAME + " where " + TContactColumn.TELEPHONE
                + " = " + telephone;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if(cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return isExist;
    }

}
