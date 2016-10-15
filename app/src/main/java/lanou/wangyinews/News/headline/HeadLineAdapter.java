package lanou.wangyinews.News.headline;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lanou.wangyinews.News.database.DBTools;
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
    public int getItemViewType(int position) {
        // 0 : 轮播图 1: 不是轮播图 是正常的
        Log.d("HeadLineAdapter", "类型"+beanList.get(position).getType());
        return position == 0 ? 0 : 1;


    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(getItemViewType(position) == 0){
            MyImageViewHolder imgholder= null;
            // 是轮播图
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.viewpager,null);
                    imgholder = new MyImageViewHolder(convertView);
                convertView.setTag(imgholder);

            }else {
                imgholder = (MyImageViewHolder) convertView.getTag();
            }
            DBTools tools = new DBTools(context);
            ViewPagerAdapter adapter = new ViewPagerAdapter(context);
            ArrayList<View> viewlist = new ArrayList<>();
            // 查询轮播图数据
            ArrayList<HeadLineBean> imgbeans = tools.queryImageFromGallery(beanList.get(position).getPostid());
            for (int i = 0; i <imgbeans.size() ; i++) {
                Log.d("HeadLineAdapter", "装载轮播图" + i);
                View view = LayoutInflater.from(context).inflate(R.layout.item_head_content_viewpager,null);
                ImageView iv_headline_item_viewpager = (ImageView) view.findViewById(R.id.iv_headline_item_viewpager);


                Picasso.with(context).load(imgbeans.get(i).getImgurl()).into(iv_headline_item_viewpager);
               TextView tv_headline_item_viewpager = (TextView) view.findViewById(R.id.tv_headline_item_viewpager);
                tv_headline_item_viewpager.setText(imgbeans.get(i).getTitle());
                viewlist.add(view);
            }
            adapter.setList(viewlist);
            imgholder.viewPager.setAdapter(adapter);

        }
        else {
            // 不是轮播图
            MyViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_headline, null);
                viewHolder = new MyViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else{
                    viewHolder = (MyViewHolder) convertView.getTag();
                }

                viewHolder.tv_item_headline.setText(beanList.get(position).getTitle());
                // viewHolder.iv_item_headline.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
                Log.d("HeadLineAdapter", beanList.get(position).getTitle());
                Log.d("HeadLineAdapter", beanList.get(position).getImgurl());
                Picasso.with(context).load(beanList.get(position).getImgurl())
                        .resize(130, 100).error(R.mipmap.ic_launcher).into(viewHolder.iv_item_headline);
            }




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

    /**
     * 图片的viewholder
     */
    private class MyImageViewHolder {
        private final ViewPager viewPager;

        public MyImageViewHolder(View convertView) {
            viewPager = (ViewPager) convertView.findViewById(R.id.headviewpager);
        }
    }
}
