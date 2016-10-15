package lanou.wangyinews.News.headline;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/14.
 */
public class ViewPagerAdapter extends PagerAdapter {
    Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    ArrayList<View> list = null;

    public void setList(ArrayList<View> list) {
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


       container.addView(list.get(position));
        return list.get(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
