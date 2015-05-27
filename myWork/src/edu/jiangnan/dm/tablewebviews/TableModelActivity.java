package edu.jiangnan.dm.tablewebviews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.util.DialogUtils;
import edu.jiangnan.dm.util.GetDataUtils;
import edu.jiangnan.dm.view.MyDialog;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/3/21.
 */
public class TableModelActivity extends Activity {

    private infoModel mInfoModel;
    WebView webView;
    Activity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mContext = this;
        webView = (WebView)findViewById(R.id.web_content);
        mInfoModel = GetDataUtils.getDataFromDB(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "GetJsonText");
        webView.loadUrl("file:///android_asset/registerPage/index.html");
    }

    final class MyJavaScriptInterface {
        @JavascriptInterface
        public String getJson() {
            JSONObject json = new JSONObject();
            json = GetDataUtils.getJsonObject(mInfoModel);
            Log.e("-----JSON--------","==:"+json.toString());
            return json.toString();
        }

        public void showOKDialog() {
            Log.e("-----Click WebButton--------","==:");
            DialogUtils.showDialog(mContext, getString(R.string.login_success), null, new DialogUtils.OnClickCallBack() {
                @Override
                public void confirm() {
                    if(!mContext.isFinishing()) {
                        finish();
                    }
                }

                @Override
                public void cancel() {
                    if(!mContext.isFinishing()) {
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.setVisibility(View.GONE);
        webView.destroy();
    }
}
