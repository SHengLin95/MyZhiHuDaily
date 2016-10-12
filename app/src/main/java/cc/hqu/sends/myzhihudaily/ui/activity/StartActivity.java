package cc.hqu.sends.myzhihudaily.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.presenter.StartViewPresenter;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.view.IStartView;


public class StartActivity extends BaseActivity<IStartView, StartViewPresenter>
        implements IStartView {
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view);
        initView();

        presenter.loadData(Constants.URL.ZHIHU_DAILY_START);
        startMainActivity();

    }



    private void initView() {
        mTextView = (TextView) findViewById(R.id.startText);
        mImageView = (ImageView) findViewById(R.id.startImage);

    }

    private void startMainActivity() {

        //延时3s后启动MainActivity
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                //结束StartActivity
                finish();
            }
        };
        new Timer().schedule(task, 2000);
        /////
    }

    @Override
    public void updateInformation(String text, Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
        mTextView.setText(text);
    }

    @NonNull
    @Override
    public StartViewPresenter createPresenter() {
        return new StartViewPresenter();
    }
}
