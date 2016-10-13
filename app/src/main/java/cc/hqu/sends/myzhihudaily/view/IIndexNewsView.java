package cc.hqu.sends.myzhihudaily.view;


import java.util.List;

import cc.hqu.sends.myzhihudaily.model.bean.Story;

public interface IIndexNewsView extends IBaseNewsView {
    void updateHeader(List<Story> data);
}
