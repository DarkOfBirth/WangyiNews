package lanou.wangyinews.News.Picture;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/10/9.
 */
public class PictureAdapter extends RecyclerView.Adapter{
    @Override
    public int getItemViewType(int position) {
       return  position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
