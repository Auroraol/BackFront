package com.lvr.threerecom.ui.home;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.lvr.threerecom.R;
import com.lvr.threerecom.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lvr on 2017/5/17.
 */

public class OpenSourceActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    WebView mWebView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_open_source;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mToolbar.setTitle("开源许可");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.loadUrl("file:///android_asset/license.html");
    }



}
