package com.moringaschool.mymovie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.moringaschool.mymovie.R;

import static com.moringaschool.mymovie.utils.Constant.URL_OF_REVIEW;


public class WebViewActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final ProgressBar loadingIndicator = findViewById(R.id.indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        int webView = 0;
        WebView web = findViewById(webView);

        Intent intent = getIntent();
        url = intent.getStringExtra(URL_OF_REVIEW);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // Hide Loading Indicator
                loadingIndicator.setVisibility(View.GONE);
            }
        });
    }


}
