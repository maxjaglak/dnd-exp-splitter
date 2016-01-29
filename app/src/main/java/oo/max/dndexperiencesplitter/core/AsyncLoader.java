package oo.max.dndexperiencesplitter.core;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncLoader<T> extends AsyncTaskLoader<T> {

    public AsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public T loadInBackground() {
        return load();
    }

    protected abstract T load();
}
