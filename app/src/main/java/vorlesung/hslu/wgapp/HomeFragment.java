package vorlesung.hslu.wgapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View homeView= inflater.inflate(R.layout.home_screen, container, false);
        ((ActivityMain) getActivity()).getSupportActionBar().setTitle("Home");

        return homeView;
    }
}
