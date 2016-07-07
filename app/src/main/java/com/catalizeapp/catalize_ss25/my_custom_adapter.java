package com.catalizeapp.catalize_ss25;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by meena on 6/27/2016.
 */
public class my_custom_adapter extends ArrayAdapter<String> {

    private Context context                     = null;
    ArrayList<String> elements                 = null;
    public static ArrayList<Boolean> itemChecked      = null;

    public my_custom_adapter(Context context, int type, ArrayList<String>  elements)
    {
        super(context, type, elements);
        this.elements =  elements;
        this.context = context;
        itemChecked = new ArrayList<Boolean>();
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("master_check_change")) {
                    boolean check_value = intent.getBooleanExtra("check_value", false);
                    set_checked(check_value);
                    notifyDataSetChanged();
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter("master_check_change"));
        set_checked(false);
    }

    // AS EVERY TIME LISTVIEW INFLATE YOUR VIEWS WHEN YOU MOVE THEM SO YOU NEED TO SAVE ALL OF YOUR CHECKBOX STATES IN SOME ARRAYLIST OTHERWISE IT WILL SET ANY DEFAULT VALUE.
    private void set_checked(boolean is_checked)
    {
        for (int i=0; i < elements.size(); i++) {
            itemChecked.add(i, is_checked);
        }
    }

    //THIS IS SIMPLY A CLASS VIEW WILL HOLD DIFFERENT VIEWS OF YOUR ROW.
    static class ViewHolder
    {
        public TextView tv;
        public CheckBox cb;
        public ImageView iv;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        ViewHolder holder = null;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            // HERE I AM INFLATING LISTVIEW LAYOUT.
            rowView = inflater.inflate(R.layout.text, null, false);
            holder = new ViewHolder();
            holder.cb = (CheckBox) rowView.findViewById(R.id.checkBox1);
            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.iv = (ImageView) rowView.findViewById(R.id.imageView1);
            rowView.setTag(holder);

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        if (holder != null) {
            holder.tv.setText(elements.get(position));

            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    itemChecked.set(position, isChecked);
                }
            });

            if(position < itemChecked.size()) {
                holder.cb.setChecked(itemChecked.get(position));
            }
        }
        return rowView;
    }
}
