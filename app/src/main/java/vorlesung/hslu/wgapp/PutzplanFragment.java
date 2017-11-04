package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.DialogFragment;
import android.app.Fragment;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static vorlesung.hslu.wgapp.R.id.date;
import static vorlesung.hslu.wgapp.R.id.parent;
import static vorlesung.hslu.wgapp.R.id.text;

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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(
                "Putzplan");
        customAdapter = new PutzplanCustomAdapter(getActivity(),putzliste);
        ListView listView = (ListView) putzplanview.findViewById(R.id.putzplan_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fab = (FloatingActionButton) putzplanview.findViewById(R.id.putzfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MyCustomAlertDialog();



            }
        });
       Date datum = new Date(31-10-2017);
        Putzdaten erstedaten = new Putzdaten("Wohnung saugen", "wöchentlich", datum, R.drawable.profil_mann1);
        addItem(erstedaten);




        return putzplanview;

    }





    private void addItem(Putzdaten daten ){
        putzliste.add(daten);
        customAdapter.notifyDataSetChanged();

    }
    private void MyCustomAlertDialog(){
        MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.putzplan_dialog_add_item);
        putzerspinner  = (Spinner) MyDialog.findViewById(R.id.putzplan_dialog_start_putzer);
        seekbar = (SeekBar) MyDialog.findViewById(R.id.putzplan_dialog_haeufigkeit);
        textview = (TextView)  MyDialog.findViewById(R.id.putzplan_dialog_haeufigkeit_text);
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
               if(progress == 1) {
                   textview.setText("täglich");
               }
               else if (progress == 2){
                   textview.setText("wöchentlich");
               }
               else if(progress == 3 ) {
                   textview.setText("1 mal im Monat");
               }
            }
        });


        String[] items = new String[] { "Luca", "Jan", "Ash", "Rosa", "Maik" };

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
           }  });

        btnDatePicker=(Button)MyDialog.findViewById(R.id.btn_date);
        txtDate=(TextView)MyDialog.findViewById(R.id.in_date);
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

                Date datum = new Date(1-11-2017);
                String haeufigkeit = textview.getText().toString();
                Putzdaten neueDaten = new Putzdaten(aufgabenname,haeufigkeit,datum, R.drawable.profil_mann1);
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







