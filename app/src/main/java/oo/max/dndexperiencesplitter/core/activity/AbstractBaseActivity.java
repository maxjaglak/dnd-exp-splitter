package oo.max.dndexperiencesplitter.core.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.injecting.ApplicationComponent;
import oo.max.dndexperiencesplitter.injecting.Injector;

public class AbstractBaseActivity extends FragmentActivity {

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
}
