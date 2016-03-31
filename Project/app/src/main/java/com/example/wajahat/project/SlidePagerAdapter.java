package com.example.wajahat.project;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wajahat on 31/3/16.
 */
public class SlidePagerAdapter extends PagerAdapter {
    private static final String TAG = "SlidePagerAdapter";

    private Context mContext;
    private int mCount;
    private LinearLayout mThumbnail;
    private ViewPager mPager;
    private String[] mImageUrl;
    private String[] mThumbUrl;

    public SlidePagerAdapter(Context context,
                             int count,
                             LinearLayout thumbnail,
                             ViewPager pager,
                             String [] imageUrl,
                             String [] thumbUrl) {
        mContext = context;
        mCount = count;
        mThumbnail = thumbnail;
        mPager = pager;
        mImageUrl = imageUrl;
        mThumbUrl = thumbUrl;
    }
    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View viewPagerSwipeLayout = inflater.inflate(R.layout.view_page_swipe_image, container,
                false);
        container.addView(viewPagerSwipeLayout);
        ImageView thumbView = new ImageView(mContext);
        thumbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Thumbnail clicked");
                mPager.setCurrentItem(position);
            }
        });
        mThumbnail.addView(thumbView);
        ImageView imageView = (ImageView) viewPagerSwipeLayout
                .findViewById(R.id.view_pager_swipe_image_view);
        new ImageDownloaderTask(imageView).execute(mImageUrl[position]);
        new ImageDownloaderTask(thumbView).execute(mThumbUrl[position]);
        return viewPagerSwipeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}

