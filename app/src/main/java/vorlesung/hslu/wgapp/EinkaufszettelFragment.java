package vorlesung.hslu.wgapp;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EinkaufszettelFragment extends Fragment {

    ArrayList<String> einkaufsListe = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View einkaufszettelView = inflater.inflate(R.layout.einkaufszettel_fragment, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Einkaufszettel");

        final EinkaufszettelCustomAdapter customAdapter = new EinkaufszettelCustomAdapter(getActivity(), einkaufsListe);
        ListView listView = (ListView) einkaufszettelView.findViewById(R.id.einkaufszettel_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fab = (FloatingActionButton) einkaufszettelView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCustomAlertDialog();
                addItem(customAdapter, "Eier");
            }
        });

        addItem(customAdapter, "Wurst");
        addItem(customAdapter, "Käse");
        addItem(customAdapter, "Brot");

        return einkaufszettelView;
    }

    private void addItem(BaseAdapter customAdapter, String string){
        einkaufsListe.add(string);
        customAdapter.notifyDataSetChanged();
    }

    private void MyCustomAlertDialog(){
        Dialog MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.einkaufszettel_dialog_add_item);
        MyDialog.show();
    }
}