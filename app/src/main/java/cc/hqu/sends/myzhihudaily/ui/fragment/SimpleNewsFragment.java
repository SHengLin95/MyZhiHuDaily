package cc.hqu.sends.myzhihudaily.ui.fragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cc.hqu.sends.myzhihudaily.Constants;
import cc.hqu.sends.myzhihudaily.R;
import cc.hqu.sends.myzhihudaily.presenter.SimpleNewsViewPresenter;
import cc.hqu.sends.myzhihudaily.view.ISimpleNewsView;

public class SimpleNewsFragment extends BaseNewsFragment<ISimpleNewsView, SimpleNewsViewPresenter>
    implements ISimpleNewsView{
    private ImageView mImageView;
    private TextView  mTextView;
    @Override
    public SimpleNewsViewPresenter createPresenter() {
        return new SimpleNewsViewPresenter();
    }

    @Override
    protected View onCreateHeader(Context context) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.theme_list_header, null);
        mImageView = (ImageView) headerView.findViewById(R.id.theme_header_image);
        mTextView = (TextView) headerView.findViewById(R.id.theme_header_title);

        return headerView;
    }

    @Override
    protected String setURL() {
        return getArguments().getString(Constants.KEY.ZHIHU_DAILY_URL);
    }

    @Override
    public void updateHeader(String imageURL, String title) {
        mTextView.setText(title);
        Picasso.with(getContext()).load(imageURL).into(mImageView);
    }
}
