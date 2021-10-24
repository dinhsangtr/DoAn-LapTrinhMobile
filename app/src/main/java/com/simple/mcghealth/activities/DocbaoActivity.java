package com.simple.mcghealth.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.simple.mcghealth.R;

public class DocbaoActivity extends AppCompatActivity {
    WebView webView;

    /* access modifiers changed from: protected */
    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_docbao);
        this.webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(intent.getStringExtra("url").trim());
    }
}
