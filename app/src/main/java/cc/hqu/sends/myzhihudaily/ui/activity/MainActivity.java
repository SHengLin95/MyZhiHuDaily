package cc.hqu.sends.myzhihudaily.ui.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.ui.fragment.IndexNewsFragment;
import cc.hqu.sends.myzhihudaily.ui.fragment.MenuFragment;

public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private long firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        initView();
        loadNews();
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }


    private void loadNews() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        IndexNewsFragment newsFragment = new IndexNewsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("url", Constants.URL.ZHIHU_DAILY_NEWS_LASTEST);
//        newsFragment.setArguments(bundle);
        transaction.replace(R.id.main_content_ll, newsFragment);

        MenuFragment navigation = new MenuFragment();
        transaction.replace(R.id.main_navigation, navigation);
        transaction.commit();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //在toolbar加入侧边栏图标
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeDrawerLayout();
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                finish();
            }
        }
    }
}
