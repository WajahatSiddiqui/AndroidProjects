package com.example.wajahat.project;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private static final String TAG = "MainActivity";
    private static final String URL = "https://skillvo-dev.skillvo.com/api/sampledata?format=json";
    private GetDataTask mGetDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ListItem> listItems = mGetDataTask.getListItems();
                ListItem listItem = listItems.get(position);
                /*Toast.makeText(MainActivity.this, "Clicked on "
                        + listItems.get(position).getProjectTitle(), Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(MainActivity.this, SliderActivity.class);
                intent.putExtra(GetDataTask.PROJECT_TITLE, listItem.getProjectTitle());
                intent.putExtra(GetDataTask.URL, listItem.getImageUrl());
                intent.putExtra(GetDataTask.THUMBNAIL, listItem.getThumbUrl());
                intent.putExtra(GetDataTask.COUNT, listItem.getPicCount());
                try {
                    startActivityForResult(intent, 101);
                } catch (ActivityNotFoundException e) {
                    Log.d(TAG, "Activity not found: " + e.getMessage());
                }
            }
        });
        mGetDataTask = new GetDataTask(this, URL, getListView());
        mGetDataTask.execute();
    }
}