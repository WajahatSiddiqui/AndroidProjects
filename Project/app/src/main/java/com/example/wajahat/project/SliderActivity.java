package com.example.wajahat.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    private static final String MONTSERRAT_REGULAR = "fonts/Montserrat-Regular.ttf";
    private String mProjectTitle;
    private String[] mImageUrl;
    private String[] mThumbUrl;
    private int mCount;
    private float mCurrentRotationAngle = 0.0f;

    private SlidePagerAdapter mSlidePagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout mThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_layout);
        mProjectTitle = (String) getIntent().getExtras().get(GetDataTask.PROJECT_TITLE);
        mImageUrl = getIntent().getExtras().getStringArray(GetDataTask.URL);
        mThumbUrl = getIntent().getExtras().getStringArray(GetDataTask.THUMBNAIL);
        mCount = getIntent().getExtras().getInt(GetDataTask.COUNT);

        TextView tv = (TextView) findViewById(R.id.text_view_photos_count);
        tv.setText(mCount + " " + getResources().getString(R.string.text_view_photos_count_string));
        Typeface tf = Typeface.createFromAsset(getAssets(), MONTSERRAT_REGULAR);
        tv.setTypeface(tf);

        TextView prev_tv = (TextView) findViewById(R.id.prev_text_view);
        prev_tv.setTypeface(tf);

        TextView next_tv = (TextView) findViewById(R.id.next_text_view);
        next_tv.setTypeface(tf);

        final TextView current_count_tv = (TextView) findViewById(R.id.text_view_current_count);
        current_count_tv.setTypeface(tf);
        current_count_tv.setText("1 of " + mCount + " Photos");

        Button button = (Button) findViewById(R.id.add_more_photos_button);
        button.setTypeface(tf);

        mThumbnail = (LinearLayout) findViewById(R.id.thumbnails);
        mViewPager = (ViewPager) findViewById(R.id.image_view_pager);
        mSlidePagerAdapter = new SlidePagerAdapter(this, mCount, mThumbnail, mViewPager,
                mImageUrl, mThumbUrl);
        mViewPager.setAdapter(mSlidePagerAdapter);
        mViewPager.setOffscreenPageLimit(mCount);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                current_count_tv.setText(position+1 + " of " + mCount + " Photos");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onRotateLeft(View view) {
        ImageView thumbView = (ImageView) mThumbnail.getChildAt(1);
        ImageView imageView = (ImageView) mViewPager.getChildAt(1)
                .findViewById(R.id.view_pager_swipe_image_view);
        mCurrentRotationAngle -= 90;
        rotate(thumbView, mCurrentRotationAngle);
        rotate(imageView, mCurrentRotationAngle);
    }

    public void onRotateRight(View view) {
        ImageView thumbView = (ImageView) mThumbnail.getChildAt(1);
        ImageView imageView = (ImageView) mViewPager.getChildAt(1)
                .findViewById(R.id.view_pager_swipe_image_view);
        mCurrentRotationAngle += 90;
        rotate(thumbView, mCurrentRotationAngle);
        rotate(imageView, mCurrentRotationAngle);
    }

    private void rotate(ImageView imageView, float degrees) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }
}