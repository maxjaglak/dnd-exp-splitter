package oo.max.dndexperiencesplitter.core.action;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

public abstract class AbstractLoadAction {

    protected LoadAsyncTask task;
    protected RecyclerView target;

    public void load(RecyclerView target) {
        this.target = target;
        task = new LoadAsyncTask();
        task.execute();
    }

    public abstract void loadInBackground();

    public abstract void after();

    public class LoadAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            loadInBackground();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            after();
        }

    }
}
