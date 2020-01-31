package com.lzk.somusic.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.lzk.lib_common_ui.base.BaseHandler;
import com.lzk.lib_common_ui.base.activity.BaseUIActivity;
import com.lzk.lib_common_ui.utils.SPUtil;
import com.lzk.somusic.R;
import com.lzk.somusic.app.Constants;

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
        finish();
    }
}
