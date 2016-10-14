package cc.hqu.sends.myzhihudaily.presenter;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.model.GsonRequest;
import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.view.IIndexNewsView;

public class IndexNewsViewPresenter extends BaseNewsViewPresenter<IIndexNewsView> {
    private Calendar date;
    private SimpleDateFormat mDateFormat;

    public IndexNewsViewPresenter() {
        //获取日期信息,设置上拉加载更多
        mDateFormat = new SimpleDateFormat("yyyyMMdd");
        date = Calendar.getInstance();
        date.setTime(new Date());
    }

    private void loadMore() {
        if (!isLoading) {
            //添加当前日期,并将日期定位到前一天
            String dateString = mDateFormat.format(date.getTime());
            if (!dateString.equals(Constants.ZHIHU_DAILY_BIRTHDAY)) {
                addMore(dateString);
                date.add(Calendar.DAY_OF_YEAR, -1);
            }

        }
    }

    private void addMore(String dateString) {
        getView().showLoading();
        isLoading = true;
        String NewURL = Constants.URL.ZHIHU_DAILY_NEWS_BEFORE + dateString;
        GsonRequest<News> newsRequest = new GsonRequest<News>(NewURL, News.class,
                new Response.Listener<News>() {
                    @Override
                    public void onResponse(News response) {
                        data.addAll(response.getStories());
                        getView().setContentData(data);
                        getView().hideLoading();
                        isLoading = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoading = false;
            }
        });
        MyZhiHuDailyApplication.getRequestQueue().add(newsRequest);
    }


    @Override
    protected void updateHeader(News news) {
        List<Story> topStoryList = news.getTop_stories();
        getView().updateHeader(topStoryList);
    }

    @Override
    public void handlerScroll() {
        super.handlerScroll();
        loadMore();
    }


}
