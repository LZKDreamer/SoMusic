package com.lzk.lib_audio.audioplayer.model;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:音乐相关实体类
 */
public class AudioBean {

    /**
     * 歌曲名
     */
    private String name;
    /**
     * 歌手名
     */
    private String author;
    /**
     * 歌曲链接
     */
    private String url;
    /**
     * 封面链接
     */
    private String picUrl;
    /**
     * 歌曲时长
     */
    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
