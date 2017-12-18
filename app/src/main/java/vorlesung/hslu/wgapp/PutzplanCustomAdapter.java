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

public class PutzplanCustomAdapter extends BaseAdapter {

    private Activity activity;
    private PutzplanAufgabe data;
    private Wohngemeinschaft wg;
    private ArrayList<PutzplanAufgabe> arrayList;

    public PutzplanCustomAdapter(Activity activity, ArrayList arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        wg = Wohngemeinschaft.getInstance();
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

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.putzplan_listview_item, parent, false);
        }
        TextView itemTitel = (TextView) row.findViewById(R.id.list_item_titel);
        TextView itemDescription = (TextView) row.findViewById(R.id.list_item_description);
        TextView itemCleaner = (TextView) row.findViewById(R.id.list_item_firstCleaner);

        data = arrayList.get(position);
        CheckBox itemCheck = (CheckBox) row.findViewById(R.id.putzplan_checkbox);
        itemCheck.setChecked(false);

        itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data  = arrayList.get(position);
                if (isChecked) {
                    PutzplanFragment.cleanedList.add((PutzplanAufgabe) getItem(position));
                } else if (!isChecked) {
                    if (PutzplanFragment.cleanedList.equals(data)) {
                        PutzplanFragment.cleanedList.remove(getItem(position));
                    }
                }
            }
        });

        itemTitel.setText(data.getName());
        itemDescription.setText(data.getHaeufigkeit());
        itemCleaner.setText(data.getFirstCleaner().getName());

        return row;
    }
}