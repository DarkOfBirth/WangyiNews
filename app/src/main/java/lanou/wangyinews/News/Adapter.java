package lanou.wangyinews.News;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/9/28.
 */
public class Adapter extends FragmentPagerAdapter {
    private String[] title = {"头条","体育","娱乐","图片"};
    public ArrayList<Fragment> list;

    public void setList(ArrayList<Fragment> list) {
        this.list = list;
    }

    public Adapter(FragmentManager fm) {
        super(fm);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
