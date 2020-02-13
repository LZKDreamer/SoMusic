package com.lzk.lib_audio.audioplayer.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.core.AudioController;
import com.lzk.lib_audio.audioplayer.db.AudioGreenDaoHelper;
import com.lzk.lib_audio.audioplayer.event.AudioCollectMusicEvent;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioProgressEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_audio.audioplayer.view.JukeboxView;
import com.lzk.lib_audio.utils.Util;
import com.lzk.lib_common_ui.base.activity.BaseUIActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicPlayerActivity extends BaseUIActivity {

    private AudioBean mAudioBean;

    private ImageView mBackIv;
    private JukeboxView mJukeboxView;
    private TextView mSongNameTv;
    private TextView mSongAuthorTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private SeekBar mSeekBar;
    private ImageView mPreviousIv;
    private ImageView mPlayIv;
    private ImageView mNextIv;
    private ImageView mPlayModeIv;
    private ImageView mFavouriteIv;
    private ImageView mShareIv;
    private ObjectAnimator mAnimator;

    public static void start(Activity activity){
        Intent intent = new Intent(activity,MusicPlayerActivity.class);
        ActivityCompat.startActivity(activity,intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.getInstance().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(
                    TransitionInflater.from(this).inflateTransition(R.transition.bottomtop));
        }
        setContentView(R.layout.activity_music_player);
        EventBusHelper.getInstance().register(this);
        initData();
        initView();
        initPlayMode();
        initEvent();
    }

    private void initView(){
        mBackIv = findViewById(R.id.music_player_back_iv);
        mJukeboxView = findViewById(R.id.music_player_jukebox_view);
        mSongNameTv = findViewById(R.id.music_player_song_name_tv);
        mSongAuthorTv = findViewById(R.id.music_player_song_author_tv);
        mStartTimeTv = findViewById(R.id.music_player_start_time_tv);
        mEndTimeTv = findViewById(R.id.music_player_end_time_tv);
        mSeekBar = findViewById(R.id.music_player_seek_bar);
        mPreviousIv = findViewById(R.id.music_player_previous_iv);
        mPlayIv = findViewById(R.id.music_player_play_iv);
        mNextIv = findViewById(R.id.music_player_next_iv);
        mPlayModeIv = findViewById(R.id.music_player_play_mode_iv);
        mFavouriteIv = findViewById(R.id.music_player_favourite_iv);
        mShareIv = findViewById(R.id.music_player_share_iv);

        if (mAudioBean != null){
            mSongNameTv.setText(mAudioBean.getName());
            mSongAuthorTv.setText(mAudioBean.getAuthor());
            changeFavouriteStatus(false);
        }

    }

    private void initData(){
        mAudioBean = AudioController.getInstance().getAudio();
    }

    private void initEvent(){
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mPlayIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().playOrPause();
            }
        });
        mPreviousIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().previous();
            }
        });
        mNextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().next();
            }
        });
        mPlayModeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayMode();
            }
        });
        mFavouriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioController.getInstance().collectMusic();
            }
        });
        mShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AudioController.getInstance().seekTo(seekBar.getProgress());
            }
        });
    }

    private void initPlayMode(){
        AudioController.PlayMode mode = AudioController.getInstance().getPlayMode();
        switch (mode){
            case LOOP:
                mPlayModeIv.setImageResource(R.drawable.ic_loop);
                break;
            case RANDOM:
                mPlayModeIv.setImageResource(R.drawable.ic_random);
                break;
            case REPEAT:
                mPlayModeIv.setImageResource(R.drawable.ic_single_tone);
                break;
        }
    }

    private void showPlayOrPauseView(){
        if (AudioController.getInstance().isPlaying()){
            mPlayIv.setImageResource(R.drawable.ic_pause_big);
        }else {
            mPlayIv.setImageResource(R.drawable.ic_play_big);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadEvent(AudioLoadEvent event){
        showLoadView(event.mAudioBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartEvent(AudioStartEvent event){
        showPlayOrPauseView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPauseEvent(AudioPauseEvent event){
        showPlayOrPauseView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(AudioProgressEvent event){
        int progress = event.progress;
        int totalDuration = event.totalDuration;
        mStartTimeTv.setText(Util.timeParse(progress));
        mEndTimeTv.setText(Util.timeParse(totalDuration));
        mSeekBar.setMax(totalDuration);
        mSeekBar.setProgress(progress);
        showPlayOrPauseView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectMusicEvent(AudioCollectMusicEvent event){
        changeFavouriteStatus(true);
    }

    private void showLoadView(AudioBean  bean){
        mAudioBean = bean;
        mSongAuthorTv.setText(mAudioBean.getAuthor());
        mSongNameTv.setText(mAudioBean.getName());
        mPlayIv.setImageResource(R.drawable.ic_play_big);
        changeFavouriteStatus(false);
    }

    private void setPlayMode(){
        AudioController.PlayMode playMode = AudioController.getInstance().getPlayMode();
        switch (playMode){
            case LOOP:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.RANDOM);
                mPlayModeIv.setImageResource(R.drawable.ic_random);
                break;
            case RANDOM:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.REPEAT);
                mPlayModeIv.setImageResource(R.drawable.ic_single_tone);
                break;
            case REPEAT:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.LOOP);
                mPlayModeIv.setImageResource(R.drawable.ic_loop);
                break;

        }
    }

    /**
     * 改变收藏的状态
     */
    private void changeFavouriteStatus(boolean anim){
        if (AudioGreenDaoHelper.getInstance().queryFavouriteMusic(mAudioBean) == null){
            mFavouriteIv.setImageResource(R.drawable.ic_favorite_nor);
        }else {
            mFavouriteIv.setImageResource(R.drawable.ic_favorite_select);
        }
        if (anim){
            if (mAnimator != null) mAnimator.end();
            PropertyValuesHolder animX =
                    PropertyValuesHolder.ofFloat(View.SCALE_X.getName(), 1.0f, 1.2f, 1.0f);
            PropertyValuesHolder animY =
                    PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(), 1.0f, 1.2f, 1.0f);
            mAnimator = ObjectAnimator.ofPropertyValuesHolder(mFavouriteIv, animX, animY);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.setDuration(300);
            mAnimator.start();
        }
    }

}
