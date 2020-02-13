package com.lzk.somusic.me.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.core.AudioPlayer;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_audio.audioplayer.model.PlayBean;
import com.lzk.lib_common_ui.utils.CommonUtil;
import com.lzk.lib_image_loader.ImageLoadManager;
import com.lzk.somusic.R;
import com.lzk.somusic.view.AudioBeatView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/12
 * Function:播放列表adapter
 */
public class PlayListAdapter extends BaseQuickAdapter<PlayBean, BaseViewHolder> {

    public PlayListAdapter(int layoutResId, @Nullable List<PlayBean> data) {
        super(layoutResId, data);
        EventBusHelper.getInstance().register(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayBean item) {
        if (item.getAudioBean() != null){
            ImageView thumbIv = helper.getView(R.id.play_list_thumb_iv);
            ImageLoadManager.getInstance().loadRoundedRectImg(thumbIv,item.getAudioBean().getPicUrl(),
                    CommonUtil.dip2px(mContext,mContext.getResources().getDimension(R.dimen.img_rounded_corner)));

            helper.setText(R.id.play_list_name_tv,item.getAudioBean().getName());
            helper.setText(R.id.play_list_author_tv,item.getAudioBean().getAuthor());

            AudioBeatView audioBeatView = helper.getView(R.id.play_list_beat_view);
            audioBeatView.setAudioBean(item.getAudioBean());
            setCurBeatView(audioBeatView,item.getAudioBean());
        }

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBusHelper.getInstance().unregister(this);
    }

    //只能在Adapter中接收EventBus事件，在AudioBeatView中出现接收不到的情况
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(AudioLoadEvent event){
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPauseEvent(AudioPauseEvent event){
        notifyDataSetChanged();
    }

    //设置音乐跳动动画
    private void setCurBeatView(AudioBeatView audioBeatView , AudioBean itemBean){
        AudioBean audioBean = AudioPlayerManager.getAudioBean();
        if (audioBean != null && audioBean.equals(itemBean)){
            if (AudioPlayerManager.isPlaying()){
                audioBeatView.startBeat();
            }else if (AudioPlayerManager.isPause()){
                audioBeatView.stopBeat();
            }
        }else {
            audioBeatView.hide();
        }
    }
}
