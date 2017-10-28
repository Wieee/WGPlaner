package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EinkaufszettelCustomAdapter extends BaseAdapter {
    Activity activity;
    ArrayList arrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row==null) {
            LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.einkaufszettel_listview_item, parent, false);
        }
        TextView itemTitel = (TextView) row.findViewById(R.id.einkaufszettel_list_item_titel);
        TextView itemDescription = (TextView) row.findViewById(R.id.einkaufszettel_list_item_description);

        itemTitel.setText(arrayList.get(position).toString());
        itemDescription.setText((++position) + "");

        return row;
    }
}