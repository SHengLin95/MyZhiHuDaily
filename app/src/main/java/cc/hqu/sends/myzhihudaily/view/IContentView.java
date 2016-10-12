package cc.hqu.sends.myzhihudaily.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import cc.hqu.sends.myzhihudaily.model.bean.Content;

public interface IContentView extends MvpView {
    void loadWebView(Content content);
}
