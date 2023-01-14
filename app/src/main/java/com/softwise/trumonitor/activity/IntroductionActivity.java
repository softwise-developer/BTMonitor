package com.softwise.trumonitor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.google.android.gms.location.places.Place;


public class IntroductionActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Button btnNext;
    /* access modifiers changed from: private */
    public Button btnSkip;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    /* access modifiers changed from: private */
    public int[] layouts;
    private MyPagerAdapter myViewPagerAdapter;
    /* access modifiers changed from: private */
    public ViewPager viewPager;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            IntroductionActivity.this.addBottomDots(i);
            if (i == IntroductionActivity.this.layouts.length - 1) {
                IntroductionActivity.this.btnNext.setText(IntroductionActivity.this.getString(R.string.start));
                IntroductionActivity.this.btnSkip.setVisibility(View.GONE);
                return;
            }
            IntroductionActivity.this.btnNext.setText(IntroductionActivity.this.getString(R.string.next));
            IntroductionActivity.this.btnSkip.setVisibility(View.VISIBLE);
        }
    };

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (SPTrueTemp.getIsFirstTimeLaunch(getApplicationContext())) {
            launchHomeScreen();
            finish();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(Place.TYPE_SUBLOCALITY_LEVEL_4);
        }
        setContentView((int) R.layout.activity_introduction);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        this.btnSkip = (Button) findViewById(R.id.btn_skip);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.layouts = new int[]{R.layout.welcome_slide1, R.layout.welcome_slide2};
        addBottomDots(0);
        changeStatusBarColor();
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        this.myViewPagerAdapter = myPagerAdapter;
        this.viewPager.setAdapter(myPagerAdapter);
        this.viewPager.addOnPageChangeListener(this.viewPagerPageChangeListener);
        this.btnSkip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IntroductionActivity.this.launchHomeScreen();
            }
        });
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int access$500 = IntroductionActivity.this.getItem(1);
                if (access$500 < IntroductionActivity.this.layouts.length) {
                    IntroductionActivity.this.viewPager.setCurrentItem(access$500);
                } else {
                    IntroductionActivity.this.launchHomeScreen();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void addBottomDots(int i) {
        TextView[] textViewArr;
        this.dots = new TextView[this.layouts.length];
        int[] intArray = getResources().getIntArray(R.array.array_dot_active);
        int[] intArray2 = getResources().getIntArray(R.array.array_dot_inactive);
        this.dotsLayout.removeAllViews();
        int i2 = 0;
        while (true) {
            textViewArr = this.dots;
            if (i2 >= textViewArr.length) {
                break;
            }
            textViewArr[i2] = new TextView(this);
            this.dots[i2].setText(Html.fromHtml("&#8226;"));
            this.dots[i2].setTextSize(35.0f);
            this.dots[i2].setTextColor(intArray2[i]);
            this.dotsLayout.addView(this.dots[i2]);
            i2++;
        }
        if (textViewArr.length > 0) {
            textViewArr[i].setTextColor(intArray[i]);
        }
    }

    /* access modifiers changed from: private */
    public int getItem(int i) {
        return this.viewPager.getCurrentItem() + i;
    }

    /* access modifiers changed from: private */
    public void launchHomeScreen() {
        SPTrueTemp.saveFirstTimeLaunch(getApplicationContext(), true);
        startActivity(new Intent(this, LauncherActivity.class));
        finish();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public MyPagerAdapter() {
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            @SuppressLint("WrongConstant") LayoutInflater layoutInflater2 = (LayoutInflater) IntroductionActivity.this.getSystemService("layout_inflater");
            this.layoutInflater = layoutInflater2;
            View inflate = layoutInflater2.inflate(IntroductionActivity.this.layouts[i], viewGroup, false);
            viewGroup.addView(inflate);
            return inflate;
        }

        public int getCount() {
            return IntroductionActivity.this.layouts.length;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }
}
