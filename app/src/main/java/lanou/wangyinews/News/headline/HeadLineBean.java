package lanou.wangyinews.News.headline;

/**
 * Created by dllo on 16/9/29.
 */


public class HeadLineBean {



    String imgurl;
    String title;
    // 新闻的唯一值
    String postid;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HeadLineBean(){
        super();
    }
    public HeadLineBean(String imgurl, String title, String postid,String type) {
        this.imgurl = imgurl;
        this.title = title;
        this.postid = postid;
        this.type = type;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
