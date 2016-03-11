package cc.hqu.sends.myzhihudaily.task;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import cc.hqu.sends.myzhihudaily.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;


public class ParseNews {
    private List<Story> newsList;
    private String url;
    private RequestQueue mRequestQueue;

    public ParseNews(String url, RequestQueue mRequestQueue) {
        this.url = url;
        this.mRequestQueue = mRequestQueue;
    }

    private void gsonParse() {
        GsonRequest<News> newsRequest = new GsonRequest<>(url, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        newsList = response.getStories();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        newsList = null;
                    }
                });
        mRequestQueue.add(newsRequest);
    }

    public List<Story> getNews() {
        gsonParse();
        return newsList;
    }
}
