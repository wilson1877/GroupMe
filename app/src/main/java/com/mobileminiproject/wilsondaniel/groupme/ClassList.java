package com.mobileminiproject.wilsondaniel.groupme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ClassList extends ArrayAdapter<Class> {
    private Activity context;
    List<Class> classes;
    ListView classList;

    public ClassList (Activity context, List<Class> classes) {
        super(context, R.layout.class_list, classes);
        this.context = context;
        this.classes = classes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.class_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDesc);

        Class claSS = classes.get(position);
        textViewName.setText(claSS.getClassName());
        textViewDescription.setText(claSS.getClassDescription());

        return listViewItem;
    }
}