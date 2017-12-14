package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    FragmentTransaction transaction;
    android.app.FragmentManager fragmentManager;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View homeView= inflater.inflate(R.layout.home_screen, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Home");

        Button einkaufszettel = (Button) homeView.findViewById(R.id.home_screen_btn1);
        Button haushaltsbuch = (Button) homeView.findViewById(R.id.home_screen_btn2);
        Button putzplan = (Button) homeView.findViewById(R.id.home_screen_btn3);
        Button option = (Button) homeView.findViewById(R.id.home_screen_btn4);

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        einkaufszettel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, new EinkaufszettelFragment());
                transaction.addToBackStack("einkaufszettel_fragment");
                transaction.commit();
            }
        });

        haushaltsbuch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, new HaushaltsbuchFragment());
                transaction.addToBackStack("haushaltsbuch_fragment");
                transaction.commit();
            }
        });

        putzplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, new PutzplanFragment());
                transaction.addToBackStack("putzplan_fragment");
                transaction.commit();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, new OptionsFragment());
                transaction.addToBackStack("options_fragment");
                transaction.commit();
            }
        });
        return homeView;
    }
}
