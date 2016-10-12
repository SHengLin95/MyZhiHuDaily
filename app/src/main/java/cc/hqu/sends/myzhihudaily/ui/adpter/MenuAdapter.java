package cc.hqu.sends.myzhihudaily.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Theme;

public class MenuAdapter extends BaseAdapter {
    private List<Theme> data;
    private LayoutInflater mInflater;

    public MenuAdapter(Context context) {
        this(context, new ArrayList<Theme>());
    }

    public MenuAdapter(Context context, List<Theme> data) {
        this.data = data;
        mInflater = LayoutInflater.from(context);

    }

    public void setData(List<Theme> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder();
            holder.setTheme((TextView) convertView.findViewById(R.id.menu_item_tv));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.getTheme().setText(data.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        private TextView theme;

        public TextView getTheme() {
            return theme;
        }

        public void setTheme(TextView theme) {
            this.theme = theme;
        }
    }
}

