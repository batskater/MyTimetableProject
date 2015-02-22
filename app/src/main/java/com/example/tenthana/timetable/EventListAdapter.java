package com.example.tenthana.timetable;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventListAdapter extends BaseAdapter {
    Context context;
    private static ArrayList<EventListItem> eventList;

    private LayoutInflater mInflater;

    public EventListAdapter(Context context, ArrayList<EventListItem> list) {
        this.context = context;
        eventList = list;
    }

    public int getCount() {
        return eventList.size();
    }

    public Object getItem(int position) {
        return eventList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup arg2) {
        EventListItem contactListItems = eventList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_view, null);
        }
        TextView tvCid = (TextView)convertView.findViewById(R.id.courseid);
        tvCid.setText(contactListItems.getCourseid());
        TextView tvCname = (TextView)convertView.findViewById(R.id.coursename);
        tvCname.setText(contactListItems.getCoursename());
        TextView tvCins = (TextView)convertView.findViewById(R.id.instructor);
        tvCins.setText(contactListItems.getInstructor());
        TextView tvCplace = (TextView)convertView.findViewById(R.id.place);
        tvCplace.setText(contactListItems.getPlace());
        TextView tvCtstart = (TextView)convertView.findViewById(R.id.tstart);
        tvCtstart.setText(contactListItems.getTstart());
        TextView tvCtend = (TextView)convertView.findViewById(R.id.tend);
        tvCtend.setText(contactListItems.getTend());
        return convertView;

    }

}