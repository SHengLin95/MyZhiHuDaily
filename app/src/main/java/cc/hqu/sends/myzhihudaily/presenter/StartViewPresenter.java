package cc.hqu.sends.myzhihudaily.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import cc.hqu.sends.myzhihudaily.MyZhiHuDailyApplication;
import cc.hqu.sends.myzhihudaily.model.bean.Index;
import cc.hqu.sends.myzhihudaily.model.GsonRequest;
import cc.hqu.sends.myzhihudaily.view.IStartView;


public class StartViewPresenter extends MvpBasePresenter<IStartView> {

    public void loadData(String url) {
        GsonRequest<Index> indexRequest = new GsonRequest<Index>(url, Index.class,
            new Response.Listener<Index>() {
                @Override
                public void onResponse(Index response) {
                    getView().updateInformation(response.getText(), response.getImg());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });
        MyZhiHuDailyApplication.getRequestQueue().add(indexRequest);
    }

}
