package lanou.wangyinews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import lanou.wangyinews.News.NewsFragment;


public class MainActivity extends FragmentActivity implements  View.OnClickListener {

    private RadioButton rb_News;
    private RadioButton rb_Live;
    private RadioButton rb_Topic;
    private RadioButton rb_Mine;
    private FrameLayout content_framelayout;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setClick();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_framelayout, new NewsFragment());
        transaction.commit();

    }

    private void setClick() {
        rb_News.setOnClickListener(this);
        rb_Live.setOnClickListener(this);
        rb_Mine.setOnClickListener(this);
        rb_Topic.setOnClickListener(this);
    }


    private void initView() {
        content_framelayout = (FrameLayout) findViewById(R.id.content_framelayout);
        rb_News = (RadioButton) findViewById(R.id.rb_News);
        rb_Live = (RadioButton) findViewById(R.id.rb_Live);
        rb_Topic = (RadioButton) findViewById(R.id.rb_Topic);
        rb_Mine = (RadioButton) findViewById(R.id.rb_Mine);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_News:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.content_framelayout, new NewsFragment());
                transaction.commit();
                Log.d("MainActivity", "新闻");
                break;
            case R.id.rb_Live:
                break;
            case R.id.rb_Topic:
                break;
            case R.id.rb_Mine:
                break;
        }
    }
}
