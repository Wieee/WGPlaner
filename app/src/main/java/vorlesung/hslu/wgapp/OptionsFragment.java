package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OptionsFragment extends Fragment {

    Wohngemeinschaft wg;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        wg = Wohngemeinschaft.getInstance();

        final View optionsView = inflater.inflate(R.layout.optionen_fragment, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Einstellungen");

        return optionsView;
    }

}
