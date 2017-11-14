package vorlesung.hslu.wgapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PutzplanFragment extends Fragment {
    View putzplanview;
    ArrayList putzliste = new ArrayList();

    Spinner putzerspinner;
    Button btnDatePicker;
    TextView txtDate;
    Dialog MyDialog;
    private int mYear, mMonth, mDay;
    SeekBar seekbar;
    TextView textview;


    PutzplanCustomAdapter customAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        putzplanview = inflater.inflate(R.layout.putzplan_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle(
                "Putzplan");
        customAdapter = new PutzplanCustomAdapter(getActivity(), putzliste);
        ListView listView = (ListView) putzplanview.findViewById(R.id.putzplan_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fab = (FloatingActionButton) putzplanview.findViewById(R.id.putzfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomAlertDialog();


            }
        });
        Date datum = new Date(31 - 10 - 2017);
        PutzplanAufgabe erstedaten = new PutzplanAufgabe("Wohnung saugen", "wöchentlich", datum);
        addItem(erstedaten);

        return putzplanview;
    }


    private void addItem(PutzplanAufgabe daten) {
        putzliste.add(daten);
        customAdapter.notifyDataSetChanged();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Schreiben von daten
        DatabaseReference objctRef = database.getReference("aufgabe");
        objctRef.push().setValue(daten);
        //lesen von daten
        objctRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {PutzplanAufgabe test = dataSnapshot.getValue(PutzplanAufgabe.class);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    private void MyCustomAlertDialog() {
        MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.putzplan_dialog_add_item);
        putzerspinner = (Spinner) MyDialog.findViewById(R.id.putzplan_dialog_start_putzer);
        seekbar = (SeekBar) MyDialog.findViewById(R.id.putzplan_dialog_haeufigkeit);
        textview = (TextView) MyDialog.findViewById(R.id.putzplan_dialog_haeufigkeit_text);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progress == 1) {
                    textview.setText("täglich");
                } else if (progress == 2) {
                    textview.setText("wöchentlich");
                } else if (progress == 3) {
                    textview.setText("1 mal im Monat");
                }
            }
        });


        String[] items = new String[]{"Luca", "Jan", "Ash", "Rosa", "Maik"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, items);
        putzerspinner.setAdapter(adapter);


        putzerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(parent.getContext(),
                        "Erster Putzer : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnDatePicker = (Button) MyDialog.findViewById(R.id.btn_date);
        txtDate = (TextView) MyDialog.findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnDatePicker) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


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


        MyDialog.show();

        Button button = (Button) MyDialog.findViewById(R.id.putzplan_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aufgabenname = ((EditText) MyDialog.findViewById(R.id.putzplan_dialog_aufgaben_name)).getText().toString();

                Date datum = new Date(1 - 11 - 2017);
                String haeufigkeit = textview.getText().toString();
                PutzplanAufgabe neueDaten = new PutzplanAufgabe(aufgabenname, haeufigkeit, datum);
                addItem(neueDaten);

                MyDialog.hide();

                Toast toast = Toast.makeText(
                        view.getContext(),
                        "neue Aufgabe wurde hinzugefügt.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


}







