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

public class HaushaltsbuchCustomAdapterNewExpense extends BaseAdapter {

    private Activity activity;
    private Wohngemeinschaft wg;
    private ArrayList<Person> arrayList;
    private Person user;

    public HaushaltsbuchCustomAdapterNewExpense(Activity activity) {
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
        arrayList = new ArrayList<>(wg.getMitbewohner().values());
    }

    @Override
    public int getCount() {
        return wg.getMitbewohner().size();
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
            row = inflater.inflate(R.layout.haushaltsbuch_dialog_new_expense_user_item, parent, false);
        }

        user = arrayList.get(position);

        CheckBox check = (CheckBox) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user = arrayList.get(position);
                if (isChecked) {
                    HaushaltsbuchFragment.boughtFor.put(user.getId(), user);
                } else if (!isChecked) {
                    HaushaltsbuchFragment.boughtFor.remove(user.getId());
                }
            }
        });

        TextView name = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_name);

        check.setChecked(false);
        name.setText(user.getName());
        return row;
    }
}
