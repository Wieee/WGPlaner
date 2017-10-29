package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EinkaufszettelCustomAdapter extends BaseAdapter {
    Activity activity;
    ArrayList arrayList;
    int checkedCounter = 0;

    public EinkaufszettelCustomAdapter(Activity activity, ArrayList arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row==null) {
            LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.einkaufszettel_listview_item, parent, false);
        }
        final TextView itemTitel = (TextView) row.findViewById(R.id.einkaufszettel_list_item_titel);
        CheckBox itemCheck = (CheckBox) row.findViewById(R.id.einkaufszettel_list_item_checkbox);
        itemCheck.setChecked(false);
        itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fabShopped);
                if (isChecked == true){
                    checkedCounter++;
                    EinkaufszettelFragment.gekaufteListe.add((String) getItem(position));
                }
                else if (isChecked == false){
                    checkedCounter--;
                    if (EinkaufszettelFragment.gekaufteListe.equals(itemTitel)) {
                        EinkaufszettelFragment.gekaufteListe.remove(getItem(position));
                    }
                }
                if (checkedCounter > 0){
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        } );
        itemTitel.setText(arrayList.get(position).toString());

        return row;
    }

}