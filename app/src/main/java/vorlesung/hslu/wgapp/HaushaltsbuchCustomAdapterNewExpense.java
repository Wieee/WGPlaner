package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class HaushaltsbuchCustomAdapterNewExpense extends BaseAdapter {

    Activity activity;
    Wohngemeinschaft wg;

    public HaushaltsbuchCustomAdapterNewExpense(Activity activity){
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
    }
    @Override
    public int getCount() {
        return wg.getMitbewohner().size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row==null) {
            LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.haushaltsbuch_dialog_new_expense_user_item, parent, false);
        }

        Person user = (Person) wg.getMitbewohner().get(position);

        CheckBox check = (CheckBox) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_checkbox);
        TextView name = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_name);

        check.setChecked(false);
        name.setText(user.getName());
        return row;
    }
}
