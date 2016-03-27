package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.hqu.sends.myzhihudaily.support.Constants;

/**
 * Created by shenglin on 16-3-19.
 */
public class NewsHeaderAdapter extends PagerAdapter {
    private List<View> viewList;
    private int viewCount;

    public NewsHeaderAdapter(List<View> viewList) {
        this.viewList = viewList;
        viewCount = viewList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position % viewCount);
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
