/* Author: Edmond Wang
 * Extra Credit Assignment: 2
 * Date: 12/12/16
 * A web browser
 */
package com.example.edmondwang.webbrowser;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class BrowserActivity extends Activity implements
        View.OnClickListener
{
    EditText urlET;
    WebView wv;
    String homeURL;
    Button goBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        urlET = (EditText)findViewById(R.id.url);
        wv = (WebView)findViewById(R.id.webView);
        goBtn = (Button)findViewById(R.id.goBtn);
        wv.setInitialScale(50);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(homeURL);
        urlET.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int i, KeyEvent
                    keyEvent)
            {
                if((keyEvent.getAction() == KeyEvent.ACTION_UP)
                        && (i == KeyEvent.KEYCODE_ENTER))
                {
                    wv.loadUrl(urlET.getText().toString());
                    return true;
                }
                return false;
            }
        });
        goBtn.setOnClickListener(this);
    }

    //***********************onClick()****************************
    @Override
    public void onClick(View v)
    {
        wv.loadUrl(urlET.getText().toString());
    }


    //*****************onKeyUp()**********************************
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if((keyCode == KeyEvent.KEYCODE_BACK) && (wv.canGoBack()))
        {
            wv.goBack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
