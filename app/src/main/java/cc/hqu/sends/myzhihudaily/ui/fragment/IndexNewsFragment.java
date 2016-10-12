package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.presenter.IndexNewsViewPresenter;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.view.IIndexNewsView;

public class IndexNewsFragment extends BaseNewsFragment<IIndexNewsView, IndexNewsViewPresenter>
    implements IIndexNewsView{
    private ViewPager mViewPager;

    @NonNull
    @Override
    public IndexNewsViewPresenter createPresenter() {
        return new IndexNewsViewPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isIndex = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreateHeader(Context context) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.news_header, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.main_header_vp);

        return headerView;
    }

    @Override
    protected String setURL() {
        return Constants.URL.ZHIHU_DAILY_NEWS_LASTEST;
    }



}
