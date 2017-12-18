package vorlesung.hslu.wgapp;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PutzplanFragment extends Fragment {

    public static ArrayList<PutzplanAufgabe> cleanedList = new ArrayList<>();

    private PutzplanCustomAdapter customAdapter;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");
    private Wohngemeinschaft wg;
    private ArrayList<PutzplanAufgabe> cleaningList;
    private String selectedStartCleaner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        wg = Wohngemeinschaft.getInstance();
        cleaningList = new ArrayList<>(wg.getPutzplan().values());

        View putzplanView = inflater.inflate(R.layout.putzplan_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Putzplan");

        customAdapter = new PutzplanCustomAdapter(getActivity(), cleaningList);

        ListView listView = (ListView) putzplanView.findViewById(R.id.putzplan_ListView);
        listView.setLongClickable(true);
        listView.setAdapter(customAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                showDeleteDialog("Möchtest du diese Aufgabe wirklich löschen?", "", position);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) putzplanView.findViewById(R.id.putzfab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskCustomDialog();
            }
        });

        FloatingActionButton fabDelete = (FloatingActionButton) putzplanView.findViewById(R.id.putzfab_delete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemcleaned();

            }
        });

        return putzplanView;
    }

    public void showDeleteDialog(String title, CharSequence message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) {
            builder.setTitle(title);
        }

        builder.setMessage(message);

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeItem(position);
            }
        })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.show();
    }

    // protected for testing
    protected void addItem(PutzplanAufgabe task) {
        cleaningList.add(task);
        wg.addPutzplanAufgaben(task);
        customAdapter.notifyDataSetChanged();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> postValues = task.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/putzplan/" + task.getName(), postValues);
        mDatabase.child("wg").child(wg.getName()).updateChildren(childUpdates);

    }

    protected void removeItem(int position) {
        wg.removePutzplanAufgabe(cleaningList.get(position));
        mDatabase.child(wg.getName()).child("putzplan").child(cleaningList.get(position).getName()).setValue(null);
        cleaningList.remove(cleaningList.get(position));

        customAdapter.notifyDataSetChanged();
    }

    private void itemcleaned() {
        PutzplanAufgabe task;
        int i = cleanedList.size();

        while (i > 0) {
            task = cleanedList.get(--i);
            if (cleaningList.containsAll(cleanedList)) {

                //remove item and change the item, then add it again
                cleaningList.remove(task);
                cleanedList.remove(task);
                wg.removePutzplanAufgabe(task);

                task.setFirstCleaner(task.getNextCleaner());
                addItem(task);

                Map<String, Object> postValues = task.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/putzplan/" + task.getName(), postValues);
                mDatabase.child(wg.getName()).updateChildren(childUpdates);
            }
        }
        customAdapter.notifyDataSetChanged();
    }

    private void AddTaskCustomDialog() {
        final ArrayList<String> cleaner = new ArrayList<>();
        wg = Wohngemeinschaft.getInstance();

        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setTitle("Neue Aufgabe erstellen");
        myDialog.setContentView(R.layout.putzplan_dialog_add_item);

        final Spinner putzerspinner = (Spinner) myDialog.findViewById(R.id.putzplan_dialog_start_putzer);
        final SeekBar seekBar = (SeekBar) myDialog.findViewById(R.id.putzplan_dialog_haeufigkeit);
        final TextView textview = (TextView) myDialog.findViewById(R.id.putzplan_dialog_haeufigkeit_text);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (progress) {
                    case 1:
                        textview.setText("täglich");
                        break;
                    case 2:
                        textview.setText("wöchentlich");
                        break;
                    case 3:
                        textview.setText("1 mal im Monat");
                        break;
                }
            }
        });

        for (Person value : wg.getMitbewohner().values()) {
            cleaner.add(value.getName());
        }

        //Adapter can not work with ArrayList without errors therefore conversion to String[]
        String[] finalcleaner = cleaner.toArray(new String[cleaner.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, finalcleaner);
        putzerspinner.setAdapter(adapter);
        putzerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedStartCleaner = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Erster Putzer : " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        myDialog.show();

        Button button = (Button) myDialog.findViewById(R.id.putzplan_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String aufgabenname = ((EditText) myDialog.findViewById(R.id.putzplan_dialog_aufgaben_name)).getText().toString();
                if (aufgabenname.trim().equals("")) {
                    Toast toast = Toast.makeText(view.getContext(), "Bitte gib einen Namen für die Aufgabe an", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    String haeufigkeit = textview.getText().toString();
                    Person cleaner = null;
                    for (Person value :  wg.getMitbewohner().values()) {
                        if (value.getName().equals(selectedStartCleaner)) {
                            cleaner = value;
                        }
                    }

                    addItem(new PutzplanAufgabe(aufgabenname, haeufigkeit, cleaner));
                    customAdapter.notifyDataSetChanged();

                    myDialog.hide();

                    Toast toast = Toast.makeText(view.getContext(), "neue Aufgabe wurde hinzugefügt.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}








