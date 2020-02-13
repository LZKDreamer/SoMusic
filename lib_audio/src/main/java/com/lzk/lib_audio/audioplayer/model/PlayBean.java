package com.lzk.lib_audio.audioplayer.model;

import android.util.Log;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.lzk.lib_audio.audioplayer.db.DaoSession;
import com.lzk.lib_audio.audioplayer.db.AudioBeanDao;
import com.lzk.lib_audio.audioplayer.db.PlayBeanDao;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/12
 * Function:播放记录
 */
@Entity
public class PlayBean {

    @Id(autoincrement = true)
    private Long id;

    //播放的时间毫秒值
    private long timeMill;

    private String audioId;
    @ToOne(joinProperty = "audioId")
    private AudioBean audioBean;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 116448589)
    private transient PlayBeanDao myDao;
    @Generated(hash = 865457118)
    public PlayBean(Long id, long timeMill, String audioId) {
        this.id = id;
        this.timeMill = timeMill;
        this.audioId = audioId;
    }
    @Generated(hash = 1251142461)
    public PlayBean() {
    }
    public long getTimeMill() {
        return this.timeMill;
    }
    public void setTimeMill(long timeMill) {
        this.timeMill = timeMill;
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
    @Generated(hash = 24530227)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayBeanDao() : null;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof PlayBean)){
            return false;
        }
        return (this.id.equals(((PlayBean) obj).id));
    }

}
