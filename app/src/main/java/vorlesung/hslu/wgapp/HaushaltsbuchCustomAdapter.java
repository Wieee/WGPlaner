package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HaushaltsbuchCustomAdapter extends BaseAdapter {

    Activity activity;
    Wohngemeinschaft wg;
    ArrayList<Person> arrayList;
    Person currentUser;
    Dialog myDialog;

    public HaushaltsbuchCustomAdapter(Activity activity) {
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
        arrayList = new ArrayList<Person>(wg.getMitbewohner().values());
        currentUser = wg.getMitbewohner().get(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        arrayList.remove(currentUser);
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
        CheckBox itemCheck = (CheckBox) row.findViewById(R.id.haushaltsbuch_list_item_checkbox);
        TextView itemTitel = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_name);
        TextView itemAmount = (TextView) row.findViewById(R.id.haushaltsbuch_list_item_amount);

        final Person user = arrayList.get(position);

        itemCheck.setChecked(false);
        itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    HaushaltsbuchFragment.payed.add(user);
                }
                else if (!isChecked){
                    HaushaltsbuchFragment.payed.remove(user);
                }
            }
        } );

        itemTitel.setText(user.getName());

        final double amount = calculateAmount(user);
        final DecimalFormat amountDecimal = new DecimalFormat("##.##");
        if (amount < 0) {
            itemAmount.setTextColor(ContextCompat.getColor(activity, R.color.haushaltsbuch_minus_money));
            itemAmount.setText("-" + amountDecimal.format(amount) + "€");
        } else {
            itemAmount.setTextColor(ContextCompat.getColor(activity, R.color.haushaltsbuch_plus_money));
            itemAmount.setText("+" + amountDecimal.format(amount) + "€");
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new Dialog(activity);

                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogUserExpenses = inflater.inflate(R.layout.haushaltsbuch_dialog_user_screen, null);

                TextView headerText = (TextView) dialogUserExpenses.findViewById(R.id.haushaltsbuch_dialog_user_screen_header);
                if (amount < 0) {
                    headerText.setText("Du schuldest " + user.getName() + " noch " + amountDecimal.format(amount) + "€.");
                } else {
                    headerText.setText("Du bekommst von " + user.getName() + " noch " + amountDecimal.format(amount) + "€.");
                }

                ListView list = (ListView) dialogUserExpenses.findViewById(R.id.haushaltsbuch_dialog_user_screen_listview);
                BaseAdapter customAdapter = new HaushaltsbuchCustomAdapterExpenseList(activity, user);
                list.setAdapter(customAdapter);

                Button button = (Button) dialogUserExpenses.findViewById(R.id.haushaltsbuch_dialog_user_screen_btn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.hide();
                    }
                });

                myDialog.setTitle("Ausgabenübersicht");
                myDialog.setContentView(dialogUserExpenses);
                myDialog.show();
            }
        });
        return row;
    }

    public double calculateAmount(Person user){
        double amount = 0;
        wg = Wohngemeinschaft.getInstance();
        ArrayList<HaushaltsbuchAusgabe> holder = new ArrayList<>(wg.getHaushaltsbuch().values());
        ArrayList<HaushaltsbuchAusgabe> getFromUser = new ArrayList<>();
        ArrayList<HaushaltsbuchAusgabe> payToUser = new ArrayList<>();

        for (HaushaltsbuchAusgabe item : holder) {
            if (item.getBoughtBy().getId().equals(currentUser.getId())) {
                for (Person boughtFor : item.getBoughtFor().values()) {
                    if (boughtFor.getId().equals(user.getId())) {
                        getFromUser.add(item);
                    }
                }
            } else if (item.getBoughtBy().getId().equals(user.getId())) {
                for (Person boughtFor : item.getBoughtFor().values()) {
                    if (boughtFor.getId().equals(currentUser.getId())) {
                        payToUser.add(item);
                    }
                }
            }
        }
        for(HaushaltsbuchAusgabe item : getFromUser){

            amount = amount + (item.getAmount()/item.getBoughtFor().size());
        }
        for(HaushaltsbuchAusgabe item : payToUser){
            amount = amount - (item.getAmount()/item.getBoughtFor().size());
        }
        return amount;
    }
}
