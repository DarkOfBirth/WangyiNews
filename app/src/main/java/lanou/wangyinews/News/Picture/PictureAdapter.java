package lanou.wangyinews.News.Picture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lanou.wangyinews.R;

/**
 * Created by dllo on 16/10/9.
 */
public class PictureAdapter extends RecyclerView.Adapter {
    private ArrayList<PictureBean> pictureBeensList;
    private Context context;

    public void setPictureBeensList(ArrayList<PictureBean> pictureBeensList) {
        this.pictureBeensList = pictureBeensList;
    }

    public PictureAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {

            return 0;
        }
        if (position % 4 == 1) {

            return 1;
        }
        if (position % 4 == 2) {

            return 2;
        }
        if (position % 4 == 3) {

            return 3;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                View view = LayoutInflater.from(context).inflate(R.layout.item_pic_bigall, parent, false);
                BigAllViewHolder holder = new BigAllViewHolder(view);
                return holder;

            }

            case 1: {
                View view = LayoutInflater.from(context).inflate(R.layout.item_pic_bigleft, parent, false);
                BigLeftViewHolder holder = new BigLeftViewHolder(view);
            }

            case 2: {
                View view = LayoutInflater.from(context).inflate(R.layout.item_pic_bigall, parent, false);
                BigAllViewHolder holder = new BigAllViewHolder(view);
                return holder;
            }

            case 3: {

            }


        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case 0: {
                BigAllViewHolder bigholder = (BigAllViewHolder) holder;
                Picasso.with(context).load(pictureBeensList.get(position).getPic1()).into(bigholder.pic);
                bigholder.imgsum.setText(pictureBeensList.get(position).getImgsum() + "pics");
                bigholder.name.setText(pictureBeensList.get(position).getName());
                bigholder.reply.setText(pictureBeensList.get(position).getReply() + "跟帖");


            }

            case 1: {

            }

            case 2: {
                BigAllViewHolder bigholder = (BigAllViewHolder) holder;
                Picasso.with(context).load(pictureBeensList.get(position).getPic1()).into(bigholder.pic);
                bigholder.imgsum.setText(pictureBeensList.get(position).getImgsum() + "pics");
                bigholder.name.setText(pictureBeensList.get(position).getName());
                bigholder.reply.setText(pictureBeensList.get(position).getReply() + "跟帖");
            }

            case 3: {

            }

        }

    }

    @Override
    public int getItemCount() {
        return pictureBeensList == null ? 0 : pictureBeensList.size();
    }

    class BigAllViewHolder extends RecyclerView.ViewHolder {


        private final ImageView pic;
        private final TextView imgsum;
        private final TextView name;
        private final TextView reply;

        public BigAllViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.iv_item_pic_bigall);
            imgsum = (TextView) itemView.findViewById(R.id.tv_item_pic_bigall_imgsum);
            name = (TextView) itemView.findViewById(R.id.tv_item_pic_bigall_name);
            reply = (TextView) itemView.findViewById(R.id.tv_item_pic_bigall_reply);


        }
    }

    class BigLeftViewHolder extends RecyclerView.ViewHolder {

        public BigLeftViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BigRightViewHolder extends RecyclerView.ViewHolder {

        public BigRightViewHolder(View itemView) {
            super(itemView);
        }
    }


}
