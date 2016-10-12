package cc.hqu.sends.myzhihudaily.support;

public final class Constants {
    public final static int HEADER_PAGE_MULT = 1;
    public final static String ZHIHU_DAILY_BIRTHDAY = "20130520";
    public final static String ZHIHU_CONTENT_ID = "zhihu_content_id";
    public final static String ZHIHU_CONTENT_IS_INDEX = "zhihu_content_is_index";
    public Constants() {
    }

    public static final class URL {
        public static final String ZHIHU_DAILY_START = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
        public static final String ZHIHU_DAILY_NEWS_LASTEST = "http://news-at.zhihu.com/api/4/news/latest";
        public static final String ZHIHU_DAILY_NEWS_THEMES = "http://news-at.zhihu.com/api/4/themes";
        public static final String ZHIHU_DAILY_NEWS_THEME= "http://news-at.zhihu.com/api/4/theme/";
        public static final String ZHIHU_DAILY_NEWS_BEFORE = "http://news.at.zhihu.com/api/4/news/before/";
        public static final String ZHIHU_DAILY_NEWS_CONTENT = "http://news-at.zhihu.com/api/4/news/";
    }

    public static final class KEY {
        public static final String ZHIHU_DAILY_URL = "zhihu_key_url";
    }
}
