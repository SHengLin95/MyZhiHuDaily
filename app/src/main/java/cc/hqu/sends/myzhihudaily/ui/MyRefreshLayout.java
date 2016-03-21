package cc.hqu.sends.myzhihudaily.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by shenglin on 16-3-21.
 */
public class MyRefreshLayout extends SwipeRefreshLayout {
    public MyRefreshLayout(Context context) {
        super(context);
    }

    @Override
    public boolean canChildScrollUp() {
        return super.canChildScrollUp();
    }
}
