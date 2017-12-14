package vorlesung.hslu.wgapp.test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;


import android.content.Context;
import android.support.v4.BuildConfig;


import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.Robolectric.*;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import vorlesung.hslu.wgapp.ActivityMain;
import vorlesung.hslu.wgapp.EinkaufszettelFragment;
import vorlesung.hslu.wgapp.EinkaufszettelProdukt;
import vorlesung.hslu.wgapp.R;
import vorlesung.hslu.wgapp.Wohngemeinschaft;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by D064744 on 13.12.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21,
manifest = "src/main/AndroidManifest.xml", packageName = "vorlesung.hslu.wgapp")
public class EinkaufszettelFragmentTest {

    private ActivityMain mActivity = null;

    private FragmentManager fragmentManager;
    private  EinkaufszettelFragment fragmenttoTest;



    @Before
    public void setUp() throws Exception {



        mActivity = Mockito.mock(ActivityMain.class);
        Context context = Mockito.mock(Context.class);
        FirebaseApp.initializeApp(context);
         fragmentManager = mActivity.getFragmentManager();
       fragmenttoTest = new EinkaufszettelFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmenttoTest);
        transaction.commitNow();
    }


    @Test
  /*+  public void addItem() throws Exception {
        EinkaufszettelProdukt produkt = new EinkaufszettelProdukt("TestProdukt", 5, "täglich");
        EinkaufszettelFragment spy = Mockito.spy(fragmenttoTest);
        Wohngemeinschaft mock = Mockito.mock(Wohngemeinschaft.class);
        spy.wg.setName("Mannheim");
        Mockito.when(mock.getName()).thenReturn("Mannheim");
        spy.addItem(produkt);
        verify(mock,times(1));
        assertEquals(spy.einkaufsListe.get(0),produkt);
    }**/


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }


}