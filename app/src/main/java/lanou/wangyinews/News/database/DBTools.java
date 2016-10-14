package lanou.wangyinews.News.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;

import lanou.wangyinews.News.headline.HeadLineBean;

/**
 * Created by ggs on 16/9/26.
 */
public class DBTools {

    private SQLiteDatabase database;

    public DBTools(Context context) {

        MySQLHelper helper = new MySQLHelper(context, DBValues.DB_NAME , null , 1);
        database = helper.getWritableDatabase();
    }
    public void insertDataToHeadLine(HeadLineBean headLineBean) {

        ContentValues values = new ContentValues();
        values.put("title",headLineBean.getTitle());
        values.put("imgurl",headLineBean.getImgurl());
        values.put("postid",headLineBean.getPostid());
      Long lines =  database.insert(DBValues.HEADLINE_TABLE,null,values);

        Log.d("DBTools", "insertlines:" + lines);
    }


}
