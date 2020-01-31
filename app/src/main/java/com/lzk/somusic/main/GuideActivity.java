package com.lzk.somusic.main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lzk.lib_common_ui.base.activity.BaseUIActivity;
import com.lzk.lib_common_ui.utils.SPUtil;
import com.lzk.somusic.R;
import com.lzk.somusic.app.Constants;
import com.lzk.somusic.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页
 */
public class GuideActivity extends BaseUIActivity {

    @BindView(R.id.guide_vp)
    ViewPager guideVp;
    @BindView(R.id.guide_dot_one_iv)
    ImageView guideDotOneIv;
    @BindView(R.id.guide_dot_two_iv)
    ImageView guideDotTwoIv;
    @BindView(R.id.guide_dot_three_iv)
    ImageView guideDotThreeIv;
    @BindView(R.id.guide_music_iv)
    ImageView guideMusicIv;
    @BindView(R.id.guide_skip_tv)
    TextView guideSkipTv;
    @BindView(R.id.guide_enter_iv)
    ImageView mGuideEnterIv;

    private List<View> mViewList;
    private GuidePagerAdapter mPagerAdapter;
    private ObjectAnimator mAnimator;
    private boolean isRotation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
        initAnimation();
    }

    @OnClick({R.id.guide_music_iv, R.id.guide_skip_tv,R.id.guide_enter_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide_music_iv:
                if (isRotation) {
                    mAnimator.pause();
                } else {
                    mAnimator.start();
                }
                isRotation = !isRotation;
                break;
            case R.id.guide_skip_tv:
            case R.id.guide_enter_iv:
                startMainActivity();
                break;
        }
    }

    private void initViewPager() {
        mViewList = new ArrayList<>();
        View view1 = View.inflate(this, R.layout.layout_guide_one, null);
        View view2 = View.inflate(this, R.layout.layout_guide_two, null);
        View view3 = View.inflate(this, R.layout.layout_guide_three, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        mPagerAdapter = new GuidePagerAdapter(mViewList);
        guideVp.setOffscreenPageLimit(mViewList.size());
        guideVp.setAdapter(mPagerAdapter);

        ImageView imgOne = view1.findViewById(R.id.guide_one_iv);
        ImageView imgTwo = view2.findViewById(R.id.guide_two_iv);
        ImageView imgThree = view3.findViewById(R.id.guide_three_iv);

        AnimationDrawable drawableOne = (AnimationDrawable) imgOne.getBackground();
        drawableOne.start();

        AnimationDrawable drawableTwo = (AnimationDrawable) imgTwo.getBackground();
        drawableTwo.start();

        AnimationDrawable drawableThree = (AnimationDrawable) imgThree.getBackground();
        drawableThree.start();

        guideVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void selectDot(int position) {
        switch (position) {
            case 0:
                guideDotOneIv.setImageResource(R.drawable.ic_dot_select);
                guideDotTwoIv.setImageResource(R.drawable.ic_dot_nor);
                guideDotThreeIv.setImageResource(R.drawable.ic_dot_nor);
                break;
            case 1:
                guideDotOneIv.setImageResource(R.drawable.ic_dot_nor);
                guideDotTwoIv.setImageResource(R.drawable.ic_dot_select);
                guideDotThreeIv.setImageResource(R.drawable.ic_dot_nor);
                break;
            case 2:
                guideDotOneIv.setImageResource(R.drawable.ic_dot_nor);
                guideDotTwoIv.setImageResource(R.drawable.ic_dot_nor);
                guideDotThreeIv.setImageResource(R.drawable.ic_dot_select);
                break;
        }
        mGuideEnterIv.setVisibility(position == 2 ? View.VISIBLE:View.INVISIBLE);
    }

    private void initAnimation() {
        mAnimator = AnimationUtil.rotation(guideMusicIv);
        mAnimator.start();
        isRotation = true;
    }

    private void startMainActivity(){
        SPUtil.getInstance().putBoolean(Constants.IS_FIRST,false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
