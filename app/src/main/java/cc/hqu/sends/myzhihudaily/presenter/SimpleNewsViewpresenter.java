package cc.hqu.sends.myzhihudaily.presenter;

import cc.hqu.sends.myzhihudaily.model.bean.News;
import cc.hqu.sends.myzhihudaily.view.ISimpleNewsView;


public class SimpleNewsViewPresenter extends BaseNewsViewPresenter<ISimpleNewsView> {

    @Override
    protected void updateHeader(News news) {
        getView().updateHeader(news.getImage(), news.getDescription());
    }


}
