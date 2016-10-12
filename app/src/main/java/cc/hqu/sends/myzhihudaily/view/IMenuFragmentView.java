package cc.hqu.sends.myzhihudaily.view;

import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.Theme;


public interface IMenuFragmentView extends MvpLceView<List<Theme>> {
    void changeContent(Fragment fragment);
}
