package com.lzk.somusic.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_common_ui.base.BaseHandler;
import com.lzk.lib_common_ui.base.activity.BaseUIActivity;
import com.lzk.lib_common_ui.utils.SPUtil;
import com.lzk.somusic.R;
import com.lzk.somusic.app.Constants;

import java.util.ArrayList;

public class WelcomeActivity extends BaseUIActivity implements BaseHandler.IHandler {

    private BaseHandler mBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mBaseHandler = new BaseHandler(this,this);

        mBaseHandler.sendEmptyMessageDelayed(1,3000);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                startGuideActivity();
                break;
        }
    }

    private void startGuideActivity(){
        if (SPUtil.getInstance().getBoolean(Constants.IS_FIRST,true)){
            Intent intent = new Intent(this,GuideActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        AudioBean audioBean = new AudioBean();
        audioBean.setName("我和我的祖国");
        audioBean.setAuthor("中央乐团合唱团");
        audioBean.setPicUrl("http://img2.kuwo.cn/star/albumcover/300/79/42/1619637433.jpg");
        audioBean.setUrl("http://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_40820402&response=res&type=convert_url&");

        AudioBean audioBean1 = new AudioBean();
        audioBean1.setName("山楂树の恋");
        audioBean1.setAuthor("程jiajia");
        audioBean1.setPicUrl("http://img1.kuwo.cn/star/albumcover/300/81/43/1063260811.jpg");
        audioBean1.setUrl("hhttp://antiserver.kuwo.cn/anti.s?useless=/resource/&format=mp3&rid=MUSIC_73433206&response=res&type=convert_url&");
        ArrayList<AudioBean> audioBeans = new ArrayList<>();
        audioBeans.add(audioBean);
        audioBeans.add(audioBean1);
        AudioPlayerManager.startMusicService(audioBeans);
        AudioPlayerManager.setNotificationTargetActivity(MainActivity.class);
        finish();
    }
}
