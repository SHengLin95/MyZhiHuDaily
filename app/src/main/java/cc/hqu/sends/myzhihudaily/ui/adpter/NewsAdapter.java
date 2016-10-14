package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Story;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<Story> newsList;
    private LayoutInflater mInflater;

    private onItemClickListener itemClickListener;
    private View headView;
    private static final int VIEW_HEADER = 0, VIEW_NORMAL = 1;

    public NewsAdapter(Context context, View headView) {
        this.context = context;
        mInflater = LayoutInflater.from(context);

        newsList = new ArrayList<>();
        this.headView = headView;
    }

    public NewsAdapter(Context context) {
        this(context, null);
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_HEADER) {
            return new ViewHolder(headView, viewType);
        }
        return new ViewHolder(mInflater.inflate(R.layout.news_list_item, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_HEADER) {

        } else {
            if (headView != null) {
                position--;
            }
            Story beans = newsList.get(position);
            String[] images = beans.getImages();
            if (images != null) {
                holder.image.setVisibility(View.VISIBLE);
                Picasso.with(context).load(images[0])
                        .placeholder(R.mipmap.ic_launcher).into(holder.image);
            } else {
                holder.image.setVisibility(View.GONE);
            }

            holder.title.setText(beans.getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null) {
            return VIEW_HEADER;
        } else {
            return VIEW_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setData(List<Story> data) {
        newsList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView title;

        public ViewHolder(View view, int viewType) {
            super(view);
            if (viewType == VIEW_HEADER) {

            } else {
                view.setOnClickListener(this);
                image = (ImageView) view.findViewById(R.id.item_image);
                title = (TextView) view.findViewById(R.id.item_title);
            }
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
