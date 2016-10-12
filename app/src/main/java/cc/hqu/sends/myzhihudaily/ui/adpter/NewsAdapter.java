package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.model.task.AddMoreTask;
import cc.hqu.sends.myzhihudaily.ui.activity.ContentActivity;

public class NewsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List<Story> newsList;
    private LayoutInflater mInflater;
    private final ImageLoader mImageLoader;
    private final DisplayImageOptions mOptions;
    private AddMoreTask updateTask;
    private Calendar date;
    private SimpleDateFormat mDateFormat;
    private boolean isIndex = false;

    public NewsAdapter(Context context, ListView listView, List<Story> newsList, boolean isIndex) {
        this.context = context;
        this.newsList = newsList;
        mInflater = LayoutInflater.from(context);
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        updateTask = new AddMoreTask(this);

        this.isIndex = isIndex;
        //为ListView绑定ImageLoader的滚动监听器
        listView.setOnScrollListener(new myScrollListener(mImageLoader, true, true));
        if (isIndex) {
            //获取日期信息,设置上拉加载更多
            mDateFormat = new SimpleDateFormat("yyyyMMdd");
            date = Calendar.getInstance();
            date.setTime(new Date());
        }

        //为ListView绑定item点击监听器
        listView.setOnItemClickListener(this);

    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.news_list_item, null);
            //不要忘记初始化ViewHolder
            holder = new ViewHolder();
            holder.setImage((ImageView) convertView.findViewById(R.id.item_image));
            holder.setTitle((TextView) convertView.findViewById(R.id.item_title));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Story beans = newsList.get(position);
        String[] images = beans.getImages();
        ImageView mImageView = holder.getImage();
        if (images != null) {
            mImageLoader.displayImage(images[0],
                    mImageView, mOptions);
        } else {
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }

        holder.getTitle().setText(beans.getTitle());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //header也占用一个position
//        long contentId = newsList.get(position).getId();
        long contentId = newsList.get(position - 1).getId();
        if (position != 0) {
            Intent intent = new Intent(context, ContentActivity.class);
            intent.putExtra(Constants.ZHIHU_CONTENT_ID, contentId);
            intent.putExtra(Constants.ZHIHU_CONTENT_IS_INDEX, isIndex);
            context.startActivity(intent);
        }
    }

    public void addMore(List<Story> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    class myScrollListener extends PauseOnScrollListener {

        public myScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
            super(imageLoader, pauseOnScroll, pauseOnFling);
        }

        public myScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener customListener) {
            super(imageLoader, pauseOnScroll, pauseOnFling, customListener);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            super.onScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            if (isIndex) {
                if (totalItemCount != 0 && firstVisibleItem + visibleItemCount >= totalItemCount - 2) {
                    if (!updateTask.isLoading()) {
                        //添加当前日期,并将日期定位到前一天
                        String dateString = mDateFormat.format(date.getTime());
                        if (!dateString.equals(Constants.ZHIHU_DAILY_BIRTHDAY)) {
                            updateTask.addMore(dateString);
                            date.add(Calendar.DAY_OF_YEAR, -1);
                        }
                    }
                }
            }
        }
    }


    private class ViewHolder {
        private ImageView image;
        private TextView title;

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }


    }
}
