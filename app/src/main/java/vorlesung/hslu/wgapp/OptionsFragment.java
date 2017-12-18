package vorlesung.hslu.wgapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OptionsFragment extends Fragment {

    private Wohngemeinschaft wg;
    private View optionsView;
    private ArrayList<String> optionsListUser;
    private ArrayList<String> optionsListWG;
    private Person currentUser;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference("wg");
        wg = Wohngemeinschaft.getInstance();
        currentUser = wg.getMitbewohner().get(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        optionsView = inflater.inflate(R.layout.optionen_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Einstellungen");

        ListView listViewUser = (ListView) optionsView.findViewById(R.id.options_fragment_listview_user);
        ListView listViewWG = (ListView) optionsView.findViewById(R.id.options_fragment_listview_wg);
        optionsListUser = new ArrayList<>();
        optionsListUser.add("Namen ändern");
        optionsListUser.add("eMail ändern");
        optionsListUser.add("Passwort ändern");
        optionsListUser.add("Konto löschen");

        optionsListWG = new ArrayList<>();
        optionsListWG.add("WG umbenennen");
        optionsListWG.add("Aus WG austreten");
        optionsListWG.add("WG löschen");

        ArrayAdapter<String> adapterUser = new ArrayAdapter<>(getActivity(), R.layout.optionen_listview_item, optionsListUser);
        listViewUser.setAdapter(adapterUser);

        ArrayAdapter<String> adapterWG = new ArrayAdapter<>(getActivity(), R.layout.optionen_listview_item, optionsListWG);
        listViewWG.setAdapter(adapterWG);

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (optionsListUser.get(position)) {
                    case "Namen ändern":
                        openDialogUsernameChange();
                        break;
                    case "eMail ändern":
                        openDialogEmailChange();
                        break;
                    case "Passwort ändern":
                        openDialogPasswordChange();
                        break;
                    case "Konto löschen":
                        openDialogDeleteAccount();
                        break;
                }
            }
        });

        listViewWG.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (optionsListWG.get(position)) {
                    case "WG umbenennen":
                        openDialogWGNameChange();
                        break;
                    case "Aus WG austreten":
                        openDialogLeaveWG();
                        break;
                    case "WG löschen":
                        openDialogDeleteWG();
                        break;
                }
            }
        });

        return optionsView;
    }

    private void openDialogUsernameChange() {
        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.optionen_dialog_username_change, null);

        TextView name = (TextView) view.findViewById(R.id.options_dialog_show_user);
        name.setText(currentUser.getName());

        Button btn = (Button) view.findViewById(R.id.options_dialog_change_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = ((TextView) view.findViewById(R.id.options_dialog_change_newname)).getText().toString();
                if (validate(currentUser.getName(), newValue)) {
                    for (Person item : wg.getMitbewohner().values()) {
                        if (item.getName() == newValue) {
                            return;
                        }
                    }

                    mDatabase.child(wg.getName()).child("mitbewohner").child(currentUser.getId()).child("name").setValue(newValue);
                    wg.getMitbewohner().get(currentUser.getId()).setName(newValue);
                    dialog.hide();
                }
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("Namen ändern");
        dialog.show();
    }

    private void openDialogEmailChange() {
        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.optionen_dialog_usermail_change, null);

        TextView email = (TextView) view.findViewById(R.id.options_dialog_show_user);
        email.setText(currentUser.getEmail());


        Button btn = (Button) view.findViewById(R.id.options_dialog_change_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = ((TextView) view.findViewById(R.id.options_dialog_change_newname)).getText().toString();

                if (validate(currentUser.getEmail(), newValue)) {
                    for (Person item : wg.getMitbewohner().values()) {
                        if (item.getEmail() == newValue) {
                            return;
                        }
                    }
                    try {
                        firebaseUser.updateEmail(newValue);
                        mDatabase.child(wg.getName()).child("mitbewohner").child(currentUser.getId()).child("email").setValue(newValue);
                        wg.getMitbewohner().get(currentUser.getId()).setEmail(newValue);
                        dialog.hide();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "E-Mail konnte nicht aktualisiert werden.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("E-Mail ändern");
        dialog.show();
    }

    private void openDialogPasswordChange() {
        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.optionen_dialog_userpassword_change, null);

        Button btn = (Button) view.findViewById(R.id.options_dialog_change_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = ((TextView) view.findViewById(R.id.options_dialog_change_newpw)).getText().toString();
                String newValueCheck = ((TextView) view.findViewById(R.id.options_dialog_change_newpw_again)).getText().toString();

                if (validatePw(newValue, newValueCheck)) {
                    try {
                        firebaseUser.updatePassword(newValue);
                        dialog.hide();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Das Passwort konnte nicht aktualisiert werden.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("Passwort ändern");
        dialog.show();
    }

    private void openDialogWGNameChange() {
        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.optionen_dialog_wgname_change, null);

        TextView name = (TextView) view.findViewById(R.id.options_dialog_show_user);
        name.setText(wg.getName());

        Button btn = (Button) view.findViewById(R.id.options_dialog_change_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newValue = ((TextView) view.findViewById(R.id.options_dialog_change_newname)).getText().toString();

                if (validate(wg.getName(), newValue)) {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Checken ob WG Name nicht bereits belegt ist
                            Iterable<DataSnapshot> wohniter = dataSnapshot.getChildren();
                            for (DataSnapshot snap : wohniter) {
                                String currentWG = snap.getKey().toString();
                                if (currentWG.equals(newValue)) {
                                    Toast.makeText(getActivity(), "Diesen WG Namen gibt es bereits.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            mDatabase.child(wg.getName()).setValue(null);
                            wg.setName(newValue);
                            mDatabase.child(wg.getName()).setValue(wg);
                            Toast.makeText(getActivity(), "Der WG Name wurde erfolgreich in" + newValue + "geändert!", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    dialog.hide();
                }
            }
        });

        dialog.setContentView(view);
        dialog.setTitle("WG Namen ändern");
        dialog.show();
    }

    private void openDialogLeaveWG() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Möchtest du die WG wirklich verlassen?");

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                wg.getMitbewohner().remove(currentUser.getId());
                mDatabase.child(wg.getName()).child("mitbewohner").child(currentUser.getId()).setValue(null);

                if (wg.getMitbewohner().size() == 0) {
                    mDatabase.child(wg.getName()).setValue(null);
                }

                Wohngemeinschaft.setInstance(null);
                wg = Wohngemeinschaft.getInstance();

                Intent enterWG = new Intent(getActivity(), ActivityEnterWG.class);
                getActivity().finish();
                enterWG.putExtra("personName", currentUser.getName());
                startActivity(enterWG);
            }
        })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    private void openDialogDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Möchtest du die WG wirklich verlassen?");

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                wg.getMitbewohner().remove(currentUser.getId());
                mDatabase.child(wg.getName()).child("mitbewohner").child(currentUser.getId()).setValue(null);
                Wohngemeinschaft.setInstance(null);

                Intent login = new Intent(getActivity(), ActivityLogin.class);
                getActivity().finish();
                startActivity(login);
            }
        })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();

    }

    private void openDialogDeleteWG() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("WG löschen");
        builder.setMessage("Um die WG zu löschen müssen alle Mitbewohner aus der WG austreten oder Ihren Account löschen.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    public boolean validate(String oldValue, String newValue) {
        boolean valid = false;

        if (oldValue != null && newValue != null && oldValue != newValue) {
            valid = true;
        }

        return valid;
    }

    public boolean validatePw(String newValue, String newValueCheck) {
        boolean valid = false;

        if (newValue != null && newValueCheck != null && newValue == newValueCheck) {
            valid = true;
        }

        return valid;
    }

}
