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

    /**
     * 以下是Theme独有的
     */
    private String image;
    private String name;
    private String description;


    public String getDate() {
        return date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public List<Story> getTop_stories() {
        return top_stories;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
