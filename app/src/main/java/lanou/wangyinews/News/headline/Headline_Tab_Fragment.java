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

import lanou.wangyinews.News.database.DBTools;
import lanou.wangyinews.R;


public class Headline_Tab_Fragment extends Fragment {
    private
    Context context;
    private PullToRefreshListView lv_headline;
    private ArrayList<HeadLineBean> headLineBeens;
    private HeadLineAdapter headLineAdapter;
    private DBTools tools;
    private   int a = 1;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        tools = new DBTools(context);
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
        if(tools.isNetWorkAvailable(context)) {
            Toast.makeText(context, "网络畅通", Toast.LENGTH_SHORT).show();
          //  tools.clearHeadLineData();
        HeadLineAsyncTask headlineAsyncTask = new HeadLineAsyncTask();
        String url = "http://c.3g.163.com/nc/article/headline/T1348647909107/0-20.html";
        headlineAsyncTask.execute(url);
        }else{
            Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
           headLineAdapter.setBeanList(tools.queryDataFormHeadLine());
        }
        lv_headline.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            if(tools.isNetWorkAvailable(context)){
                tools.clearHeadLineData();
                Toast.makeText(context, "下拉刷新",Toast.LENGTH_SHORT).show();
                PullToRefresh headlineAsyncTask = new PullToRefresh();
                a=0;
                tools.clearHeadLineData();
                String url = "http://c.3g.163.com/nc/article/headline/T1348647909107/0-20.html";
                headlineAsyncTask.execute(url);
            } else {
                pullToRefreshBase.onRefreshComplete();
                Log.d("Headline_Tab_Fragment", "网络似乎断了,请检查网络状况~~");
            }

              //  pullToRefreshBase.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                Toast.makeText(context, "上拉加载更多",Toast.LENGTH_SHORT).show();
                if(tools.isNetWorkAvailable(context)){

              //  pullToRefreshBase.onRefreshComplete();
                PullToRefresh headlineAsyncTask = new PullToRefresh();
              String url =  "http://c.3g.163.com/nc/article/headline/T1348647909107/" + 20 * a +"" + "-" + (20 * a + 20)+ "" + ".html";
                a++;

                headlineAsyncTask.execute(url);

                }else {
                    pullToRefreshBase.onRefreshComplete();
                    Log.d("Headline_Tab_Fragment", "网络似乎断了,请检查网络状况~~");
                }
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
        Log.d("Headline_Tab_Fragment", "开始解析数据");
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

                if(i == 0){

                    if(object.has("ads")){
                        Log.d("Headline_Tab_Fragment", "轮播图");
                        bean.setType("0");
                        // 如果含有ads字段, 说明是轮播图
                        //ArrayList<HeadLineBean> imgbeanlist= new ArrayList<>();
                        JSONArray adsarray = object.getJSONArray("ads");
                        Log.d("Headline_Tab_Fragment", "adsarray.length():" + adsarray.length());
                        {
                            for (int j = 0; j <adsarray.length() ; j++) {
                                Log.d("Headline_Tab_Fragment", "次数"+j);
                                HeadLineBean imgbean = new HeadLineBean();
                                JSONObject adsobject = (JSONObject) adsarray.get(j);
                                if(adsobject.has("title")){
                                    imgbean.setTitle(adsobject.getString("title"));
                                    Log.d("Headline_Tab_Fragment", imgbean.getTitle());
                                }
                                if(adsobject.has("imgsrc")){
                                    imgbean.setImgurl(adsobject.getString("imgsrc"));
                                }
                                imgbean.setPostid(bean.getPostid());
                                tools.insertImageToGallery(imgbean);
                            }
                        }
                    }
                }else{
                    bean.setType("1");
                }




                tools.insertDataToHeadLine(bean);
                beans.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("HeadLineAsyncTask", "beans:" + beans);
        return tools.queryDataFormHeadLine();
    }
}
