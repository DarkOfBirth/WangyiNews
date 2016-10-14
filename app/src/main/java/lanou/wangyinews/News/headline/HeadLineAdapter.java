package lanou.wangyinews.News.headline;

import android.content.Context;
import android.graphics.BitmapFactory;
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
 * Created by dllo on 16/9/29.
 */
public class HeadLineAdapter extends BaseAdapter {
    private Context context;

    public HeadLineAdapter(Context context) {
        this.context = context;
    }

    ArrayList<HeadLineBean> beanList = new ArrayList<>();

    public void setBeanList(ArrayList<HeadLineBean> beanList) {
        this.beanList = beanList;
//        Log.d("HeadLineAdapter", beanList.get(0).getTitle());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_headline,null);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        viewHolder.tv_item_headline.setText(beanList.get(position).getTitle());
        viewHolder.iv_item_headline.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
                Picasso.with(context).load(beanList.get(position).getImgurl()).into(viewHolder.iv_item_headline);


        return convertView;
    }

    private class MyViewHolder {

        private final TextView tv_item_headline;
        private final ImageView iv_item_headline;

        public MyViewHolder(View convertView) {
            tv_item_headline = (TextView) convertView.findViewById(R.id.tv_item_headline);
            iv_item_headline = (ImageView)convertView.findViewById(R.id.iv_item_headline);

        }
    }
}
