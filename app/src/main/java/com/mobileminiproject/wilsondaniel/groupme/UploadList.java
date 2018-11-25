package com.mobileminiproject.wilsondaniel.groupme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class UploadList extends ArrayAdapter<Upload> {
    private Activity context;
    List<Upload> uploads;
    ListView uploadsList;

    public UploadList(Activity context, List<Upload> uploads) {
        super(context, R.layout.uploads_list, uploads);
        this.context = context;
        this.uploads = uploads;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.uploads_list, null, true);

        TextView textViewFileName = (TextView) listViewItem.findViewById(R.id.textViewFileName);

        Upload upload = uploads.get(position);
        textViewFileName.setText(upload.getName());

        return listViewItem;
    }
}
