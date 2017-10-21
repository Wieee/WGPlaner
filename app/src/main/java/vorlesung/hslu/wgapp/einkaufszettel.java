package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by D064744 on 20.10.2017.
 */

public class einkaufszettel extends Fragment {
    View einkaufszettelview ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       einkaufszettelview = inflater.inflate(R.layout.einkaufszettel, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(
                "Einkaufszettel");
        return einkaufszettelview;
    }
}
