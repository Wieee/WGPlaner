package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EinkaufszettelCustomAdapter extends BaseAdapter {
    private Activity activity;
    private EinkaufszettelProdukt data;
    private Wohngemeinschaft wg;
    ArrayList<EinkaufszettelProdukt> arrayList;

    public EinkaufszettelCustomAdapter(Activity activity) {
        wg = Wohngemeinschaft.getInstance();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return

                wg.getEinkaufszettel().size();
    }

    @Override
    public Object getItem(int position) {
        ArrayList<EinkaufszettelProdukt> arrayList = new ArrayList<>(wg.getEinkaufszettel().values());
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        arrayList = new ArrayList<>(wg.getEinkaufszettel().values());
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.einkaufszettel_listview_item, parent, false);
        }
        final TextView itemTitel = (TextView) row.findViewById(R.id.einkaufszettel_list_item_titel);
        final CheckBox itemCheck = (CheckBox) row.findViewById(R.id.einkaufszettel_list_item_checkbox);

        data = arrayList.get(position);
        itemCheck.setChecked(false);

        itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data = arrayList.get(position);
                if (isChecked) {
                    EinkaufszettelFragment.gekaufteListe.put(data.getName(), data);
                } else if (!isChecked) {
                    EinkaufszettelFragment.gekaufteListe.remove(data.getName());
                }
            }
        });

        itemTitel.setText(data.getAmount() + "x " + data.getName());
        return row;
    }

}