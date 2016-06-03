package oo.max.dndexperiencesplitter.launch.action;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.app.Logger;
import oo.max.dndexperiencesplitter.menu.activity.MainActivity;

public class InitializeInjectorAction {

    private final long SPLASH_SCREEN_DELAY_MILLIS = 800;

    private final Context context;

    InitAsyncTask initAsyncTask;

    public InitializeInjectorAction(Context context) {
        this.context = context;
    }

    public void init() {
        initAsyncTask = new InitAsyncTask();
        initAsyncTask.execute();
    }

    private void initApp() {
        ExpApplication.getApp(context).init();
    }

    protected void after() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    class InitAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                initApp();
                Thread.sleep(SPLASH_SCREEN_DELAY_MILLIS);
            } catch (InterruptedException e) {
                Logger.error(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            after();
        }
    }
}
