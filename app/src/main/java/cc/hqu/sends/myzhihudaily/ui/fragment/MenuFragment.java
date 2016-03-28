package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.model.bean.Theme;
import cc.hqu.sends.myzhihudaily.model.bean.Themes;
import cc.hqu.sends.myzhihudaily.support.Constants;

/**
 * Created by shenglin on 16-3-15.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout header;
    private TextView download, favorite, index;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_main, container);
        header = (LinearLayout) view.findViewById(R.id.menu_header);
        download = (TextView) view.findViewById(R.id.menu_tv_download);
        favorite = (TextView) view.findViewById(R.id.menu_tv_favorite);
        index = (TextView) view.findViewById(R.id.menu_tv_index);
        mListView = (ListView) view.findViewById(R.id.menu_lv);
        //获取主题信息
        jsonParse(Constants.URL.ZHIHU_DAILY_NEWS_THEMES, getActivity());
        header.setOnClickListener(this);
        download.setOnClickListener(this);
        favorite.setOnClickListener(this);
        index.setOnClickListener(this);
        return view;
    }


    private void jsonParse(String url, final Context context) {
        GsonRequest<Themes> request = new GsonRequest<>(url, Themes.class,
                new Response.Listener<Themes>() {
                    @Override
                    public void onResponse(Themes response) {
                        List<Theme> themeList = response.getOthers();
                        mListView.setAdapter(new MenuAdapter(context, themeList));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyZhiHuDailyApplication.getRequestQueue().add(request);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_header: {
                break;
            }
            case R.id.menu_tv_download: {
                break;
            }
            case R.id.menu_tv_favorite: {
                break;
            }
            case R.id.menu_tv_index: {
                break;
            }
        }
    }
    private class MenuAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
        private List<Theme> data;
        private LayoutInflater mInflater;

        public MenuAdapter(Context context, List<Theme> data) {
            this.data = data;
            mInflater = LayoutInflater.from(context);
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
            if(convertView == null) {
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

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int themeId = data.get(position).getId();
            String url = Constants.URL.ZHIHU_DAILY_NEWS_THEME + id;
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
}
