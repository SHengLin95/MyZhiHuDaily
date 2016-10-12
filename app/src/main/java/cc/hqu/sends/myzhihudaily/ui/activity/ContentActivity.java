package cc.hqu.sends.myzhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Content;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.presenter.ContentViewPresenter;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.view.IContentView;


public class ContentActivity extends BaseActivity<IContentView, ContentViewPresenter>
 implements IContentView{
    private Toolbar mToolbar;
    private WebView mWebView;
    private ImageView mImage;
    private TextView mTitle;
    private boolean isIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        long id = intent.getLongExtra(Constants.ZHIHU_CONTENT_ID, 0);
        isIndex = intent.getBooleanExtra(Constants.ZHIHU_CONTENT_IS_INDEX, false);
        super.onCreate(savedInstanceState);
        if (isIndex) {
            setContentView(R.layout.news_content_index);
        } else {
            setContentView(R.layout.news_content);
        }
        initView();
        if (id != 0) {
            presenter.loadData(Constants.URL.ZHIHU_DAILY_NEWS_CONTENT + id);
        }

    }

    @NonNull
    @Override
    public ContentViewPresenter createPresenter() {
        return new ContentViewPresenter();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.content_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (isIndex) {
            mImage = (ImageView) findViewById(R.id.content_image);
            mTitle = (TextView) findViewById(R.id.content_title);
        }
        mWebView = (WebView) findViewById(R.id.content_webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void loadWebView(Content content) {
        if (isIndex) {
            mImageLoader.displayImage(content.getImage(), mImage, mOptions);
            mTitle.setText(content.getTitle());
        }

//            String css = "<link rel=\"stylesheet\" href=\file://" + getFilesDir() + "/" + CSS_FILE_NAME
//                    + "\" type=\"text/css\">";
        String css = "<link rel=\"stylesheet\" href=\"" + content.getCss()[0] + " type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }


}
