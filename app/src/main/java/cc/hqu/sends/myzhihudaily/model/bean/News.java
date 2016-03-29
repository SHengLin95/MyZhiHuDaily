package cc.hqu.sends.myzhihudaily.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHeng_Lin on 2016/3/11.
 */
public class News {
    private String date;
    private List<Story> stories = new ArrayList<>();
    private List<Story> top_stories = new ArrayList<>();
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public List<Story> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<Story> top_stories) {
        this.top_stories = top_stories;
    }
}
