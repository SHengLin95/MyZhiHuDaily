package cc.hqu.sends.myzhihudaily.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.model.bean.Theme;
import cc.hqu.sends.myzhihudaily.presenter.MenuViewPresenter;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.ui.activity.MainActivity;
import cc.hqu.sends.myzhihudaily.ui.adpter.MenuAdapter;
import cc.hqu.sends.myzhihudaily.view.IMenuFragmentView;

import static android.R.attr.data;


public class MenuFragment extends BaseFragment<IMenuFragmentView, MenuViewPresenter>
        implements IMenuFragmentView, AdapterView.OnItemClickListener {

    private MainActivity mActivity;
    private MenuAdapter mAdapter;

    @Override
    public MenuViewPresenter createPresenter() {
        return new MenuViewPresenter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_main, null);
        initView(view);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getMenuList(Constants.URL.ZHIHU_DAILY_NEWS_THEMES);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.menu_lv);
        listView.setOnItemClickListener(this);

        TextView indexTextView = (TextView) view.findViewById(R.id.menu_tv_index);
        indexTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.itemCLick(-1);
            }
        });

        mAdapter = new MenuAdapter(mActivity);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void changeContent(String url) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        newsFragment.setArguments(bundle);
        transaction.replace(R.id.main_content_ll, newsFragment);


        transaction.commit();

    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {
        mActivity.closeDrawerLayout();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        try {
            throw e;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void setData(List<Theme> data) {
        mAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.itemCLick(position);
    }


}
