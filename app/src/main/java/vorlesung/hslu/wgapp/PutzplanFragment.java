package vorlesung.hslu.wgapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meike on 9.11.2017
 */

public class PutzplanFragment extends Fragment {
    View putzplanview;
    ArrayList<PutzplanAufgabe> putzliste = new ArrayList<>();
    String selectedCleaner = "";
    HashMap<String, Person> mitbewohner;


    public static ArrayList geputzteListe = new ArrayList();

    Spinner putzerspinner;
    Button btnDatePicker;
    TextView txtDate;
    Dialog myDialog;
    private int mYear, mMonth, mDay;
    SeekBar seekbar;
    TextView textview;
    boolean answer;


    PutzplanCustomAdapter customAdapter;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");
    Wohngemeinschaft wg = Wohngemeinschaft.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        putzplanview = inflater.inflate(R.layout.putzplan_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle(
                "Putzplan");
        customAdapter = new PutzplanCustomAdapter(getActivity(), putzliste);
        ListView listView = (ListView) putzplanview.findViewById(R.id.putzplan_ListView);
        listView.setLongClickable(true);
        listView.setAdapter(customAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
             showDialog("Möchtest du diese Aufgabe wirklich löschen?", "" , position);
               return true;

            }
        });


        FloatingActionButton fab = (FloatingActionButton) putzplanview.findViewById(R.id.putzfab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomAlertDialog();
            }
        });
        FloatingActionButton fabDelete = (FloatingActionButton) putzplanview.findViewById(R.id.putzfab_delete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemcleaned();

            }
        });

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                for (DataSnapshot singlesnap : snapshot) {
                    if (singlesnap.getKey().toString().equals(wg.getName())) {
                        Iterable<DataSnapshot> cleaningtasks = singlesnap.child("putzplan").getChildren();
                        for (DataSnapshot singletask : cleaningtasks) {
                            PutzplanAufgabe task = singletask.getValue(PutzplanAufgabe.class);
                            if (task != null) {
                                putzliste.add(task);

                            }
                        }
                        customAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return putzplanview;
    }

    public void showDialog(String title, CharSequence message, final int  position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeItem(position);
                customAdapter.notifyDataSetChanged();

            }
        })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.show();

    }



    private void addItem(PutzplanAufgabe daten) {
        putzliste.add(daten);
        customAdapter.notifyDataSetChanged();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Schreiben von daten
        Wohngemeinschaft wg = Wohngemeinschaft.getInstance();
        wg.addPutzplanAufgaben(daten);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> postValues = daten.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/putzplan/" + daten.aufgabe, postValues);
        mDatabase.child("wg").child(wg.getName()).updateChildren(childUpdates);



    }
    private void removeItem(int position){

        PutzplanAufgabe aufgabe;



            aufgabe = (PutzplanAufgabe) putzliste.get(position);
                 putzliste.remove(aufgabe);

                wg.removePutzplanAufgabe(aufgabe);

                mDatabase.child(wg.getName()).child("putzplan").child(aufgabe.getAufgabe()).setValue(null);
            }





    private void itemcleaned() {
        PutzplanAufgabe aufgabe;
        int i = geputzteListe.size();

        while (i > 0) {
            aufgabe = (PutzplanAufgabe) geputzteListe.get(--i);
            if (putzliste.containsAll(geputzteListe)) {
                //remove item and change it then add it again
                putzliste.remove(aufgabe);
                geputzteListe.remove(aufgabe);
                wg.removePutzplanAufgabe(aufgabe);


                aufgabe.setFirstCleaner(aufgabe.getNextCleaner());
                addItem(aufgabe);


                Map<String, Object> postValues = aufgabe.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/putzplan/" + aufgabe.aufgabe, postValues);
                mDatabase.child(wg.getName()).updateChildren(childUpdates);
            }
        }
        customAdapter.notifyDataSetChanged();


    }




    private void MyCustomAlertDialog() {
       final  ArrayList<String> cleaner = new ArrayList<String>();
        wg = Wohngemeinschaft.getInstance();
        myDialog = new Dialog(getActivity());
        myDialog.setTitle("Neue Aufgabe erstellen");
        myDialog.setContentView(R.layout.putzplan_dialog_add_item);
        putzerspinner = (Spinner) myDialog.findViewById(R.id.putzplan_dialog_start_putzer);
        seekbar = (SeekBar) myDialog.findViewById(R.id.putzplan_dialog_haeufigkeit);
        textview = (TextView) myDialog.findViewById(R.id.putzplan_dialog_haeufigkeit_text);
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


        mitbewohner = wg.getMitbewohner();
        for (Person value : mitbewohner.values()) {
            cleaner.add(value.getName());
        }


        //Adapter can not work with ArrayList without errors therefore conversion to String[]
        String[] finalcleaner = cleaner.toArray(new String[cleaner.size()]);


    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,finalcleaner);

        putzerspinner.setAdapter(adapter);


        putzerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedCleaner = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(),
                        "Erster Putzer : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnDatePicker = (Button) myDialog.findViewById(R.id.btn_date);
        txtDate = (TextView) myDialog.findViewById(R.id.in_date);
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


        myDialog.show();

        Button button = (Button) myDialog.findViewById(R.id.putzplan_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aufgabenname = ((EditText) myDialog.findViewById(R.id.putzplan_dialog_aufgaben_name)).getText().toString();
                if(aufgabenname.trim().equals("")) {

                    Toast toast = Toast.makeText(
                            view.getContext(),
                            "Bitte gib einen Namen für die Aufgabe an",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    //Nicht lieber String anstatt ein Datum nehmen anstatt den Datentyp Datum?
                    Date datum = new Date(1 - 11 - 2017);
                    String haeufigkeit = textview.getText().toString();
                    Person cleaner = null ;
                    for (Person value : mitbewohner.values()) {
                        if(value.getName().equals(selectedCleaner)){
                            cleaner = value;

                        }
                    }
                    PutzplanAufgabe neueDaten = new PutzplanAufgabe(aufgabenname, haeufigkeit, datum,cleaner);

                    addItem(neueDaten);

                    myDialog.hide();

                    Toast toast = Toast.makeText(
                            view.getContext(),
                            "neue Aufgabe wurde hinzugefügt.",
                            Toast.LENGTH_SHORT);
                    toast.show();}


            }
        });
    }


}







