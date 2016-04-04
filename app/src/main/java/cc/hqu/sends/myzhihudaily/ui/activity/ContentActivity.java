package cc.hqu.sends.myzhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Content;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.support.Constants;

/**
 * Created by shenglin on 16-3-30.
 */
public class ContentActivity extends BaseActivity {
    private Toolbar mToolbar;
    private WebView mWebView;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long id = getIntent().getLongExtra(Constants.ZHIHU_CONTENT_ID, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        initView();
        if (id != 0) {
            new contentTask().run(Constants.URL.ZHIHU_DAILY_NEWS_CONTENT + id);
        }

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.content_toolbar);
        setSupportActionBar(mToolbar);
        mImage = (ImageView) findViewById(R.id.content_image);
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
                            mImageLoader.displayImage(response.getImage(), mImage, mOptions);

                            loadWebView(response.getBody(), response.getCss()[0]);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(mRequest);
        }


        private void loadWebView(String body, String cssURL) {
//            String css = "<link rel=\"stylesheet\" href=\file://" + getFilesDir() + "/" + CSS_FILE_NAME
//                    + "\" type=\"text/css\">";
            String css = "<link rel=\"stylesheet\" href=\"" + cssURL + " type=\"text/css\">";
            String html = "<html><head>" + css + "</head><body>" + body + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");
            mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        }
    }
}
