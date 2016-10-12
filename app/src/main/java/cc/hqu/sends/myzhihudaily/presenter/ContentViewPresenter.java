package cc.hqu.sends.myzhihudaily.presenter;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.model.bean.Content;
import cc.hqu.sends.myzhihudaily.model.data.GsonRequest;
import cc.hqu.sends.myzhihudaily.view.IContentView;

public class ContentViewPresenter extends MvpBasePresenter<IContentView>{
    public void loadData(String url) {
        GsonRequest<Content> request = new GsonRequest<Content>(url, Content.class,
                new Response.Listener<Content>() {
                    @Override
                    public void onResponse(Content response) {
                        getView().loadWebView(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyZhiHuDailyApplication.getRequestQueue().add(request);
    }
}
