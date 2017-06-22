package com.example.rahul.webview;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    private WebView mWebview ;
    private String username = "Rahuljha6212@gmail.com";
    private String password = "8051722002";
    private String appCachePath;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mWebview  = new WebView(this);
        // use cookies to remember a logged in status
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);

        mWebview.getSettings().setAppCachePath(appCachePath);
        mWebview.getSettings().setAppCacheEnabled(true);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });




      //  webView.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview .loadUrl("http://liquidserve.com/xf/index.php?login/");
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d(TAG, "All the cookies in a string:" + cookies);

                CookieSyncManager.getInstance().sync();



                final String answer = "5";

                view.loadUrl("javascript:function()");
              //  view.loadUrl("javascript:document.forms['pageLogin'].submit()");
                final String js ="javascript:  document.getElementById('ctrl_pageLogin_login').value = '" + username + "';" +
                        " document.getElementById('ctrl_pageLogin_password').value = '" + password + "';";

                mWebview.loadUrl("javascript:(function(){"+
                        "l=document.getElementsByClassName('button primary');"+
                        "e=document.createEvent('HTMLEvents');"+
                        "e.initEvent('click',true,true);"+
                        "l.dispatchEvent(e);"+
                        "})()");


                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                      System.out.println(s);
                    }
                });
            }
        });
        setContentView(mWebview );
    }

}
