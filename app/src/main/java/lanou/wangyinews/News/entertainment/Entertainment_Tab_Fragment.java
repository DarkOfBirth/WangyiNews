package lanou.wangyinews.News.entertainment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import lanou.wangyinews.R;

/**
 * Created by dllo on 16/9/28.
 */
public class Entertainment_Tab_Fragment extends Fragment {
    private Context context;
    private ListView lv_entertainment;
    private EntertainmentAdapter entertainmentAdapter;

    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.entertainment_tab_frag,container,false);
        entertainmentAdapter = new EntertainmentAdapter(context);
        lv_entertainment = (ListView) view.findViewById(R.id.lv_entertainment);
        lv_entertainment.setAdapter(entertainmentAdapter);
        EntertainmentAsyncTask entertainmentAsyncTask = new EntertainmentAsyncTask();
        Log.d("Entertainment_Tab_Fragm", "进入娱乐");
        String url = "http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html";
        entertainmentAsyncTask.execute(url);
        return  view;
    }

    private class EntertainmentAsyncTask extends AsyncTask<String, Integer, ArrayList<EntertainmetBean>>{



        @Override
        protected ArrayList<EntertainmetBean> doInBackground(String... params) {
             String result = new String();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = null;
                    while ((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }

                   bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();
                    connection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("EntertainmentAsyncTask","reslut1" + result);
            return parse(result);
        }

        @Override
        protected void onPostExecute(ArrayList<EntertainmetBean> entertainmetBeen) {
            super.onPostExecute(entertainmetBeen);
            Log.d("EntertainmentAsyncTask", "entertainmetBeen:" + entertainmetBeen);
            entertainmentAdapter.setEntertainmetList(entertainmetBeen);
        }

        private ArrayList<EntertainmetBean> parse(String result) {
            ArrayList<EntertainmetBean> beans = new ArrayList<>();
            Log.d("EntertainmentAsyncTask","reslut2" + result);
            try {
                JSONObject jsonobject = new JSONObject(result);
                Log.d("EntertainmentAsyncTask", "已执行");
                Log.d("EntertainmentAsyncTask", "jsonobject:" + jsonobject);
                JSONArray jsonarray = (JSONArray) jsonobject.get("T1348648517839");
                for (int i = 0; i < jsonarray.length(); i++) {
                    Log.d("EntertainmentAsyncTask", "jsonarray.get(i):" + jsonarray.get(i));
                    EntertainmetBean bean = new EntertainmetBean();
                    JSONObject object = (JSONObject) jsonarray.get(i);
                    if(object.has("title")){
                        bean.setTitle(object.getString("title"));
                        Log.d("EntertainmentAsyncTask", bean.getTitle());
                    }
                    if(object.has("source")){
                        bean.setSource(object.getString("source"));
                    }
                    if(object.has("imgsrc")){
                        bean.setImgurl(object.getString("imgsrc"));
                    }
                    beans.add(bean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("EntertainmentAsyncTask", "beans:" + beans);
            Log.d("EntertainmentAsyncTask", "返回");
            return beans;

        }
    }
}
