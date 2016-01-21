package oo.max.dndexperiencesplitter.launch.action;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.menu.activity.MainActivity;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class InitializeInjectorActionTest {

    @Mock
    Context context;

    @Mock
    ExpApplication expApplication;

    private InitializeInjectorAction initializeInjectorAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        initializeInjectorAction = new InitializeInjectorAction(context);

        when(context.getApplicationContext()).thenReturn(expApplication);
    }

    @Test
    public void shouldInitApplicationClassWhenRunningInitializing() {
        //when
        initializeInjectorAction.init();
        initializeInjectorAction.initAsyncTask.doInBackground(null);

        //then
        Mockito.verify(expApplication, times(1)).init();
    }

    @Test
    public void shouldStartMainActivityAfterActionIsExecuted() {
        //when
        initializeInjectorAction.after();

        //then
        Mockito.verify(context, times(1)).startActivity(argThat(new ArgumentMatcher<Intent>() {
            @Override
            public boolean matches(Object argument) {
                Intent intent = (Intent) argument;
                return intent.getComponent().getClassName().equals(MainActivity.class.getName())
                        && (intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TASK) != 0
                        && (intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) != 0;

            }
        }));
    }

}