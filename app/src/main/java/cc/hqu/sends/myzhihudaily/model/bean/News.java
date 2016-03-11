package cc.hqu.sends.myzhihudaily.model.bean;

import java.util.List;

/**
 * Created by SHeng_Lin on 2016/3/11.
 */
public class News {
    private String date;
    private List<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
