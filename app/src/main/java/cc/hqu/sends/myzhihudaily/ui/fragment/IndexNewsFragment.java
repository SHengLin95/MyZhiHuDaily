package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.presenter.IndexNewsViewPresenter;
import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.ui.adpter.NewsHeaderAdapter;
import cc.hqu.sends.myzhihudaily.view.IIndexNewsView;

public class IndexNewsFragment extends BaseNewsFragment<IIndexNewsView, IndexNewsViewPresenter>
        implements IIndexNewsView, ViewPager.OnPageChangeListener {
    private static final int CHANGE_PAGE = 12;
    private LinearLayout mViewGroup;
    private ViewPager mViewPager;
    private NewsHeaderAdapter mPagerAdapter;
    private List<ImageView> dots;
    private int mPageSize = 0;
    private TimerHandler mTimerHandler = new TimerHandler();

    @NonNull
    @Override
    public IndexNewsViewPresenter createPresenter() {
        return new IndexNewsViewPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isIndex = true;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View onCreateHeader(Context context) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.news_header, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.main_header_vp);
        mViewGroup = (LinearLayout) headerView.findViewById(R.id.main_header_dots);
        mPagerAdapter = new NewsHeaderAdapter(context);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        StartHeaderTimer();
        return headerView;
    }

    @Override
    protected String setURL() {
        return Constants.URL.ZHIHU_DAILY_NEWS_LASTEST;
    }


    @Override
    public void updateHeader(List<Story> data) {
        mPagerAdapter.setData(data);
        mPageSize = data.size();
        setDots(mViewGroup);
    }

    private void setDots(LinearLayout mViewGroup) {
        mViewGroup.removeAllViews();
        dots = new ArrayList<>();
        for (int i = 0; i < mPageSize; i++) {
            //设置图片轮播指示器
            ImageView dot = new ImageView(getContext());
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
        mViewPager.setCurrentItem(0);
    }

    private void updateDots(int position) {
        dots.get((position - 1 + mPageSize) % mPageSize).setImageResource(R.drawable.dot_blur);
        dots.get(position % mPageSize).setImageResource(R.drawable.dot_focus);
        dots.get((position + 1) % mPageSize).setImageResource(R.drawable.dot_blur);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateDots(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                mSwipeRefreshLayout.setEnabled(true);

                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                mSwipeRefreshLayout.setEnabled(false);
                resetHeaderTimer();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                mSwipeRefreshLayout.setEnabled(true);
                break;
        }
    }

    /**
     * 设置图片轮播定时器
     */
    private void StartHeaderTimer() {
        mTimerHandler.sendEmptyMessageDelayed(CHANGE_PAGE, 5000);
    }

    private void cancelHeaderTimer() {
        mTimerHandler.removeMessages(CHANGE_PAGE);
    }

    private void resetHeaderTimer() {
        cancelHeaderTimer();
        StartHeaderTimer();
    }


    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_PAGE:
                    int currentItem = mViewPager.getCurrentItem();
                    if (currentItem < mPageSize * Constants.HEADER_PAGE_MULT - 1) {
                        mViewPager.setCurrentItem(currentItem + 1);
                    } else {
                        mViewPager.setCurrentItem(0);
                    }
                    StartHeaderTimer();
                    break;
            }
        }
    }

}
