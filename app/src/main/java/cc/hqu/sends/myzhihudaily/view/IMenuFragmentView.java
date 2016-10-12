package cc.hqu.sends.myzhihudaily.view;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.Theme;


public interface IMenuFragmentView extends MvpLceView<List<Theme>> {
    void changeContent(String url);
}
