package com.example.wajahat.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by wajahat on 28/3/16.
 */
public class GetDataTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "GetDataTask";
    // Required JSON Array
    private static final String PAGED_LIST = "pagedList";
    private static final String PORTFOLIO = "portfolio";

    // Required JSON Objects
    public static final String PROJECT_TITLE = "title";
    public static final String URL = "url";
    public static final String THUMBNAIL = "thumbnail";
    public static final String COUNT = "count";

    private String mUrl;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private ListView mListView;
    private ArrayList<ListItem> mListItem;
    public GetDataTask(Context context, String url, ListView listView) {
        mUrl = url;
        mContext = context;
        mListView = listView;
    }

    public ArrayList<ListItem> getListItems() {
        return mListItem;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String jsonStr = downloadText(mUrl);
        createListItems(jsonStr);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        ListAdapter listAdapter = new ListAdapter(mContext, mListItem);
        mListView.setAdapter(listAdapter);
    }

    private void createListItems(String json) {
        String projectTitle;
        String [] imageUrls = null;
        int picCount = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray pagedList = jsonObject.getJSONArray(PAGED_LIST);
            if (pagedList == null) return;
            mListItem = new ArrayList<>(pagedList.length());
            for (int i = 0; i < pagedList.length(); i++) {
                JSONObject pagedListObj = pagedList.getJSONObject(i);
                projectTitle = pagedListObj.getString(PROJECT_TITLE);
                JSONArray portfolio = pagedListObj.getJSONArray(PORTFOLIO);
                if (portfolio != null && portfolio.length() > 0) {
                    picCount = portfolio.length();
                    imageUrls = new String[picCount];
                    for (int j = 0; j < picCount; j++) {
                        JSONObject portFolioObj = portfolio.getJSONObject(j);
                        imageUrls[j] = portFolioObj.getString(URL);
                    }
                }
                mListItem.add(new ListItem(projectTitle, picCount, imageUrls));
            }
        } catch (JSONException e) {
            Log.d(TAG, "Error while parseJsonObject exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String downloadText(String url) {
        int bufferSize = 200;
        String text = "";
        InputStream in = null;
        int resCode = -1;
        try {
            java.net.URL neturl = new URL(url);
            URLConnection urlConnection = neturl.openConnection();
            if (!(urlConnection instanceof HttpURLConnection))
                throw new IOException("Url is not HTTP URL");
            HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            resCode = httpURLConnection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK)
                in = httpURLConnection.getInputStream();
            if (in == null) return text;
            InputStreamReader isr = new InputStreamReader(in);
            int charReads;
            char[] inputBuffer = new char[bufferSize];
            while((charReads = isr.read(inputBuffer))>0) {
                String readString = String.copyValueOf(inputBuffer, 0, charReads);
                text += readString;
            }
            in.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            Log.d(TAG, "Error while openHttpConnection url: "+url+" exception: "+e.getMessage());
        }
        return text;
    }
}
