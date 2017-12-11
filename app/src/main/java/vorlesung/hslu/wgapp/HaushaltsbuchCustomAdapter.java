package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HaushaltsbuchCustomAdapter extends BaseAdapter {

    Activity activity;
    Wohngemeinschaft wg;
    ArrayList<Person> arrayList;

    public HaushaltsbuchCustomAdapter(Activity activity) {
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
        arrayList = new ArrayList<Person>(wg.getMitbewohner().values());
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
            row = inflater.inflate(R.layout.haushaltsbuch_listview_item, parent, false);
        }
        final TextView itemTitel = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_name);
        final TextView itemAmount = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_amount);

        final Person user = arrayList.get(position);
        itemTitel.setText(user.getName());



        //Amount berechnen und in Variable speichern
        final double amount = 0;
        itemAmount.setText(amount + "€");

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(activity);

                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogUserExpenses = inflater.inflate(R.layout.haushaltsbuch_dialog_user_screen, null);

                TextView headerText = (TextView) dialogUserExpenses.findViewById(R.id.haushaltsbuch_dialog_user_screen_header);
                if (amount < 0) {
                    headerText.setText("Du schuldest " + user.getName() + " noch " + amount + "€.");
                } else {
                    headerText.setText("Du bekommst von " + user.getName() + " noch " + amount + "€.");
                }

                ListView list = (ListView) dialogUserExpenses.findViewById(R.id.haushaltsbuch_dialog_user_screen_listview);
                BaseAdapter customAdapter = new HaushaltsbuchCustomAdapterExpenseList(activity);
                list.setAdapter(customAdapter);

                myDialog.setTitle("Ausgabenübersicht");
                myDialog.setContentView(dialogUserExpenses);
                myDialog.show();
            }
        });

        return row;
    }
}
