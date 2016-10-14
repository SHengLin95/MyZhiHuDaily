package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.ui.activity.ContentActivity;


public class NewsHeaderAdapter extends PagerAdapter implements View.OnClickListener {

    private static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // Use 1/8th of the available memory for this memory cache.
    private static final int cacheSize = maxMemory / 8;
    private LruCache<Long, View> viewMap;

    private Context context;
    private List<Story> data;


    public NewsHeaderAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        viewMap = new LruCache<>(cacheSize);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view;
        ViewHolder viewHolder;
        Story bean = data.get(position);
        if ((view = viewMap.get(bean.getId())) == null) {
            view = LayoutInflater.from(context).inflate(R.layout.news_header_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.main_header_image);
            TextView title = (TextView) view.findViewById(R.id.main_header_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ContentActivity.class);
                    intent.putExtra(Constants.ZHIHU_CONTENT_ID, data.get(position).getId());
                    intent.putExtra(Constants.ZHIHU_CONTENT_IS_INDEX, true);
                    context.startActivity(intent);
                }
            });

            viewHolder = new ViewHolder();
            viewHolder.image = image;
            viewHolder.title = title;
            view.setTag(viewHolder);
            viewMap.put(bean.getId(), view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.title.setText(bean.getTitle());
        Picasso.with(context).load(bean.getImage()).into(viewHolder.image);
        container.addView(view);
        return view;
    }

    public void setData(List<Story> topStoryList) {
        this.data = topStoryList;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        long keyId = data.get(position).getId();
        container.removeView(viewMap.get(keyId));
        viewMap.remove(keyId);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onClick(View v) {

    }

    private class ViewHolder {
        ImageView image;
        TextView title;
    }

}
