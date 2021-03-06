package cc.hqu.sends.myzhihudaily.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.presenter.StartViewPresenter;
import cc.hqu.sends.myzhihudaily.Constants;
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
    public void updateInformation(String text, String img) {
        Picasso.with(this).load(img).into(mImageView);
        mTextView.setText(text);
        startMainActivity();
    }

    @NonNull
    @Override
    public StartViewPresenter createPresenter() {
        return new StartViewPresenter();
    }
}
