package vorlesung.hslu.wgapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by D064744 on 17.12.2017.
 * Problem with addItem because I cannot avoid methodcalls of other classes.

public class PutzplanFragmentTest {

    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<ActivityMain>(ActivityMain.class);


    private ActivityMain mActivity = null;
    private PutzplanFragment fragmenttoTest = new PutzplanFragment();
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


    @Test
    @UiThreadTest
    public void TestAddAndDelete() throws Exception {
        Person firstCleaner = new Person("PutzplanCleaner","cleaner@test.de","testId123");
        PutzplanAufgabe task = new PutzplanAufgabe("TestAufgabe","täglich",firstCleaner);
        PutzplanFragment spy = Mockito.spy(fragmenttoTest);
        Wohngemeinschaft mock = Mockito.mock(Wohngemeinschaft.class);
        spy.wg.setName("Mannheim");
        Mockito.when(mock.getName()).thenReturn("Mannheim");


            // Test add Item
        spy.addItem(task);

        assertEquals(spy.putzliste.get(0),task);
       PutzplanFragment.geputzteListe.add(task);
        //Test delete Item
        spy.removeItem(0);
        assertFalse(spy.putzliste.contains(task));
    }

    @After
    public void tearDown() throws Exception {


    }


} **/