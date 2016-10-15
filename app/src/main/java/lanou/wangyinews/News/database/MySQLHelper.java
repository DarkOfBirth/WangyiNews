package lanou.wangyinews.News.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ggs on 16/9/26.
 */
public class MySQLHelper extends SQLiteOpenHelper {
    public MySQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBValues.HEADLINE_TABLE+"(" +
                "id integer primary key autoincrement," +
                " title text, imgurl text, postid text,type text) ");
        db.execSQL("create table "+DBValues.GALLERY_TABLE+"(id integer primary " +
                "key autoincrement, title text, imgurl text, postid text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
