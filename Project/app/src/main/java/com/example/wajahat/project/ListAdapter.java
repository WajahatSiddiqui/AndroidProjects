package com.example.wajahat.project;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wajahat on 28/3/16.
 */
public class ListAdapter extends BaseAdapter {
    private ArrayList<ListItem> mListItems;
    private Context mContext;

    private static final String TEXT_VIEW_FONT_PATH = "fonts/Montserrat-Regular.ttf";
    private Typeface mTypeface;

    public ListAdapter(Context context, ArrayList<ListItem> listItems) {
        mContext = context;
        mListItems = listItems;
        mTypeface = Typeface.createFromAsset(context.getAssets(), TEXT_VIEW_FONT_PATH);
    }
    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ProjectTitle = (TextView)
                    convertView.findViewById(R.id.text_view_title);
            viewHolder.PicCount = (TextView) convertView.findViewById(R.id.text_view_count);
            viewHolder.ProjectTitle.setTypeface(mTypeface);
            viewHolder.PicCount.setTypeface(mTypeface);
            viewHolder.Radio = (RadioButton)
                    convertView.findViewById(R.id.list_item_radio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ListItem listItem = mListItems.get(position);
        viewHolder.ProjectTitle.setText(listItem.getProjectTitle());
        viewHolder.PicCount.setText(listItem.getPicCount() + " photos");
        listItem.setSelected(!listItem.isSelected());
        viewHolder.Radio.setSelected(!listItem.isSelected());
        return convertView;
    }

    private static class ViewHolder {
        TextView ProjectTitle;
        TextView PicCount;
        RadioButton Radio;
    }
}
