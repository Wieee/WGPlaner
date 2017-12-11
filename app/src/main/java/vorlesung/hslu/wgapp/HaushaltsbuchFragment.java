package vorlesung.hslu.wgapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HaushaltsbuchFragment extends Fragment {
    View haushaltsbuchView;
    BaseAdapter customAdapter;
    Wohngemeinschaft wg = Wohngemeinschaft.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        haushaltsbuchView = inflater.inflate(R.layout.haushaltsbuch_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Haushaltsbuch");

        customAdapter = new HaushaltsbuchCustomAdapter(getActivity());
        ListView listView = (ListView) haushaltsbuchView.findViewById(R.id.haushaltsbuch_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fabAdd = (FloatingActionButton) haushaltsbuchView.findViewById(R.id.haushaltsbuch_fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddExpense();
            }
        });

        return haushaltsbuchView;
    }

    private void dialogAddExpense() {

        final Dialog myDialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newExpenseView = inflater.inflate(R.layout.haushaltsbuch_dialog_new_expense, null);

        final Button btnDatePicker = (Button) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_btn_date);
        final TextView txtDate = (TextView) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_date);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDatePicker) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        BaseAdapter customDialogAddExpense = new HaushaltsbuchCustomAdapterNewExpense(getActivity());
        ListView listView = (ListView) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_new_expense_listview);
        listView.setAdapter(customDialogAddExpense);

        Button button = (Button) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abfrage falls felder leer und button trotzdem geclickt wurde - validate function
                String name = ((EditText) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_product_name)).getText().toString();

                //amount muss double(am besten neue Klasse Währung einführen) sein, um verrechnung möglich zu machen
                String amount = ((EditText) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_amount)).getText().toString();
                String date = ((EditText) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_date)).getText().toString();

                HaushaltsbuchAusgabe newExpense = new HaushaltsbuchAusgabe(name, amount, date);
                if(validate(newExpense)){ //validate()
                    wg.addHaushaltsbuchAusgabe(newExpense);
                }

                myDialog.hide();

                Toast toast = Toast.makeText(
                        view.getContext(),
                       newExpense.toString() + " wurde erstellt.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        myDialog.setContentView(newExpenseView);
        myDialog.setTitle("Neue Ausgabe erstellen");
        myDialog.show();

    }

    private boolean validate(HaushaltsbuchAusgabe expense){
        //abfrage ob name vorhanden
        //abfrage ob amount passt, 2 stellen nach dem komma
        //abfrage ob boughtby und boughtfor angegeben
        //falls kein date - aktuellenTag angeben
        return false;
    }
}

