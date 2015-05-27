package edu.jiangnan.dm.tablewebviews;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import edu.jiangnan.dm.Model.infoModel;
import edu.jiangnan.dm.R;
import edu.jiangnan.dm.util.GetDataUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/3/19.
 */
public class TableAmerica  extends Activity{

    private WebView webView;
    private infoModel mInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        webView = (WebView)findViewById(R.id.web_content);
        mInfo = GetDataUtils.getDataFromDB(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
        webView.loadUrl("file:///android_asset/America.html");
    }

    final class DemoJavaScriptInterface {
        @JavascriptInterface
        public String getAmericaJson() {
            JSONObject json = new JSONObject();
            String birthDate = mInfo.getBirthday();
            Log.e("-----JSON--------","==:"+birthDate);
            String[] date = birthDate.split("-");
            Log.e("-----JSON--------","==:"+date[0]+date[1]);
            String issueDate = "";
            try {
                json.put("namePinyin", mInfo.getName().substring(1));
                json.put("letterPinyin", mInfo.getName().substring(0,1));
                json.put("placeOfIssuePinyin", mInfo.getCurrentAddress());
                json.put("nation",mInfo.getNationality());
                json.put("passport_no", " ");
                json.put("letter_pingyin2", " ");
                json.put("sex",mInfo.getSex());
                if (birthDate != "") {
                    json.put("birthyear3", date[0].substring(2, 3));
                    json.put("birthyear4", date[0].substring(3, 4));
                    if(date[1].length() == 2) {
                        json.put("birthmonth1", date[1].substring(0, 1));
                        json.put("birthmonth2", date[1].substring(1, 2));
                    } else  {
                        json.put("birthmonth1", 0 +"");
                        json.put("birthmonth2", date[1]);
                    }

                    if(date[2].length() == 2) {
                        json.put("birthday1", date[2].substring(0, 1));
                        json.put("birthday2", date[2].substring(1, 2));
                    } else  {
                        json.put("birthday1", 0 +"");
                        json.put("birthday2", date[2]);
                    }

                } else {
                    json.put("birthyear3", "");
                    json.put("birthyear4", "");
                    json.put("birthmonth1", "");
                    json.put("birthmonth2", "");
                    json.put("birthday1", "");
                    json.put("birthday2", "");
                }

                if (issueDate != "") {
                    json.put("issueyear3", issueDate.substring(2, 3));
                    json.put("issueyear4", issueDate.substring(3, 4));
                    json.put("issuemonth1", issueDate.substring(5, 6));
                    json.put("issuemonth2", issueDate.substring(6, 7));
                    json.put("issueday1", issueDate.substring(8, 9));
                    json.put("issueday2", issueDate.substring(9, 10));
                } else {
                    json.put("issueyear3", "");
                    json.put("issueyear4", "");
                    json.put("issuemonth1", "");
                    json.put("issuemonth2", "");
                    json.put("issueday1", "");
                    json.put("issueday2", "");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("-----JSON--------","==:"+json.toString());
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
