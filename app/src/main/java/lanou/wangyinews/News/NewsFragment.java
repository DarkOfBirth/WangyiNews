package lanou.wangyinews.News;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lanou.wangyinews.News.entertainment.Entertainment_Tab_Fragment;
import lanou.wangyinews.News.headline.Headline_Tab_Fragment;
import lanou.wangyinews.News.Picture.Picture_Tab_Fragment;
import lanou.wangyinews.News.sport.Sport_Tab_Fragment;
import lanou.wangyinews.R;

/**
 * Created by dllo on 16/9/28.
 */
public class NewsFragment extends Fragment {

    private ViewPager vp;
    private TabLayout tb;
    private Adapter adapter;
    private ArrayList<Fragment> datas;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
        vp = (ViewPager) view.findViewById(R.id.vp_news);
        tb = (TabLayout) view.findViewById(R.id.tb_news);
        initData();
        Log.d("NewsFragment", "进入NewsFragment");
        adapter = new Adapter(getChildFragmentManager());
        adapter.setList(datas);
        vp.setAdapter(adapter);
        tb.setupWithViewPager(vp);




        return view;
    }

    private void initData() {
        Headline_Tab_Fragment head = new Headline_Tab_Fragment();
        Sport_Tab_Fragment sport = new Sport_Tab_Fragment();
        Entertainment_Tab_Fragment entertainment = new Entertainment_Tab_Fragment();
        Picture_Tab_Fragment picture = new Picture_Tab_Fragment();
        datas = new ArrayList<>();
        datas.add(head);
        datas.add(sport);
        datas.add(entertainment);
        datas.add(picture);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
