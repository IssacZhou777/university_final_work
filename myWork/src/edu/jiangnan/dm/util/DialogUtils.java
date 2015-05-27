package edu.jiangnan.dm.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import edu.jiangnan.dm.Constants;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.view.MyDialog;

/**
 * Created by Administrator on 2015/3/31.
 */
public class DialogUtils {
    public interface OnClickCallBack {
       public void confirm();
       public void cancel();
    }

    public static void showDialog(Activity context, String title, String msg, final OnClickCallBack callBack) {
        try {
            final MyDialog mDialog = new MyDialog(context);
            mDialog.setTitle(title);
            mDialog.setMessage(msg);
            if(callBack != null) {
                mDialog.setNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.cancel();
                        mDialog.dismiss();
                    }
                });

                mDialog.setPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.confirm();
                        mDialog.dismiss();
                    }
                });
            } else {
                mDialog.setNegativeListener(null);
            }
            mDialog.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showTipsByState(Activity context, int state) {
        String msg = "";
        String title = context.getString(R.string.tips_failed);
            switch (state) {
                case Constants.STATE_NO_DATA:
                    msg = context.getString(R.string.tips_content_no_data);
                    break;
                case Constants.STATE_NAME_ERRO:
                    msg = context.getString(R.string.tips_content_erro_name);
                    break;
                case Constants.STATE_JOB_ERRO:
                    msg = context.getString(R.string.tips_content_erro_job);
                    break;
                case Constants.STATE_TELEPHONE_ERRO:
                    msg = context.getString(R.string.tips_content_erro_telephone);
                    break;
                case Constants.STATE_EMAIL_ERRO:
                    msg = context.getString(R.string.tips_content_erro_email);
                    break;
                case Constants.STATE_ORGANIZATION_ERRO:
                    msg = context.getString(R.string.tips_content_erro_organization);
                    break;
                case Constants.STATE_ADDRESS_ERRO:
                    msg = context.getString(R.string.tips_content_erro_address);
                    break;
                case Constants.STATE_HAD_EXISTED:
                    msg = context.getString(R.string.tips_content_had_exit);
                    break;
                default:
                    msg = context.getString(R.string.tips_content_erro_unknow);
                    break;
            }
        showDialog(context, title, msg, null);
    }
}
