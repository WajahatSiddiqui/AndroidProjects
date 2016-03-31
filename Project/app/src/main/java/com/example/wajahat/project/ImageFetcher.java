package com.example.wajahat.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

/**
 * Created by wajahat on 30/3/16.
 */
public class ImageFetcher {

    private static final String TAG = "ImageFetcher";
    // Create an Lru Cache 10% of application space
    private LruCache<String, Bitmap> mLruCache =
            new LruCache<>((int) Math.round(0.10 * Runtime.getRuntime().maxMemory() / 1024));

    private HashSet<String> mHashSet;

    public ImageFetcher(HashSet<String> hashSet) {
        mHashSet = hashSet;
    }

    private Bitmap downloadBitmap(String url) {
        InputStream is = null;
        Bitmap bitmap = null;
        int resCode = -1;
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            if (!(urlConnection instanceof HttpURLConnection))
                throw new IOException("Url is not HTTP URL");
            HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            resCode = httpURLConnection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK)
                is = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            if (is != null) is.close();
            httpURLConnection.disconnect();
        } catch (IOException exception) {
            Log.d(TAG, "Error while downloadBitmap: "+exception.getMessage());
        }
        return bitmap;
    }

    private void loadImage(ImageView imageView, String url) {

    }
}
