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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EinkaufszettelFragment extends Fragment {

    public ArrayList<EinkaufszettelProdukt> einkaufsListe = new ArrayList();
    public static ArrayList<EinkaufszettelProdukt> gekaufteListe = new ArrayList();
    EinkaufszettelCustomAdapter customAdapter;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");
    public Wohngemeinschaft wg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wg = Wohngemeinschaft.getInstance();


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
            }
        });


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                for (DataSnapshot singlesnap : snapshot) {
                    if (singlesnap.getKey().toString().equals(wg.getName())) {
                        Iterable<DataSnapshot> einkaufproducts = singlesnap.child("einkaufszettel").getChildren();
                        for (DataSnapshot singleproduct : einkaufproducts) {
                            EinkaufszettelProdukt product = singleproduct.getValue(EinkaufszettelProdukt.class);
                            if (product != null) {
                                einkaufsListe.add(product);

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

        return einkaufszettelView;
    }

    // protected to enable testing
    protected void addItem(EinkaufszettelProdukt product) {
        for (int i = 0; i < einkaufsListe.size(); i++) {
            EinkaufszettelProdukt listedItem = einkaufsListe.get(i);
            // check if the new product is already listed, if so increase the amount of products
            if (listedItem.getName().equals(product.getName())) {
                listedItem.setAmount(listedItem.getAmount() + product.getAmount());
                einkaufsListe.set(i, listedItem);

                Wohngemeinschaft wg = Wohngemeinschaft.getInstance();

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");
                Map<String, Object> postValues = listedItem.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/einkaufszettel/" + listedItem.getName(), postValues);
                mDatabase.child(wg.getName()).updateChildren(childUpdates);
                customAdapter.notifyDataSetChanged();
                return;
            }
        }
        Wohngemeinschaft wg = Wohngemeinschaft.getInstance();

        einkaufsListe.add(product);
        customAdapter.notifyDataSetChanged();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");
        Map<String, Object> postValues = product.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/einkaufszettel/" + product.getName(), postValues);
        mDatabase.child(wg.getName()).updateChildren(childUpdates);
    }

    protected void deleteItems() {

        //NOCH NICHT ZU 100 PROZENT RICHTIG
        EinkaufszettelProdukt product;
        int i = gekaufteListe.size();

        while (i > 0) {
            product = (EinkaufszettelProdukt) gekaufteListe.get(--i);
            if (einkaufsListe.containsAll(gekaufteListe)) {
                einkaufsListe.remove(product);
                gekaufteListe.remove(product);
                mDatabase.child(wg.getName()).child("einkaufszettel").child(product.getName()).setValue(null);

            }
        }
        customAdapter.notifyDataSetChanged();
    }

    private void customAlertDialog() {
        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setTitle("Zum Einkaufszettel packen");
        myDialog.setContentView(R.layout.einkaufszettel_dialog_add_item);
        myDialog.show();

        Button button = (Button) myDialog.findViewById(R.id.einkaufszettel_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ((EditText) myDialog.findViewById(R.id.einkaufszettel_dialog_product_name)).getText().toString();
                if (name.trim().equals("")) {
                    Toast toast = Toast.makeText(
                            view.getContext(),
                            "Bitte einen Produktnamen angeben",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int amount = ((DiscreteSeekBar) myDialog.findViewById(R.id.einkaufszettel_dialog_product_count)).getProgress();

                    EinkaufszettelProdukt newProduct = new EinkaufszettelProdukt(name, amount, "");

                    addItem(newProduct);

                    myDialog.hide();

                    Toast toast = Toast.makeText(
                            view.getContext(),
                            newProduct.toString() + " wurde zum Einkaufzettel hinzugefügt.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }
}