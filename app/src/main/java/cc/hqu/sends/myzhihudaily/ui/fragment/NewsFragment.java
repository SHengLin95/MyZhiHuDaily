package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.task.ParseNews;

/**
 * Created by shenglin on 16-3-16.
 */
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String url;
    private ParseNews mParseNews;
    private Calendar date;
    private SimpleDateFormat mDateFormat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_list, null);

        mListView = (ListView) view.findViewById(R.id.news_lv);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        if ((url = getArguments().getString("url")) == null) {
            url = Constants.URL.ZHIHU_DAILY_NEWS_LASTEST;
        }

        // mListView.setAdapter(new NewsAdapter(this, mListView, new ParseNews(Constants.URL.ZHIHU_DAILY_NEWS_LASTEST).getNews()));
        mParseNews = new ParseNews(getActivity(), url, mListView, mSwipeRefreshLayout, true);
        mParseNews.execute();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("tab", "onItemClick");
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        if(firstVisibleItem + visibleItemCount == totalItemCount ) {
//            if (! mParseNews.isLoading()) {
//                mParseNews.addMore(mDateFormat.format(date));
//                //日期减一天
//                date.add(Calendar.DAY_OF_YEAR, -1);
//            }
//        }
                Log.d("tab", "onScroll");
            }
        });
        //获得日期
        mDateFormat = new SimpleDateFormat("yyyyMMdd");
        date = Calendar.getInstance();
        date.setTime(new Date());
        return view;
    }

    @Override
    public void onRefresh() {
        new ParseNews(getActivity(), url, mListView, mSwipeRefreshLayout).execute();
    }



}
