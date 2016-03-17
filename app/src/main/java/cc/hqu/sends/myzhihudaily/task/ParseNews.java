package cc.hqu.sends.myzhihudaily.task;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsAdapter;


public class ParseNews {
    private String url;
    private ListView mListView;
    private Context context;
    private final RequestQueue mRequestQueue;
    private SwipeRefreshLayout mRefreshLayout;
    public ParseNews(Context context, String url, ListView listView) {
        this.context = context;
        this.url = url;
        mRequestQueue = MyZhiHuDailyApplication.getRequestQueue();
        mListView = listView;
    }

    public ParseNews(Context context, String url, ListView listView, SwipeRefreshLayout RefreshLayout) {
        this(context, url, listView);
        mRefreshLayout = RefreshLayout;
    }

    private void gsonParse() {
        GsonRequest<News> newsRequest = new GsonRequest<>(url, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        List<Story> newsList = response.getStories();
                        mListView.setAdapter(new NewsAdapter(context, mListView, newsList));
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(newsRequest);
    }

    /**
     *  不能这样获得list,gsonParse()是异步线程,
     *  该线程还未更新newsList的值,
     *  return语句就已经执行
     */
/*    public List<Story> getNews() {
        gsonParse();
        return newsList;
    }*/

    public void execute() {
        gsonParse();
    }
}
