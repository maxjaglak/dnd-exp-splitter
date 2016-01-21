package oo.max.dndexperiencesplitter.launch.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.launch.action.InitializeInjectorAction;

public class SplashScreenActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        runInitAction();
    }

    private void runInitAction() {
        InitializeInjectorAction action = new InitializeInjectorAction(this);
        action.init();
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}