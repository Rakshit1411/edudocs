package com.example.rakshitsharma.edutiate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class website extends AppCompatActivity {

    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        wb = (WebView)findViewById(R.id.web);
        wb.loadUrl("https://www.edutiate.com/");
    }
}
