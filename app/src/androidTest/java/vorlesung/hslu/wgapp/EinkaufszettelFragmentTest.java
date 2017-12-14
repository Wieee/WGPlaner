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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Created by D064744 on 13.12.2017.
 */
public class EinkaufszettelFragmentTest {


    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<ActivityMain>(ActivityMain.class);
    private ActivityMain mActivity = null;
    private EinkaufszettelFragment fragmenttoTest = new EinkaufszettelFragment();
    private FragmentManager fragmentManager;


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        fragmentManager = mActivity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmenttoTest);
        transaction.commitNow();
    }
    @Test
    public void testLaunch() throws Exception {
        //test if Fragment is launched or not
        View view = fragmenttoTest.getView().findViewById(R.id.einkaufszettel_ListView);
        assertNotNull(view);
    }

    @Test
    @UiThreadTest
    public void addItem() throws Exception {
        EinkaufszettelProdukt produkt = new EinkaufszettelProdukt("TestProdukt",5,"täglich");
        fragmenttoTest.addItem(produkt);
        assertTrue(fragmenttoTest.einkaufsListe.contains(produkt));
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }


}