package cc.hqu.sends.myzhihudaily.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cc.hqu.sends.myzhihudaily.support.Constants;

/**
 * Created by SHeng_Lin on 2016/3/8.
 */
public class StartActivity extends BaseActivity{
    private ImageView mImageView;
    private TextView mTextView;
    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //更新图片
            mImageView.setImageBitmap((Bitmap) msg.obj);
            mTextView.setText(msg.getData().getString("text"));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_view);
        mTextView = (TextView) findViewById(R.id.startText);
        mImageView = (ImageView) findViewById(R.id.startImage);
        new StartThread(Constants.URL.ZHIHU_DAILY_START, mHandler).start();

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


    class StartThread extends Thread {
        private String mURL;
        private Handler handler;
        private String text;
        private String imageURL;

        public StartThread(String mURL, Handler handler) {
            this.mURL = mURL;
            this.handler = handler;
        }

        private void getJsonData(String url) {
            String json ;
            try {
                json = readStream(new URL(url).openStream());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    text = jsonObject.getString("text");
                    imageURL = jsonObject.getString("img");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         *从输入流中读出json数据
         */
        private String readStream(InputStream is) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String string;

            try {
                while((string = br.readLine()) != null) {
                    sb.append(string);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        //获得图片
        private Bitmap getBitmap(String url) throws IOException {
            return BitmapFactory.decodeStream(new URL(url).openStream());
        }

        @Override
        public void run() {
            super.run();
            getJsonData(mURL);
            try {

                //Message message = new Message();
                Message message = Message.obtain();
                Bundle bundle = message.getData();
                bundle.putString("text",text);
                message.obj = getBitmap(imageURL);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
