package cc.hqu.sends.myzhihudaily.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.model.bean.Theme;
import cc.hqu.sends.myzhihudaily.model.bean.Themes;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.support.Constants;
import cc.hqu.sends.myzhihudaily.view.IMenuFragmentView;


public class MenuViewPresenter extends MvpBasePresenter<IMenuFragmentView> {
    private List<Theme> themeList;
    private int curPosition = -1;

    public void getMenuList(String url) {
        GsonRequest<Themes> request = new GsonRequest<>(url, Themes.class,
                new Response.Listener<Themes>() {
                    @Override
                    public void onResponse(Themes response) {
                        themeList = response.getOthers();
                        getView().setData(themeList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getView().showError(error, false);
            }
        });
        MyZhiHuDailyApplication.getRequestQueue().add(request);
    }

    public void itemCLick(int position) {
        if (curPosition != position) {
            String url;
            if (position == -1) {
                url = Constants.URL.ZHIHU_DAILY_NEWS_LASTEST;
            } else {
                int themeId = themeList.get(position).getId();
                url = Constants.URL.ZHIHU_DAILY_NEWS_THEME + themeId;
            }
            getView().changeContent(url);
            curPosition = position;
        }
        getView().showContent();
    }
}
