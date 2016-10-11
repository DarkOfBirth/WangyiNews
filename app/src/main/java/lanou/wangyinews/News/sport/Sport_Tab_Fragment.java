package lanou.wangyinews.News.sport;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lanou.wangyinews.R;

/**
 * Created by dllo on 16/9/28.
 */
public class Sport_Tab_Fragment extends Fragment {
    private Context context;
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.sport_tab_frag,null);
        return  view;
    }
}
