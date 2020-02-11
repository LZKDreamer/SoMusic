package com.lzk.lib_audio.audioplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_audio.audioplayer.model.FavouriteBean;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/8
 * Function:数据库帮助类
 */
public class GreenDaoHelper {
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;

    private static GreenDaoHelper sGreenDaoHelper;

    public static GreenDaoHelper getInstance(){
        if (sGreenDaoHelper == null){
            synchronized (GreenDaoHelper.class){
                if (sGreenDaoHelper == null){
                    sGreenDaoHelper = new GreenDaoHelper(AudioPlayerManager.getContext());
                }
            }
        }
        return sGreenDaoHelper;
    }

    private GreenDaoHelper(Context context){
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
}
