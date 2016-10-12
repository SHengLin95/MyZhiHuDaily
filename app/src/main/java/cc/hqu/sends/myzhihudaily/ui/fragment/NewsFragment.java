package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.model.task.NewsTask;

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String url;
    private NewsTask mTask;
    private boolean isIndex = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_list, null);

        mListView = (ListView) view.findViewById(R.id.news_lv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        url = getArguments().getString("url");
        if (url == null) {
            url = Constants.URL.ZHIHU_DAILY_NEWS_LASTEST;
            isIndex = true;
        } else if (url.equals(Constants.URL.ZHIHU_DAILY_NEWS_LASTEST)) {
            isIndex = true;
        }

        mTask = new NewsTask(getActivity(), url, mListView, mSwipeRefreshLayout, isIndex);
        mTask.start();
        //获得日期

        return view;
    }

    @Override
    public void onRefresh() {
        mTask.update();
    }

}
