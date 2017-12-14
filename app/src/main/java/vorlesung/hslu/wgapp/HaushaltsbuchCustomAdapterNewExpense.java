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

    Activity activity;
    Wohngemeinschaft wg;
    ArrayList<Person> arrayList;

    public HaushaltsbuchCustomAdapterNewExpense(Activity activity){
        this.activity = activity;
        wg = Wohngemeinschaft.getInstance();
        arrayList = new ArrayList<Person>(wg.getMitbewohner().values());
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

        final Person user = arrayList.get(position);

        CheckBox check = (CheckBox) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //BoughtFor;
                    HaushaltsbuchFragment.boughtFor.put(user.getId(), user);
                }
                else if (!isChecked){
                    //Delte from Bought For
                    HaushaltsbuchFragment.boughtFor.remove(user.getId());
                }
            }
        } );

        TextView name = (TextView) row.findViewById(R.id.haushaltsbuch_dialog_new_expense_user_item_name);

        check.setChecked(false);
        name.setText(user.getName());
        return row;
    }
}
