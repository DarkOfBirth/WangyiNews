package lanou.wangyinews.News.headline;

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
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
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


public class Headline_Tab_Fragment extends Fragment {
    private
    Context context;
    private PullToRefreshListView lv_headline;
    private ArrayList<HeadLineBean> headLineBeens;
    private HeadLineAdapter headLineAdapter;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.headline_tab_frag, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_headline = (PullToRefreshListView) view.findViewById(R.id.lv_headline);
        lv_headline.setMode(PullToRefreshBase.Mode.BOTH);

        headLineAdapter = new HeadLineAdapter(context);
        headLineBeens = new ArrayList<>();
        lv_headline.setAdapter(headLineAdapter);
        HeadLineAsyncTask headlineAsyncTask = new HeadLineAsyncTask();
        String url = "http://c.3g.163.com/nc/article/headline/T1348647909107/0-20.html";
        headlineAsyncTask.execute(url);
        lv_headline.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

                Toast.makeText(context, "下拉刷新",Toast.LENGTH_SHORT).show();
                PullToRefresh headlineAsyncTask = new PullToRefresh();
                String url = "http://c.3g.163.com/nc/article/headline/T1348647909107/0-20.html";
                headlineAsyncTask.execute(url);
              //  pullToRefreshBase.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                Toast.makeText(context, "上拉加载更多",Toast.LENGTH_SHORT).show();
              //  pullToRefreshBase.onRefreshComplete();
                PullToRefresh headlineAsyncTask = new PullToRefresh();
                String url = "http://c.3g.163.com/nc/article/headline/T1348647909107/0-40.html";
                headlineAsyncTask.execute(url);
            }
        });

    }


    /**
     * 初次加载
     * 内部类HeadLineAsyncTask
     */
    private class HeadLineAsyncTask extends AsyncTask<String, Integer, ArrayList<HeadLineBean>> {

        @Override
        protected ArrayList<HeadLineBean> doInBackground(String... params) {

            String result = getDataFormInternet(params[0]);

            return parse(result);

        }


        @Override
        protected void onPostExecute(ArrayList<HeadLineBean> headLineBeen) {
            super.onPostExecute(headLineBeen);

            headLineAdapter.setBeanList(headLineBeen);

        }
    }

    /**
     * 下拉刷新异步类任务
     */
    class PullToRefresh extends AsyncTask<String, Integer, ArrayList<HeadLineBean>> {

        @Override
        protected ArrayList<HeadLineBean> doInBackground(String... params) {


            String result = getDataFormInternet(params[0]);
            return parse(result);
        }


        @Override
        protected void onPostExecute(ArrayList<HeadLineBean> headLineBeen) {
            super.onPostExecute(headLineBeen);

            headLineAdapter.setBeanList(headLineBeen);
            lv_headline.onRefreshComplete();
        }
    }

    /**
     *  从网络获取数据
     * @param strurl 网址
     * @return 获取到的json数据
     */
    private String getDataFormInternet(String strurl) {
        String result = null;
        try {
            URL url = new URL(strurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d("HeadLineAsyncTask", "connection.getResponseCode():" + connection.getResponseCode());
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                result = new String();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }

                reader.close();
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解析数据
     *
     * @param result json数据
     * @return  解析后数据的集合集合
     */
    private ArrayList<HeadLineBean> parse(String result) {
        ArrayList<HeadLineBean> beans = new ArrayList<>();
        try {
            JSONObject jsonobject = new JSONObject(result);
            JSONArray jsonarray = (JSONArray) jsonobject.get("T1348647909107");
            for (int i = 0; i < jsonarray.length(); i++) {
                HeadLineBean bean = new HeadLineBean();
                JSONObject object = (JSONObject) jsonarray.get(i);
                if (object.has("title")) {
                    bean.setTitle(object.getString("title"));
                }
                if (object.has("imgsrc")) {
                    bean.setImgurl(object.getString("imgsrc"));
                }
                if(object.has("postid")) {
                    bean.setPostid(object.getString("postid"));
                }

                beans.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("HeadLineAsyncTask", "beans:" + beans);
        return beans;
    }
}
