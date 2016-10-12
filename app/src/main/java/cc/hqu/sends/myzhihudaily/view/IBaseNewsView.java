package cc.hqu.sends.myzhihudaily.view;

import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.Story;


public interface IBaseNewsView extends MvpView {
    void setContentData(List<Story> data);

    void showLoading();

    void hideLoading();

    void startContentActivity(long contentId);


}
