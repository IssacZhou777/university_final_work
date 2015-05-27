package edu.jiangnan.dm.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import edu.jiangnan.dm.R;

/**
 * Created by Administrator on 2015/4/11.
 */
public class MyAlertDialog extends Dialog {

    ImageView mQRCode;
    TextView mText;
    public MyAlertDialog(Context context) {
        super(context, R.style.myDialog);
        this.getWindow().setFeatureDrawable(0, new ColorDrawable());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_my_qrcode);
        initView();
    }

    private void initView() {
        mQRCode = (ImageView) findViewById(R.id.qrcode);
        mText = (TextView) findViewById( R.id.text);
        View emptyZone = findViewById(R.id.empty_zone);
        emptyZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setmQRCode(Bitmap bitmap) {
            mQRCode.setImageBitmap(bitmap);
    }
    public void setmQRCode(int resId) {
        mQRCode.setImageResource(resId);
    }
    public void setmText(String text) {
        mText.setText(text);
    }
}
