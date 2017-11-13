package vorlesung.hslu.wgapp;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

public class EinkaufszettelFragment extends Fragment {

    ArrayList<String> einkaufsListe = new ArrayList<>();
    public static ArrayList<String> gekaufteListe = new ArrayList<>();
    EinkaufszettelCustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View einkaufszettelView = inflater.inflate(R.layout.einkaufszettel_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Einkaufszettel");

        customAdapter = new EinkaufszettelCustomAdapter(getActivity(), einkaufsListe);
        ListView listView = (ListView) einkaufszettelView.findViewById(R.id.einkaufszettel_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fabAdd = (FloatingActionButton) einkaufszettelView.findViewById(R.id.einkaufszettel_fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAlertDialog();
            }
        });

        FloatingActionButton fabDelete = (FloatingActionButton) einkaufszettelView.findViewById(R.id.einkaufszettel_fabShopped);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItems();
                einkaufszettelView.findViewById(R.id.einkaufszettel_fabShopped).setVisibility(View.INVISIBLE);
            }
        });

        addItem("1x Wurst");
        addItem("2x Käse");
        addItem("1x Brot");

        return einkaufszettelView;
    }

    private void addItem(String string) {
        einkaufsListe.add(string);
        customAdapter.notifyDataSetChanged();
    }

    private void deleteItems() {
        String string;
        int i = gekaufteListe.size();

        while (i > 0) {
            string = gekaufteListe.get(--i);
            if (einkaufsListe.containsAll(gekaufteListe)) {
                einkaufsListe.remove(string);
                gekaufteListe.remove(string);
            }
        }
        customAdapter.notifyDataSetChanged();
        customAdapter.checkedCounter = 0;
    }

    private void customAlertDialog() {
        final Dialog MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.einkaufszettel_dialog_add_item);
        MyDialog.show();


        Button button = (Button) MyDialog.findViewById(R.id.einkaufszettel_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = ((DiscreteSeekBar) MyDialog.findViewById(R.id.einkaufszettel_dialog_product_count)).getProgress()
                        + "x " + ((EditText) MyDialog.findViewById(R.id.einkaufszettel_dialog_product_name)).getText();
                addItem(newItem);

                MyDialog.hide();

                Toast toast = Toast.makeText(
                        view.getContext(),
                        newItem + " wurde zum Einkaufzettel hinzugefügt.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}