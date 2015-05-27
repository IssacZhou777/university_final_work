package edu.jiangnan.dm.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import edu.jiangnan.dm.R;

public class MyDialog extends Dialog {

    private TextView mTitle;
    private TextView mMessage;
    private Button confirm, cancel;

    public MyDialog(Context context) {
        super(context, R.style.myDialog);
        this.getWindow().setFeatureDrawable(0, new ColorDrawable());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xml_dialog_layout);
        initView();
    }

    private void initView() {
        mTitle = (TextView)findViewById(R.id.title);
        mMessage = (TextView)findViewById(R.id.message);
        confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(btnClicked);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(btnClicked);
    }

    private View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setMessage(String msg) {
        if(msg != null && !msg.equals("")) {
            mMessage.setText(msg);
        } else {
            mMessage.setVisibility(View.GONE);
        }
    }

    public void setPositiveListener (View.OnClickListener listener) {
        if ( listener != null ) {
            confirm.setOnClickListener(listener);
        }
    }

    public void setNegativeListener (View.OnClickListener listener) {
        if(listener == null) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setOnClickListener(listener);
        }
    }
}