package vorlesung.hslu.wgapp;

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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by D064744 on 13.12.2017.
 */
public class EinkaufszettelFragmentTest {


    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<ActivityMain>(ActivityMain.class);


    private ActivityMain mActivity = null;
    private EinkaufszettelFragment fragmenttoTest = new EinkaufszettelFragment();
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        fragmentManager = mActivity.getFragmentManager();
         transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmenttoTest);
        transaction.commitNow();
    }


    @Test
    @UiThreadTest
    public void TestAddAndDelete() throws Exception {
        EinkaufszettelProdukt produkt = new EinkaufszettelProdukt("TestProdukt", 5);
        EinkaufszettelFragment spy = Mockito.spy(fragmenttoTest);
        Wohngemeinschaft mock = Mockito.mock(Wohngemeinschaft.class);
        spy.wg.setName("Mannheim");
        spy.wg.addEinkaufszettelProdukt(produkt);


        // Test add Item)
        spy.addItem(produkt);
        EinkaufszettelFragment.gekaufteListe.put(produkt.getName(),produkt);
        //Test delete Item
        spy.deleteItems();
        assertFalse(mock.getEinkaufszettel().containsValue(produkt));


    }
    @After
    public void validate() {
        validateMockitoUsage();
    }



}