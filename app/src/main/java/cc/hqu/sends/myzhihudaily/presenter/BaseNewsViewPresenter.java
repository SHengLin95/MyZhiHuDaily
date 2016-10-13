package cc.hqu.sends.myzhihudaily.presenter;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.view.IBaseNewsView;

public abstract class BaseNewsViewPresenter<V extends IBaseNewsView> extends MvpBasePresenter<V> {
    protected List<Story> data;
    protected News news;
    protected boolean isLoading = false;
    private String url;
    public void initNews(String url) {
        loadNews(url);
        this.url = url;
    }

    private void loadNews(String url) {
        isLoading = true;
        getView().showLoading();
        GsonRequest<News> newsRequest = new GsonRequest<>(url, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        news = response;
                        data = response.getStories();
                        getView().setContentData(data);
                        updateHeader(response);
                        getView().hideLoading();
                        isLoading = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isLoading = false;
                    }
                });
        MyZhiHuDailyApplication.getRequestQueue().add(newsRequest);
    }

    protected abstract void updateHeader(News news);

    public void handlerItemClick(int position) {
        if (position > 0) {
            getView().startContentActivity(data.get(position - 1).getId());
        }
    }


    public void handlerScroll() {
    }

    public void handlerRefresh() {
        loadNews(url);
    }


}
