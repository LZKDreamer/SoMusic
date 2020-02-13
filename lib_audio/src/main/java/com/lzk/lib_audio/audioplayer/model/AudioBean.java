package com.lzk.lib_audio.audioplayer.model;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:音乐相关实体类
 */
@Entity
public class AudioBean implements Serializable {

    private static final long serialVersionUID = -2770995586612698282L;


    private String id;

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
    @Id
    @Unique
    private String url;
    /**
     * 封面链接
     */
    private String picUrl;
    /**
     * 歌曲时长
     */
    private String duration;

    @Generated(hash = 1561486656)
    public AudioBean(String id, String name, String author, String url, String picUrl,
            String duration) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.url = url;
        this.picUrl = picUrl;
        this.duration = duration;
    }

    @Generated(hash = 1628963493)
    public AudioBean() {
    }

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AudioBean)){
            return false;
        }
        return (this.url.equals(((AudioBean) obj).url));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
