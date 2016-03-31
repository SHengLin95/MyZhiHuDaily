package cc.hqu.sends.myzhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Content;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.support.Constants;

/**
 * Created by shenglin on 16-3-30.
 */
public class ContentActivity extends BaseActivity {
    private final static String CSS_FILE_NAME = "news.css";
    private Toolbar mToolbar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        initView();
        new contentTask().run(Constants.URL.ZHIHU_DAILY_NEWS_CONTENT + "3892357");
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.content_toolbar);
        setSupportActionBar(mToolbar);
        mWebView = (WebView) findViewById(R.id.content_webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }


    private class contentTask {
        private RequestQueue mQueue;
        private String url;

        contentTask() {
            mQueue = MyZhiHuDailyApplication.getRequestQueue();
        }

        public void run(String url) {
            this.url = url;
            initData();
        }

        private void initData() {
            GsonRequest<Content> mRequest = new GsonRequest<Content>(url, Content.class,
                    new Response.Listener<Content>() {
                        @Override
                        public void onResponse(Content response) {
//                            boolean isFileExist = false;
//                            String body = response.getBody();
//                            String css = response.getCss()[0];
//                            for (String name : fileList()) {
//                                if (name.equals(CSS_FILE_NAME)) {
//                                    isFileExist = true;
//                                    break;
//                                }
//                            }
//                            if (isFileExist) {
//                                loadWebView(body);
//                            } else {
//                                loadCss(css, body);
//                            }
                            loadWebView(response.getBody());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(mRequest);
        }

//        private void loadCss(String cssURL, final String body) {
//            StringRequest mStingRequest = new StringRequest(cssURL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                FileOutputStream fos = openFileOutput(CSS_FILE_NAME, MODE_APPEND);
//                                PrintStream ps = new PrintStream(fos);
//                                ps.println(response);
//                                ps.close();
//
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                            loadWebView(body);
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//            mQueue.add(mStingRequest);
//        }

        private void loadWebView(String body) {
//            String css = "<link rel=\"stylesheet\" href=\file://" + getFilesDir() + "/" + CSS_FILE_NAME
//                    + "\" type=\"text/css\">";
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
            String html = "<html><head>" + css + "</head><body>" + body + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");
            mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        }
    }
}
