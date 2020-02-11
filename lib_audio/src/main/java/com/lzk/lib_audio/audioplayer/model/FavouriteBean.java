package com.lzk.lib_audio.audioplayer.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.lzk.lib_audio.audioplayer.db.DaoSession;
import com.lzk.lib_audio.audioplayer.db.AudioBeanDao;
import com.lzk.lib_audio.audioplayer.db.FavouriteBeanDao;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/8
 * Function:收藏实体类
 */
@Entity
public class FavouriteBean {

    @Id(autoincrement = true)
    private Long id;

    private String audioId;
    @ToOne(joinProperty = "audioId")
    private AudioBean audioBean;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1051331019)
    private transient FavouriteBeanDao myDao;
    @Generated(hash = 1639475816)
    public FavouriteBean(Long id, String audioId) {
        this.id = id;
        this.audioId = audioId;
    }
    @Generated(hash = 579040056)
    public FavouriteBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAudioId() {
        return this.audioId;
    }
    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }
    @Generated(hash = 249106606)
    private transient String audioBean__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 281167394)
    public AudioBean getAudioBean() {
        String __key = this.audioId;
        if (audioBean__resolvedKey == null || audioBean__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AudioBeanDao targetDao = daoSession.getAudioBeanDao();
            AudioBean audioBeanNew = targetDao.load(__key);
            synchronized (this) {
                audioBean = audioBeanNew;
                audioBean__resolvedKey = __key;
            }
        }
        return audioBean;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 391748149)
    public void setAudioBean(AudioBean audioBean) {
        synchronized (this) {
            this.audioBean = audioBean;
            audioId = audioBean == null ? null : audioBean.getUrl();
            audioBean__resolvedKey = audioId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1254847589)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFavouriteBeanDao() : null;
    }


}
