package vorlesung.hslu.wgapp;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HaushaltsbuchFragment extends Fragment {

    public static ArrayList<Person> payed = new ArrayList<>();
    public static HashMap<String, Person> boughtFor = new HashMap<>();

    //for testing
    protected Person currentUser;

    private View haushaltsbuchView;
    private HaushaltsbuchCustomAdapter customAdapter;
    private Wohngemeinschaft wg;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        haushaltsbuchView = inflater.inflate(R.layout.haushaltsbuch_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Haushaltsbuch");

        wg = Wohngemeinschaft.getInstance();
        currentUser = wg.getMitbewohner().get(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        customAdapter = new HaushaltsbuchCustomAdapter(getActivity());
        ListView listView = (ListView) haushaltsbuchView.findViewById(R.id.haushaltsbuch_ListView);
        listView.setAdapter(customAdapter);

        FloatingActionButton fabAdd = (FloatingActionButton) haushaltsbuchView.findViewById(R.id.haushaltsbuch_fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddExpense();
            }
        });

        FloatingActionButton fabPayed = (FloatingActionButton) haushaltsbuchView.findViewById(R.id.haushaltsbuch_fabPayed);
        fabPayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payed();
                customAdapter.notifyDataSetChanged();
            }
        });

        return haushaltsbuchView;
    }

    private void dialogAddExpense() {

        final Dialog myDialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newExpenseView = inflater.inflate(R.layout.haushaltsbuch_dialog_new_expense, null);

        BaseAdapter customDialogAddExpense = new HaushaltsbuchCustomAdapterNewExpense(getActivity());
        listView = (ListView) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_new_expense_listview);
        listView.setAdapter(customDialogAddExpense);

        Button button = (Button) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_new_expense_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ((EditText) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_new_expense_product_name)).getText().toString();
                double amount;
                try {
                    amount = Double.parseDouble(((EditText) newExpenseView.findViewById(R.id.haushaltsbuch_dialog_new_expense_amount)).getText().toString());
                } catch (Exception e) {
                    amount = 0;
                }
                Person boughtBy = wg.getMitbewohner().get(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                HashMap<String, Person> boughtForNewExpense = new HashMap<>(boughtFor);

                HaushaltsbuchAusgabe newExpense = new HaushaltsbuchAusgabe(name, amount, boughtBy, boughtForNewExpense);
                addNewExpense(newExpense, view, myDialog);

                customAdapter.notifyDataSetChanged();
            }
        });

        myDialog.setContentView(newExpenseView);
        myDialog.setTitle("Neue Ausgabe erstellen");
        myDialog.show();

    }

    protected boolean validate(HaushaltsbuchAusgabe expense) {
        boolean valid = true;
        if (TextUtils.isEmpty(expense.getName())) {
            Toast.makeText(getActivity(), "Bitte überprüfe den eingegebenen Namen.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (wg.getHaushaltsbuch().containsKey(expense.getName())) {
            Toast.makeText(getActivity(), "Eine Ausgabe mit dem eingegebenen Namen exisitert bereits!", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (expense.getAmount() == 0.00) {
            Toast.makeText(getActivity(), "Bitte überprüfe den eingegebenen Preis.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (expense.getBoughtFor().size() == 0) {
            Toast.makeText(getActivity(), "Bitte überprüfe für wen diese Ausgabe ist.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (expense.getBoughtFor().size() == 1 && expense.getBoughtFor().containsKey(currentUser.getId())) {
            Toast.makeText(getActivity(), "Du hast nur dich selbst für diese Ausgabe markiert, bitte wähle eine weitere Person aus.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }

    private void addNewExpense(HaushaltsbuchAusgabe newExpense, View view, Dialog myDialog) {
        if (validate(newExpense)) {
            wg = Wohngemeinschaft.getInstance();

            //The current user won*t get money from himself
            if (newExpense.getBoughtFor().containsKey(currentUser.getId())) {
                double currentUsersPart = newExpense.getAmount() - (newExpense.getAmount() / newExpense.getBoughtFor().size());
                newExpense.setAmount(currentUsersPart);
                newExpense.getBoughtFor().remove(currentUser.getId());
            }

            wg.addHaushaltsbuchAusgabe(newExpense);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> postValues = newExpense.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/haushaltsbuch/" + newExpense.getName(), postValues);
            mDatabase.child("wg").child(wg.getName()).updateChildren(childUpdates);

            boughtFor.clear();
            boughtFor = new HashMap<>();

            Toast toast = Toast.makeText(view.getContext(), newExpense.toString(), Toast.LENGTH_SHORT);
            toast.show();

            customAdapter.notifyDataSetChanged();
            myDialog.hide();
        }
    }

    private void payed() {

        wg = Wohngemeinschaft.getInstance();
        ArrayList<HaushaltsbuchAusgabe> holder = new ArrayList<>(wg.getHaushaltsbuch().values());
        ArrayList<Person> payedHolder = new ArrayList<>(payed);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("wg");

        for (Person user : payedHolder) {
            for (HaushaltsbuchAusgabe item : holder) {
                if (item.getBoughtBy().getId().equals(currentUser.getId())) {
                    for (Person boughtFor : item.getBoughtFor().values()) {
                        if (boughtFor.getId().equals(user.getId())) {
                            if (item.getBoughtFor().size() == 1) {
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).setValue(null);
                                wg.getHaushaltsbuch().remove(item.getName());
                            } else {
                                double newAmount = item.getAmount() - (item.getAmount() / item.getBoughtFor().size());
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).child("amount").setValue(newAmount);
                                wg.getHaushaltsbuch().get(item.getName()).setAmount(newAmount);

                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(user.getId(), null);
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).child("boughtFor").updateChildren(childUpdates);

                                HashMap<String, Person> update = new HashMap<>(wg.getHaushaltsbuch().get(item.getName()).getBoughtFor());
                                update.remove(user.getId());
                                wg.getHaushaltsbuch().get(item.getName()).setBoughtFor(update);
                            }
                        }
                    }
                } else if (item.getBoughtBy().getId().equals(user.getId())) {
                    for (Person boughtFor : item.getBoughtFor().values()) {
                        if (boughtFor.getId().equals(currentUser.getId())) {
                            if (item.getBoughtFor().size() == 1) {
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).setValue(null);
                                wg.getHaushaltsbuch().remove(item.getName());
                            } else {
                                double newAmount = item.getAmount() - (item.getAmount() / item.getBoughtFor().size());
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).child("amount").setValue(newAmount);
                                wg.getHaushaltsbuch().get(item.getName()).setAmount(newAmount);

                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put(currentUser.getId(), null);
                                mDatabase.child(wg.getName()).child("haushaltsbuch").child(item.getName()).child("boughtFor").updateChildren(childUpdates);

                                HashMap<String, Person> update = new HashMap<>(wg.getHaushaltsbuch().get(item.getName()).getBoughtFor());
                                update.remove(currentUser.getId());
                                wg.getHaushaltsbuch().get(item.getName()).setBoughtFor(update);
                            }
                        }
                    }
                }
            }
        }

        payed.clear();
        payed = new ArrayList<>();
    }
}

