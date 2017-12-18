package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HaushaltsbuchCustomAdapterExpenseList extends BaseAdapter {

    private Activity activity;
    private Wohngemeinschaft wg;
    private ArrayList<HaushaltsbuchAusgabe> arrayList;
    private Person user;
    private Person currentUser;

    public HaushaltsbuchCustomAdapterExpenseList(Activity activity, Person user) {
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();

        ArrayList<HaushaltsbuchAusgabe> holder = new ArrayList<>(wg.getHaushaltsbuch().values());
        arrayList = new ArrayList<>();
        this.user = user;
        currentUser = wg.getMitbewohner().get(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        //fill holder with data,
        //1. if the current user bought the product and the selected user is in the "boughtFor" list,
        for (HaushaltsbuchAusgabe item : holder) {
            if (item.getBoughtBy().getId().equals(currentUser.getId())) {
                for (Person boughtFor : item.getBoughtFor().values()) {
                    if (boughtFor.getId().equals(user.getId())) {
                        arrayList.add(item);
                    }
                }
                //2. if the selected user bought the product and the current user is in the "boughtFor" list
            } else if (item.getBoughtBy().getId().equals(user.getId())) {
                for (Person boughtFor : item.getBoughtFor().values()) {
                    if (boughtFor.getId().equals(currentUser.getId())) {
                        arrayList.add(item);
                    }
                }
            }
        }
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
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.haushaltsbuch_dialog_user_screen_item, parent, false);
        }

        TextView tvName = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_name);
        TextView tvExpense = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_expense);
        TextView tvAmount = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_amount);

        HaushaltsbuchAusgabe expense = arrayList.get(position);
        tvName.setText(expense.getBoughtBy().getName());
        tvExpense.setText(expense.getName());
        DecimalFormat amountDecimal = new DecimalFormat("##.##");
        if (expense.getBoughtBy().getId().equals(user.getId())) {
            tvAmount.setText("-" + amountDecimal.format(expense.getAmount()) + "€");
            tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.haushaltsbuch_minus_money));
        } else if (expense.getBoughtBy().getId().equals(currentUser.getId())) {
            tvAmount.setText("+" + amountDecimal.format(expense.getAmount()) + "€");
            tvAmount.setTextColor(ContextCompat.getColor(activity, R.color.haushaltsbuch_plus_money));
        }

        return row;

    }
}
