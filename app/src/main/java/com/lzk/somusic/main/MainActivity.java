package com.lzk.somusic.main;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lzk.lib_common_ui.base.activity.BaseUIActivity;
import com.lzk.somusic.R;
import com.lzk.somusic.explore.fragment.ExploreFragment;
import com.lzk.somusic.me.fragment.MeFragment;
import com.lzk.somusic.recommend.fragment.RecommendFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseUIActivity {

    @BindView(R.id.main_menu_iv)
    ImageView mMainMenuIv;
    @BindView(R.id.main_indicator)
    MagicIndicator mMainIndicator;
    @BindView(R.id.main_search_iv)
    ImageView mMainSearchIv;
    @BindView(R.id.main_vp)
    ViewPager mMainVp;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mMainDrawerLayout;

    private List<String> mNavTitles;
    private MainPagerAdapter mMainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTitles();
        initViewPager();
        initIndicator();
    }

    @OnClick({R.id.main_menu_iv, R.id.main_search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_menu_iv:
                mMainDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.main_search_iv:
                break;
        }
    }

    private void initTitles(){
        mNavTitles = new ArrayList<>();
        mNavTitles.add(getResources().getString(R.string.me));
        mNavTitles.add(getResources().getString(R.string.recommend));
        mNavTitles.add(getResources().getString(R.string.explore));
    }

    private void initIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mNavTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                titleView.setNormalColor(ContextCompat.getColor(MainActivity.this,R.color.nav_text_normal));
                titleView.setSelectedColor(ContextCompat.getColor(MainActivity.this,R.color.nav_text_select));
                titleView.setText(mNavTitles.get(index));
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMainVp.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMainIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMainIndicator,mMainVp);
        mMainVp.setCurrentItem(1);
    }

    private void initViewPager(){
        List<Fragment> list = new ArrayList<>();
        list.add(MeFragment.newInstance());
        list.add(RecommendFragment.newInstance());
        list.add(ExploreFragment.newInstance());
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),list);
        mMainVp.setAdapter(mMainPagerAdapter);
    }

    class MainPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;

        public MainPagerAdapter(FragmentManager fm,List<Fragment> fragmentList){
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList == null ?0:mFragmentList.size();
        }
    }
}
