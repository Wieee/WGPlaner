package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_haushaltsbuch extends Fragment {
    View haushaltsbuchview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        haushaltsbuchview = inflater.inflate(R.layout.fragment_haushaltsbuch, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(
                "Haushaltsbuch");
        return haushaltsbuchview;
    }


}
