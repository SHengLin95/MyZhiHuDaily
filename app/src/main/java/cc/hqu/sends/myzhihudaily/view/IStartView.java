package cc.hqu.sends.myzhihudaily.view;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface IStartView extends MvpView {
    void updateInformation(String text, String img);
}
