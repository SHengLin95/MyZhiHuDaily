package cc.hqu.sends.myzhihudaily.presenter;

import android.graphics.Bitmap;
import android.os.Handler;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import cc.hqu.sends.myzhihudaily.model.StartThread;
import cc.hqu.sends.myzhihudaily.view.IStartView;


public class StartViewPresenter extends MvpBasePresenter<IStartView> {
    private Handler handler = new Handler();

    public void loadData(String url) {
        new StartThread(url, new StartThread.onLoadFinishedListener() {
            @Override
            public void onLoadFinished(final String title, final Bitmap bitmap) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().updateInformation(title, bitmap);
                    }
                });
            }
        }).start();
    }

}
