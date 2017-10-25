package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_einkaufszettel extends Fragment {
    View einkaufszettelview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        einkaufszettelview = inflater.inflate(R.layout.fragment_einkaufszettel, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(
                "Einkaufszettel");
        return einkaufszettelview;
    }
}
