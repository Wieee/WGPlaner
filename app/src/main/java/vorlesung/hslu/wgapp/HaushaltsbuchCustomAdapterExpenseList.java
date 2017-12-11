package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HaushaltsbuchCustomAdapterExpenseList extends BaseAdapter {

    Activity activity;
    Wohngemeinschaft wg;
    ArrayList<HaushaltsbuchAusgabe> arrayList;

    public HaushaltsbuchCustomAdapterExpenseList(Activity activity){
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
        //arrayList = new ArrayList<Person>(wg.getHaushaltsbuch().values());
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

        HaushaltsbuchAusgabe expense = arrayList.get(position);
        TextView tvName = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_name);
        TextView tvExpense = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_expense);
        TextView tvAmount = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_user_screen_amount);

        tvName.setText(expense.getBoughtBy().getName());
        tvExpense.setText(expense.getName());
        tvAmount.setText(expense.getAmount());

        return row;
    }
}
