package vorlesung.hslu.wgapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class fragment_einkaufszettel extends Fragment {


    public fragment_einkaufszettel einkaufszettelActivity = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] produkte = {"Bananen", "Klopapier", "Gehacktes", "Marmelade", "Klopapier", "Gehacktes", "Marmelade", "Klopapier", "Gehacktes", "Marmelade", "Klopapier", "Gehacktes", "Marmelade", "Klopapier", "Gehacktes", "Marmelade"};

        View einkaufszettelView = inflater.inflate(R.layout.fragment_einkaufszettel, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Einkaufszettel");

        einkaufszettelActivity = this;

        CustomAdapter customAdapter = new CustomAdapter((MainActivity)getActivity(), produkte);

        //List<String> produktListe = new ArrayList<>(Arrays.asList(produkte));

        //ArrayAdapter<String> produktListeAdapter = new ArrayAdapter<>(
        //       getActivity(), //aktuelle Umgebung
        //        R.layout.fragment_einkaufszettel_listview_item, //XML Datei in der ID liegt
        //        R.id.list_item_titel, //List Item ID
        //        produktListe); //Array Liste


        ListView listView = (ListView) einkaufszettelView.findViewById(R.id.listview_einkaufszettel);
        listView.setAdapter(customAdapter);

        return einkaufszettelView;
    }

    public class CustomAdapter extends BaseAdapter {

        private String[] array;
        Activity activity;

        public CustomAdapter(Activity activity, String[] array) {
            this.activity = activity;
            this.array = array;
        }

        @Override
        public int getCount() {
            return array.length;
        }

        @Override
        public Object getItem(int position) {
            return array[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            if (row==null) {
                LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.fragment_einkaufszettel_listview_item, parent, false);
            }
            TextView itemTitel = (TextView) row.findViewById(R.id.list_item_titel);
            TextView itemDescription = (TextView) row.findViewById(R.id.list_item_description);

            itemTitel.setText(array[position].toString());
            itemDescription.setText((++position) + "");

            return row;
        }
    }
}