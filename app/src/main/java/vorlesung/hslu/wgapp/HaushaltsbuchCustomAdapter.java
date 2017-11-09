package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HaushaltsbuchCustomAdapter extends BaseAdapter {

    Activity activity;
    public HaushaltsbuchCustomAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            row = inflater.inflate(R.layout.haushaltsbuch_listview_item, parent, false);
        }
        TextView itemTitel = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_name);
        TextView itemAmount = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_amount);


        //USER LISTE EINPFLEGEN
        itemTitel.setText("Erste Person");
        itemAmount.setText("+25€");
        return row;
    }
}
