package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.ui.activity.ContentActivity;

/**
 * Created by shenglin on 16-3-19.
 */
public class NewsHeaderAdapter extends PagerAdapter {
    private List<View> viewList;
    private int viewCount;
    private Context context;
    private List<Story> topStoryList;

    public NewsHeaderAdapter(Context context, List<View> viewList, List<Story> topStoryList) {
        this.context = context;
        this.viewList = viewList;
        this.topStoryList = topStoryList;

        viewCount = viewList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = viewList.get(position % viewCount);
        //设置图片轮播点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long contentId = topStoryList.get(position % viewCount).getId();
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(Constants.ZHIHU_CONTENT_ID, contentId);
                context.startActivity(intent);
            }
        });

        container.addView(view);
        //Log.d("tag", "Create " + position + " " + position % viewCount);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position % viewCount));
        //  Log.d("tag", "remove " + position + " " + position % viewCount);
    }

    @Override
    public int getCount() {
        return Constants.HEADER_PAGE_MULT * viewCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
