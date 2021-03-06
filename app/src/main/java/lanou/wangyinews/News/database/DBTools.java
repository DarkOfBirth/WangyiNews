package lanou.wangyinews.News.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

import lanou.wangyinews.News.headline.HeadLineBean;

/**
 * Created by ggs on 16/9/26.
 */
public class DBTools {

    private SQLiteDatabase database;

    public DBTools(Context context) {

        MySQLHelper helper = new MySQLHelper(context, DBValues.DB_NAME, null, 1);
        database = helper.getWritableDatabase();
    }

    /**
     * 将头条的数据插入数据库, 同时进行了去重复
     *
     * @param headLineBean
     */
    public void insertDataToHeadLine(HeadLineBean headLineBean) {
        Cursor cursor = database.query(DBValues.HEADLINE_TABLE, null, "postid = ?",
                new String[]{headLineBean.getPostid()}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("title", headLineBean.getTitle());
        values.put("imgurl", headLineBean.getImgurl());
        values.put("postid", headLineBean.getPostid());
        values.put("type",headLineBean.getType());
        Long lines = database.insert(DBValues.HEADLINE_TABLE, null, values);

        Log.d("DBTools", "insertlines:" + lines);
    }

    public boolean isNetWorkAvailable (Context context) {

        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;



    }

    /**
     * 从头条数据库查询
     * @return
     */
    public ArrayList<HeadLineBean> queryDataFormHeadLine() {

        ArrayList<HeadLineBean> beansArrayList = new ArrayList();
        Cursor cursor = database.query(DBValues.HEADLINE_TABLE, null, null, null, null, null, null);
        Log.d("DBTools", "cursor.getCount():" + cursor.getCount());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));
                String postid = cursor.getString(cursor.getColumnIndex("postid"));
                String type = cursor.getString(cursor.getColumnIndex("type"));


                HeadLineBean bean = new HeadLineBean(imgurl, title, postid,type);
                beansArrayList.add(bean);

            }
        }

        return beansArrayList;
    }

    public void clearHeadLineData() {
        database.delete(DBValues.HEADLINE_TABLE, null, null);
        database.delete(DBValues.GALLERY_TABLE, null, null);
    }

    /**
     * 轮播图的插入
     * @param imgbean
     */
    public void insertImageToGallery(HeadLineBean imgbean) {
        Cursor cursor = database.query(DBValues.GALLERY_TABLE, null, "postid = ? and imgurl = ?",
                new String[]{imgbean.getPostid(),imgbean.getImgurl()}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("title",  imgbean.getTitle());
        values.put("imgurl", imgbean.getImgurl());
        values.put("postid", imgbean.getPostid());
        database.insert(DBValues.GALLERY_TABLE,null,values);
    }

    /**
     * 从轮播图图库中查找所需要的轮播图
     *
     * @param postid 唯一的id
     * @return
     */
    public ArrayList<HeadLineBean> queryImageFromGallery(String postid) {
        ArrayList<HeadLineBean> imgbeans = new ArrayList<>();
        Cursor cursor = database.query(DBValues.GALLERY_TABLE,null,"postid = ?",
                new String[]{postid},null,null,null);
        if(cursor != null) {
            while (cursor.moveToNext()){
                 HeadLineBean bean = new HeadLineBean();
                bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                bean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                imgbeans.add(bean);

            }

        }
        cursor.close();
        return imgbeans;

    }
}
