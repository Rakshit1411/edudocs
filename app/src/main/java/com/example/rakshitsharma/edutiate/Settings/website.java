package com.example.rakshitsharma.edutiate.Settings;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.rakshitsharma.edutiate.R;

public class website extends AppCompatActivity {

    WebView wb;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        wb = (WebView)findViewById(R.id.web);
        wb.setWebViewClient(new MyBrowser());

        wb.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                setTitle("Loading...");
                if(newProgress==100)
                {
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());



                }
                super.onProgressChanged(view,newProgress);
            }
        });

        wb.getSettings().setLoadsImagesAutomatically(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.canGoBack();
        progressBar.setMax(100);
        wb.getSettings().setSupportZoom(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDisplayZoomControls(true);
        wb.canGoForward();

        wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wb.loadUrl("https://edudocs.xyz");
        progressBar.setProgress(0);

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);

            view.loadUrl(url);
            return true;
        }

    }



    @Override
    public void onBackPressed() {
        if(wb.canGoBack())
        wb.goBack();
        else
            super.onBackPressed();

    }
}

