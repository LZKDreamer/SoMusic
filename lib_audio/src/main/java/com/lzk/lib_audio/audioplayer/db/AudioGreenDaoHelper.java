package com.lzk.lib_audio.audioplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_audio.audioplayer.model.FavouriteBean;
import com.lzk.lib_audio.audioplayer.model.PlayBean;
import com.lzk.lib_audio.utils.Util;

import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/8
 * Function:数据库帮助类
 */
public class AudioGreenDaoHelper {
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;

    private static AudioGreenDaoHelper sAudioGreenDaoHelper;

    public static AudioGreenDaoHelper getInstance(){
        if (sAudioGreenDaoHelper == null){
            synchronized (AudioGreenDaoHelper.class){
                if (sAudioGreenDaoHelper == null){
                    sAudioGreenDaoHelper = new AudioGreenDaoHelper(AudioPlayerManager.getContext());
                }
            }
        }
        return sAudioGreenDaoHelper;
    }

    private AudioGreenDaoHelper(Context context){
        mContext = context;
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context,"favourite.db");
        mDatabase = mDevOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    /**添加收藏的歌曲
     * @param audioBean
     */
    public void addFavouriteMusic(AudioBean audioBean){
        FavouriteBeanDao favouriteBeanDao = mDaoSession.getFavouriteBeanDao();
        FavouriteBean favouriteBean = new FavouriteBean();
        favouriteBean.setAudioId(audioBean.getUrl());
        favouriteBean.setAudioBean(audioBean);
        favouriteBeanDao.insertOrReplace(favouriteBean);
    }

    /**
     * 移除收藏的歌曲
     * @param audioBean
     */
    public void removeFavouriteMusic(AudioBean audioBean){
        FavouriteBeanDao favouriteBeanDao = mDaoSession.getFavouriteBeanDao();
        FavouriteBean favouriteBean = favouriteBeanDao.queryBuilder()
                .where(FavouriteBeanDao.Properties.AudioId.eq(audioBean.getUrl())).unique();
        favouriteBeanDao.delete(favouriteBean);
    }

    /**
     * 查询收藏的歌曲
     * @param audioBean
     */
    public FavouriteBean queryFavouriteMusic(AudioBean audioBean){
        FavouriteBeanDao favouriteBeanDao = mDaoSession.getFavouriteBeanDao();
        FavouriteBean favouriteBean = favouriteBeanDao.queryBuilder()
                .where(FavouriteBeanDao.Properties.AudioId.eq(audioBean.getUrl())).unique();
        return favouriteBean;
    }

    /**
     * 查询所有收藏的歌曲
     * @return
     */
    public List<FavouriteBean> queryAllFavouriteMusic(){
        FavouriteBeanDao favouriteBeanDao = mDaoSession.getFavouriteBeanDao();
        return favouriteBeanDao.loadAll();
    }

    /**
     * 添加播放记录
     * @param audioBean
     */
    public void addPlayBackRecord(AudioBean audioBean){
        List<PlayBean> list = getPlaybackRecord();
        boolean exist= false;
        PlayBean newBean = null;
        for (PlayBean bean : list){
            if (bean.getAudioBean() != null){
                if (bean.getAudioBean().getUrl()
                .equals(audioBean.getUrl())){
                    exist = true;
                    newBean = bean;
                    break;
                }
            }
        }
        PlayBeanDao playBeanDao = mDaoSession.getPlayBeanDao();
        if (exist){
            newBean.setTimeMill(Util.timeMill());
            playBeanDao.update(newBean);
        }else {
            PlayBean playBean = new PlayBean();
            playBean.setAudioBean(audioBean);
            playBean.setAudioId(audioBean.getUrl());
            playBean.setTimeMill(Util.timeMill());
            playBeanDao.insertOrReplace(playBean);
        }
    }

    /**
     * 获取播放记录
     * @return
     */
    public List<PlayBean> getPlaybackRecord(){
        PlayBeanDao playBeanDao = mDaoSession.getPlayBeanDao();
        List<PlayBean> list = (List<PlayBean>) playBeanDao.queryBuilder().orderDesc(PlayBeanDao.Properties.TimeMill).build().list();
        Log.d("shiZi","size："+list.size());
        for (PlayBean playBean : list){
            if (playBean.getAudioBean() != null ){
                Log.d("shiZi","song:"+playBean.getAudioBean().getName());
            }else {
                Log.d("shiZi","null");
            }
        }
        return list;
    }

    /**
     * 删除播放记录
     * @param audioBean
     */
    public void removePlaybackRecord(AudioBean audioBean){
        PlayBeanDao playBeanDao = mDaoSession.getPlayBeanDao();
        PlayBean playBean = playBeanDao.queryBuilder().where(PlayBeanDao.Properties.AudioId.eq(audioBean.getUrl())).unique();
        playBeanDao.delete(playBean);
    }
}
