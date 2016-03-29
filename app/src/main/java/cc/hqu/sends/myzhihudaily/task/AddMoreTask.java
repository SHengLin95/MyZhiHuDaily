package cc.hqu.sends.myzhihudaily.task;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsAdapter;

/**
 * Created by SHeng_Lin on 2016/3/25.
 */
public class AddMoreTask extends BaseTask {
    private NewsAdapter mAdapter;
    private boolean isLoading = false;

    public AddMoreTask(NewsAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public void addMore(String date) {
        if (!isLoading) {
            isLoading = true;
            String NewURL = Constants.URL.ZHIHU_DAILY_NEWS_BEFORE + date;
            GsonRequest<News> newsRequest = new GsonRequest<News>(NewURL, News.class,
                    new Response.Listener<News>() {
                        @Override
                        public void onResponse(News response) {
                            List<Story> stories = response.getStories();
                            mAdapter.addMore(stories);
                            isLoading = false;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mRequestQueue.add(newsRequest);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }
}
