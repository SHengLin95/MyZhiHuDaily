package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<Story> newsList;
    private LayoutInflater mInflater;
    private final ImageLoader mImageLoader;
    private final DisplayImageOptions mOptions;

    public NewsAdapter(Context context) {
        this.context = context;

        mInflater = LayoutInflater.from(context);
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        newsList = new ArrayList<>();

//        updateTask = new AddMoreTask(this);
//
//        this.isIndex = isIndex;
//        //为ListView绑定ImageLoader的滚动监听器
//        listView.setOnScrollListener(new myScrollListener(mImageLoader, true, true));
//        if (isIndex) {
//            //获取日期信息,设置上拉加载更多
//            mDateFormat = new SimpleDateFormat("yyyyMMdd");
//            date = Calendar.getInstance();
//            date.setTime(new Date());
//        }
//
//        //为ListView绑定item点击监听器
//        listView.setOnItemClickListener(this);

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



    public void setData(List<Story> data) {
        newsList = data;
        notifyDataSetChanged();
    }


    public void addMore(List<Story> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
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
