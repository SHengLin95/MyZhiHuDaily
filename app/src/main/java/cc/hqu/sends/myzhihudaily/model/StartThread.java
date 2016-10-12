package cc.hqu.sends.myzhihudaily.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;



public class StartThread extends Thread {
    private String mURL;
    private String text;
    private String imageURL;
    private onLoadFinishedListener mListener;
    public interface onLoadFinishedListener {
        void onLoadFinished(String title, Bitmap bitmap);
    }

    public StartThread(String url, onLoadFinishedListener listener) {
        mURL = url;
        mListener = listener;
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
            mListener.onLoadFinished(text, getBitmap(imageURL));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
