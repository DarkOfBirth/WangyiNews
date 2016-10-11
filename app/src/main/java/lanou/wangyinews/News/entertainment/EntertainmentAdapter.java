package lanou.wangyinews.News.entertainment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lanou.wangyinews.R;

/**
 * Created by dllo on 16/10/4.
 */
public class EntertainmentAdapter extends BaseAdapter {
    private Context context;
    ArrayList<EntertainmetBean> entertainmetList = new ArrayList<>();
    public void setEntertainmetList(ArrayList<EntertainmetBean> entertainmetList) {

        this.entertainmetList = entertainmetList;
        notifyDataSetChanged();
        Log.d("EntertainmentAdapter", "赋值");
    }

    public EntertainmentAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return entertainmetList == null ? 0 : entertainmetList.size();
    }

    @Override
    public Object getItem(int position) {
        return entertainmetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_entertainment,null);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        Log.d("EntertainmentAdapter", "进入Adapter");
        Picasso.with(context).load(entertainmetList.get(position).getImgurl()).resize(120,120).centerCrop().into(viewHolder.iv_item_entertainment);
        viewHolder.title_item_entertainment.setText(entertainmetList.get(position).getTitle());
        viewHolder.source_item_entertainment.setText(entertainmetList.get(position).getSource());


        return convertView;
    }

    private class MyViewHolder {

        private final ImageView iv_item_entertainment;
        private final TextView title_item_entertainment;
        private final TextView source_item_entertainment;

        public MyViewHolder(View convertView) {
            iv_item_entertainment = (ImageView) convertView.findViewById(R.id.iv_item_entertainment);
            title_item_entertainment = (TextView) convertView.findViewById(R.id.title_item_entertainment);
            source_item_entertainment = (TextView) convertView.findViewById(R.id.source_item_entertainment);

        }
    }
}
