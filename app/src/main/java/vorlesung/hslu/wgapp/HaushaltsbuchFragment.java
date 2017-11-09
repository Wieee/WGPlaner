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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HaushaltsbuchFragment extends Fragment {
    View haushaltsbuchView;
    BaseAdapter customAdapter;
    ArrayList<HaushaltsbuchAusgabe> ausgabenListe = new ArrayList<>();
    ArrayList<Person> usersListe = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        haushaltsbuchView = inflater.inflate(R.layout.haushaltsbuch_fragment, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Haushaltsbuch");

        customAdapter = new HaushaltsbuchCustomAdapter(getActivity());
        ListView listView = (ListView) haushaltsbuchView.findViewById(R.id.haushaltsbuch_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fabAdd = (FloatingActionButton) haushaltsbuchView.findViewById(R.id.haushaltsbuch_fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogAddExpense();
            }
        });

        return haushaltsbuchView;
    }

    private void customDialogAddExpense() {
        final Dialog MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.haushaltsbuch_dialog_new_expense);
        MyDialog.show();

        Button button = (Button) MyDialog.findViewById(R.id.einkaufszettel_dialog_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abfrage falls felder leer und button trotzdem geclickt wurde

                String name = ((EditText) MyDialog.findViewById(R.id.haushaltsbuch_dialog_product_name)).getText() + "";

                //amount muss double(am besten neue Klasse Währung einführen) sein, um verrechnung möglich zu machen
                String amount = ((EditText) MyDialog.findViewById(R.id.haushaltsbuch_dialog_amount)).getText() + "";

                //date Struktur angucken, ist das notwendig?
                String date = ((EditText) MyDialog.findViewById(R.id.haushaltsbuch_dialog_date)).getText() + "";

                HaushaltsbuchAusgabe newExpense = new HaushaltsbuchAusgabe(name, amount, date);
                ausgabenListe.add(newExpense);

                MyDialog.hide();
                Toast toast = Toast.makeText(
                        view.getContext(),
                        newExpense.toString() + " wurde erstellt.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void customDialogUserExpenses() {
        final Dialog MyDialog = new Dialog(getActivity());
        MyDialog.setContentView(R.layout.haushaltsbuch_dialog_user_screen);
        MyDialog.show();
    }


}
