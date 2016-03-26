package cc.hqu.sends.myzhihudaily.task;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsAdapter;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsHeaderAdapter;


public class ParseNews extends BaseNews implements ViewPager.OnPageChangeListener {
    private ParseNews mTask;
    private String url;
    private ListView mListView;
    private NewsAdapter mAdapter;
    private Context context;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean hasHeader = false;
    private View headerView;
    private ViewPager mViewPager;
    private int pageLength;
    private List<ImageView> dots;
    private final Handler handler;
    private final Timer mTimer;
    private myTimer currentTask;
    private boolean isLoaded = false;

    public ParseNews(Context context, String url, ListView listView) {
        this.context = context;
        this.url = url;
        mListView = listView;
        handler = new Handler();
        mTimer = new Timer();
        mTask = this;
    }

    public ParseNews(Context context, String url, ListView listView, SwipeRefreshLayout refreshLayout) {
        this(context, url, listView);
        mRefreshLayout = refreshLayout;
    }

    public ParseNews(Context context, String url, ListView listView, SwipeRefreshLayout refreshLayout, boolean hasHeader) {
        this(context, url, listView, refreshLayout);
        this.hasHeader = hasHeader;

        headerView = LayoutInflater.from(context).inflate(R.layout.news_header, null);


    }


    private void initData() {
        GsonRequest<News> newsRequest = new GsonRequest<>(url, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        //加入头部
                        if (hasHeader) {
                            List<Story> topStoryList = response.getTop_stories();
                            addHeader(topStoryList);
                        }
                        List<Story> newsList = response.getStories();
                        mAdapter = new NewsAdapter(context, mListView, newsList, mTask);
                        mListView.setAdapter(mAdapter);
                        if (mRefreshLayout != null) {
                            mRefreshLayout.setRefreshing(false);
                        }
                        isLoaded = true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(newsRequest);


    }


    public boolean isLoaded() {
        return isLoaded;
    }

    private void addHeader(List<Story> topStoryList) {
        dots = new ArrayList<>();
        mViewPager = (ViewPager) headerView.findViewById(R.id.main_header_vp);
        LinearLayout mViewGroup = (LinearLayout) headerView.findViewById(R.id.main_header_dots);
        pageLength = topStoryList.size();
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < pageLength; i++) {
            Story bean = topStoryList.get(i);
            View view = LayoutInflater.from(context).inflate(R.layout.news_header_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.main_header_image);
            TextView title = (TextView) view.findViewById(R.id.main_header_title);

            mImageLoader.displayImage(bean.getImage(), image, mOptions);
            title.setText(bean.getTitle());
            viewList.add(view);

            //设置图片轮播指示器
            ImageView dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.rightMargin = 5;
            params.leftMargin = 5;
            if (i != 0) {
                dot.setImageResource(R.drawable.dot_blur);
            } else {
                dot.setImageResource(R.drawable.dot_focus);
            }
            mViewGroup.addView(dot, params);
            dots.add(dot);
        }


        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new NewsHeaderAdapter(viewList));
        mViewPager.setCurrentItem(Constants.HEADER_PAGE_MULT / 2 * pageLength);
        StartHeaderTimer();
        mListView.addHeaderView(headerView);
    }

    private void StartHeaderTimer() {
        currentTask = new myTimer();
        mTimer.schedule(currentTask, 5000);
    }

    private void resetHeaderTimer() {
        currentTask.cancel();
        mTimer.purge();
        StartHeaderTimer();
    }

    class myTimer extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int currentItem = mViewPager.getCurrentItem();
                    if (currentItem < pageLength * Constants.HEADER_PAGE_MULT - 1) {
                        mViewPager.setCurrentItem(currentItem + 1);
                    } else {
                        mViewPager.setCurrentItem(0, false);
                    }
                }
            });
            StartHeaderTimer();
        }
    }


    /**
     * 不能这样获得list,initData()是异步线程,
     * 该线程还未更新newsList的值,
     * return语句就已经执行
     */
/*    public List<Story> getNews() {
        initData();
        return newsList;
    }*/
    public void execute() {
        initData();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滚动完成后,刷新轮播指示器

        dots.get((position - 1 + pageLength) % pageLength).setImageResource(R.drawable.dot_blur);
        dots.get(position % pageLength).setImageResource(R.drawable.dot_focus);
        dots.get((position + 1) % pageLength).setImageResource(R.drawable.dot_blur);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                mRefreshLayout.setEnabled(true);

                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                mRefreshLayout.setEnabled(false);
                resetHeaderTimer();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                mRefreshLayout.setEnabled(true);

        }
    }
}
