package oo.max.dndexperiencesplitter.core.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.app.Logger;
import oo.max.dndexperiencesplitter.injecting.ApplicationComponent;
import oo.max.dndexperiencesplitter.injecting.Injector;

public class AbstractBaseActivity extends FragmentActivity {

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationComponent applicationComponent = ExpApplication.getApp(this).getInjector().getApplicationComponent();
        Injector.invokeInjection(this, applicationComponent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            eventBus.register(this);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            eventBus.unregister(this);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }
}
