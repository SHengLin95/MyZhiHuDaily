package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.presenter.BaseNewsViewPresenter;
import cc.hqu.sends.myzhihudaily.ui.activity.ContentActivity;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsAdapter;
import cc.hqu.sends.myzhihudaily.view.IBaseNewsView;

public abstract class BaseNewsFragment<V extends IBaseNewsView, P extends BaseNewsViewPresenter<V>>
        extends BaseFragment<V, P>
        implements SwipeRefreshLayout.OnRefreshListener, IBaseNewsView, NewsAdapter.onItemClickListener {
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mNewsAdapter;
    protected boolean isIndex = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_list, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_lv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mNewsAdapter = new NewsAdapter(getContext(), onCreateHeader(getContext()));
        recyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (dy > lastItemPosition - 5) {
                    presenter.handlerScroll();
                }
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
    public void onItemClick(View view, int position) {
        presenter.handlerItemClick(position);
    }

}
