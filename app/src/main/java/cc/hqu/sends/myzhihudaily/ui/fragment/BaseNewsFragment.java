package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.presenter.BaseNewsViewPresenter;
import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.ui.activity.ContentActivity;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsAdapter;
import cc.hqu.sends.myzhihudaily.view.IBaseNewsView;

public abstract class BaseNewsFragment<V extends IBaseNewsView, P extends BaseNewsViewPresenter<V>>
        extends BaseFragment<V, P>
        implements SwipeRefreshLayout.OnRefreshListener, IBaseNewsView, AdapterView.OnItemClickListener{
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mNewsAdapter;
    protected boolean isIndex = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_list, null);

        ListView listView = (ListView) view.findViewById(R.id.news_lv);
        mNewsAdapter = new NewsAdapter(getContext());
        listView.addHeaderView(onCreateHeader(getContext()));
        listView.setAdapter(mNewsAdapter);
        listView.setOnScrollListener(new myScrollListener(ImageLoader.getInstance(), true, true));
        listView.setOnItemClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.initNews(setURL());
    }

    protected abstract View onCreateHeader(Context context);

    protected abstract String setURL();

    @Override
    public void onRefresh() {
        presenter.handlerRefresh();
    }


    @Override
    public void setContentData(List<Story> data) {
        mNewsAdapter.setData(data);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startContentActivity(long contentId) {
        Intent intent = new Intent(getContext(), ContentActivity.class);
        intent.putExtra(Constants.ZHIHU_CONTENT_ID, contentId);
        intent.putExtra(Constants.ZHIHU_CONTENT_IS_INDEX, isIndex);
        getContext().startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.handlerItemClick(position);
    }

    private class myScrollListener extends PauseOnScrollListener {

        public myScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
            super(imageLoader, pauseOnScroll, pauseOnFling);
        }

        public myScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener customListener) {
            super(imageLoader, pauseOnScroll, pauseOnFling, customListener);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            super.onScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            if (isIndex) {
                if (totalItemCount != 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 2) {
                    if (presenter != null) {
                        presenter.handlerScroll();
                    }
                }
            }
        }
    }
}
