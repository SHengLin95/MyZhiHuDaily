package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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

import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;

/**
 * Created by SHeng_Lin on 2016/3/12.
 */
public class NewsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{
    private List<Story> newsList;
    private LayoutInflater mInflater;
    private final ImageLoader mImageLoader;
    private final DisplayImageOptions mOptions;
    public NewsAdapter(Context context, ListView listView, List<Story> newsList) {
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
        //为ListView绑定ImageLoader的滚动监听器
        listView.setOnScrollListener(new PauseOnScrollListener(mImageLoader, true, true));
        //为ListView绑定item点击监听器
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
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
            convertView = mInflater.inflate(R.layout.main_lv_item, null);
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
        if(images != null) {
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

    }

    public void addMore(List<Story> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("tag", "onScroll");
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
