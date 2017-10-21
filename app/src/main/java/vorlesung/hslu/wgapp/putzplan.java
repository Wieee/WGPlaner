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

public class putzplan extends Fragment {
   View putzplanview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        putzplanview = inflater.inflate(R.layout.putzplan, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(
                "Putzplan");
        return putzplanview;
    }
}