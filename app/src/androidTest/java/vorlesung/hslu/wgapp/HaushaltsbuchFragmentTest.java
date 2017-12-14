package vorlesung.hslu.wgapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by D064744 on 14.12.2017.
 */
public class HaushaltsbuchFragmentTest {
      @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<ActivityMain>(ActivityMain.class);


    private ActivityMain mActivity = null;
    private HaushaltsbuchFragment fragmenttoTest = new HaushaltsbuchFragment();
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        fragmentManager = mActivity.getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmenttoTest);
        transaction.commitNow();
    }

/**
    @Test
    @UiThreadTest
    public void validate() throws Exception {
        Person boughtby = new Person();
        boughtby.setId("TestID");
        Person boughtFor = new Person();
        HashMap<String,Person> hash = new HashMap<>();
        hash.put(boughtFor.getName(),boughtFor);
        boughtFor.setName("BoughtForTest");
        HaushaltsbuchAusgabe expense = new HaushaltsbuchAusgabe("Expense from Test",5 , boughtby, hash);
       HaushaltsbuchFragment mockfrag = Mockito.mock(HaushaltsbuchFragment.class);
        Wohngemeinschaft mock = Mockito.mock(Wohngemeinschaft.class);
        mockfrag.currentUser = boughtby;

         Mockito.when(mock.getName()).thenReturn("Mannheim");
        assertFalse(mockfrag.validate(expense));

    } **/

    @After
    public void tearDown() throws Exception {


    }


}