package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fragment_einkaufszettel extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] produkte = {"Bananen", "Klopapier", "Gehacktes", "Marmelade"};

        View einkaufszettelView = inflater.inflate(R.layout.fragment_einkaufszettel, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Einkaufszettel");

        List<String> produktListe = new ArrayList<>(Arrays.asList(produkte));

        ArrayAdapter<String> produktListeAdapter =
                new ArrayAdapter<>(
                        getActivity(), //aktuelle Umgebung
                        R.layout.fragment_einkaufszettel_listview_item, //XML Datei in der ID liegt
                        R.id.listview_item_einkaufszettel, //List Item ID
                        produktListe); //Array Liste

        ListView listView = (ListView) einkaufszettelView.findViewById(R.id.listview_einkaufszettel);
        listView.setAdapter(produktListeAdapter);

        return einkaufszettelView;
    }
}
