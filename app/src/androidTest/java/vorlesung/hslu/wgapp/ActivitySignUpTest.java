package vorlesung.hslu.wgapp;


import android.os.Looper;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by D064744 on 14.12.2017.
 */

public class ActivitySignUpTest {
    @Rule
    public ActivityTestRule<ActivitySignUp> rule  = new ActivityTestRule<>(ActivitySignUp.class);
    private ActivitySignUp mActivity;


    @Before
    public void setUp() throws Exception {
      mActivity = rule.getActivity();
    }
    @Test
    @UiThreadTest
    public void noNameValuesignUp() throws Exception {

        assertFalse(mActivity.signUp(null,"hans@email.de","secretpasswort123"));

    }
    @Test
    @UiThreadTest
    public void noEmailValuesignUp() throws Exception {
        assertFalse(mActivity.signUp("Hans","","secretpasswort123"));
    }

    @Test
    @UiThreadTest
    public void noPasswortFormatValuesignUp() throws Exception {
        assertFalse(mActivity.signUp("Hans","hansemail@email.de",""));
    }

    @Test
    public void goodFormatValuesignUp() throws Exception {
        assertTrue(mActivity.signUp("Hans","hansemail@email.de","secretpasswort123"));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }


}