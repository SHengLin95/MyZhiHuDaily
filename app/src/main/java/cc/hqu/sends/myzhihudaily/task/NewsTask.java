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


public class NewsTask extends BaseTask implements ViewPager.OnPageChangeListener {

    private String url;
    private ListView mListView;
    private NewsAdapter mAdapter;
    private Context context;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isIndex = false;
    private View headerView;
    private ViewPager mViewPager;
    private ImageView mImageView;
    private TextView mTextView;
    private int pageSize;
    private List<ImageView> dots;
    private final Handler handler;
    private final Timer mTimer;
    private myTimer currentTask;

    public NewsTask(Context context, String url, ListView listView) {
        this.context = context;
        this.url = url;
        mListView = listView;
        handler = new Handler();
        mTimer = new Timer();

    }

    public NewsTask(Context context, String url, ListView listView, SwipeRefreshLayout refreshLayout) {
        this(context, url, listView);
        mRefreshLayout = refreshLayout;
    }

    public NewsTask(Context context, String url, ListView listView, SwipeRefreshLayout refreshLayout, boolean isIndex) {
        this(context, url, listView, refreshLayout);
        this.isIndex = isIndex;

    }

    /**
     * 初始化数据
     */
    private void initData() {
        GsonRequest<News> newsRequest = new GsonRequest<>(url, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        //加入头部
                        if (isIndex) {
                            List<Story> topStoryList = response.getTop_stories();
                            addViewPage(topStoryList);
                        } else {
                            addImage(response.getImage(), response.getDescription());
                        }
                        List<Story> newsList = response.getStories();
                        mAdapter = new NewsAdapter(context, mListView, newsList, isIndex);
                        mListView.setAdapter(mAdapter);
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
     * 更新数据
     */
    private void updateNews() {
        GsonRequest<News> request = new GsonRequest<News>(url, News.class, new Response.Listener<News>() {
            @Override
            public void onResponse(News response) {
                if (isIndex) {
                    List<Story> topStories = response.getTop_stories();
                    updateViewPage(topStories, true);
                } else {
                    updateImage(response.getImage(), response.getDescription());
                }
                List<Story> stories = response.getStories();
                mListView.setAdapter(new NewsAdapter(context, mListView, stories, isIndex));
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }

    /**
     * 配置listView的header
     */


    private void addImage(String imageURL, String title) {
        headerView = LayoutInflater.from(context).inflate(R.layout.theme_list_header, null);
        mImageView = (ImageView) headerView.findViewById(R.id.theme_header_image);
        mTextView = (TextView) headerView.findViewById(R.id.theme_header_title);
        updateImage(imageURL, title);

        mListView.addHeaderView(headerView);
    }

    private void updateImage(String imageURL, String title) {
        mTextView.setText(title);
        mImageLoader.displayImage(imageURL, mImageView, mOptions);
    }


    private void addViewPage(List<Story> topStoryList) {
        headerView = LayoutInflater.from(context).inflate(R.layout.news_header, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.main_header_vp);

        updateViewPage(topStoryList, false);
        mListView.addHeaderView(headerView);
        StartHeaderTimer();
    }


    private void updateViewPage(List<Story> topStoryList, boolean isSetDots) {
        LinearLayout mViewGroup = (LinearLayout) headerView.findViewById(R.id.main_header_dots);
        pageSize = topStoryList.size();
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            Story bean = topStoryList.get(i);
            View view = LayoutInflater.from(context).inflate(R.layout.news_header_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.main_header_image);
            TextView title = (TextView) view.findViewById(R.id.main_header_title);

            mImageLoader.displayImage(bean.getImage(), image, mOptions);
            title.setText(bean.getTitle());
            viewList.add(view);
        }
        if (isSetDots) {
            mViewGroup.removeAllViews();
            mViewPager.removeAllViews();
        } else {
            mViewPager.addOnPageChangeListener(this);
        }
        setDots(mViewGroup);
        NewsHeaderAdapter headerAdapter = new NewsHeaderAdapter(viewList);
        mViewPager.setAdapter(headerAdapter);
        mViewPager.setCurrentItem(Constants.HEADER_PAGE_MULT / 2 * pageSize);
    }

    private void setDots(LinearLayout mViewGroup) {
        dots = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
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
            dots.add(dot);
            mViewGroup.addView(dot, params);
        }

    }

    /**
     * 设置图片轮播定时器
     */
    private void StartHeaderTimer() {
        currentTask = new myTimer();
        mTimer.schedule(currentTask, 5000);
    }

    private void cancelHeaderTimer() {
        currentTask.cancel();
        mTimer.purge();
    }

    private void resetHeaderTimer() {
        cancelHeaderTimer();
        StartHeaderTimer();
    }

    class myTimer extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int currentItem = mViewPager.getCurrentItem();
                    if (currentItem < pageSize * Constants.HEADER_PAGE_MULT - 1) {
                        mViewPager.setCurrentItem(currentItem + 1);
                    } else {
                        mViewPager.setCurrentItem(0);
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
    public void start() {
        initData();
    }

    public void update() {
        updateNews();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滚动完成后,刷新轮播指示器

        dots.get((position - 1 + pageSize) % pageSize).setImageResource(R.drawable.dot_blur);
        dots.get(position % pageSize).setImageResource(R.drawable.dot_focus);
        dots.get((position + 1) % pageSize).setImageResource(R.drawable.dot_blur);
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