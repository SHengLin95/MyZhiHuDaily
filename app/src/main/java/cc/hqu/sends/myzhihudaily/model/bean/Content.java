package cc.hqu.sends.myzhihudaily.model.bean;

/**
 * Created by shenglin on 16-3-30.
 */
public class Content {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private long id;
    private String[] css;

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public long getId() {
        return id;
    }

    public String[] getCss() {
        return css;
    }
}
