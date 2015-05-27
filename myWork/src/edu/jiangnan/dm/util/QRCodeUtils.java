package edu.jiangnan.dm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.google.zxing.WriterException;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.Model.ContactModel;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.view.MyAlertDialog;
import edu.jiangnan.dm.zxing.encoding.EncodingHandler;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/4/11.
 */
public class QRCodeUtils {

    public static void showQRcode(Context context) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context);
        myAlertDialog.show();
    }

    public static void showQRcode(Context context, ContactModel contactModel, String cardId) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context);
        contactModel.setCardId(cardId);
        Bitmap qrCodeBitmap = generateQRCode(contactModel);
        if ( qrCodeBitmap != null ) {
            myAlertDialog.setmQRCode(qrCodeBitmap);
        }
        myAlertDialog.show();
    }

    /**
     * 分享名片
     * @param context
     * @param contactModel
     */
    public static void showQRcode(Context context, ContactModel contactModel) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context);
        Bitmap qrCodeBitmap = generateQRCode(contactModel);
        if ( qrCodeBitmap != null ) {
            myAlertDialog.setmQRCode(qrCodeBitmap);
        }
        String name = contactModel.getName();
        if( name != null && !name.equals("") ) {
            myAlertDialog.setmText(name);
        }
        myAlertDialog.show();
    }

    public static Bitmap generateQRCode(String json) {
        try {
            json = SecurityUtils.encode(Constants.KEY_ENCODE, json);
            Log.e("KKKKKKKKEEEYYY","==========" + json);
                if ( json != null ) {
                    Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
                            json, 600);
                    return qrCodeBitmap;
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }

    public static Bitmap generateQRCode( infoModel model ) {
            if (model == null) {
                return null;
            }
        ContactModel contactModel = GetDataUtils.infoModel2ContactModel(model);
        String jsonStr = GetDataUtils.getContactJsonObject(contactModel).toString();
        Bitmap qrCodeBotmap = generateQRCode(jsonStr);
        return qrCodeBotmap;
    }

    public static Bitmap generateQRCode( ContactModel model ) {
        if (model == null) {
            return null;
        }
        String jsonStr = GetDataUtils.getContactJsonObject(model).toString();
        Bitmap qrCodeBotmap = generateQRCode(jsonStr);
        return qrCodeBotmap;
    }
}
