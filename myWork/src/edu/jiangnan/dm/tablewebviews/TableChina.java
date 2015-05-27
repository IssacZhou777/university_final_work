package edu.jiangnan.dm.tablewebviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.util.GetDataUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/3/21.
 */
public class TableChina extends Activity {

    private infoModel mInfoModel;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView)findViewById(R.id.web_content);
        mInfoModel = GetDataUtils.getDataFromDB(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "GetJsonText");
        webView.loadUrl("file:///android_asset/china/china.html");
    }

    final class MyJavaScriptInterface {
        @JavascriptInterface
        public String getJson() {
            JSONObject json = new JSONObject();
            json = GetDataUtils.getJsonObject(mInfoModel);
            Log.e("-----JSON--------","==:"+json.toString());
            String mIDNum = mInfoModel.getIDCardNum();
            char[] nums = mIDNum.toCharArray();
            String[] keys = {"id_num0","id_num1","id_num2","id_num3","id_num4","id_num5",
                    "id_num6","id_num7", "id_num8","id_num9","id_num10","id_num11","id_num12",
                    "id_num13","id_num14", "id_num15","id_num16","id_num17"};

            String[] birthdate = mInfoModel.getBirthday().split("-");

                try {
                    for(int i = 0; i< nums.length; i++) {
                        json.put(keys[i], nums[i] + "");
                    }

                    json.put("birth_date_year", birthdate[0]);
                    json.put("birth_date_month", birthdate[1]);
                    json.put("birth_date_day", birthdate[2]);

                    json.put("family_name", mInfoModel.getName().substring(0,1));
                    json.put("first_name", mInfoModel.getName().substring(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            Log.e("-----JSON--------","==:" + json.toString());
            return json.toString();
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
