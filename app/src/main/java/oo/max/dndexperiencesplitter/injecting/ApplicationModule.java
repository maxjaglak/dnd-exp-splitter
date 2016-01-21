package oo.max.dndexperiencesplitter.injecting;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;

@Module
public class ApplicationModule {

    private final Context context;
    private final DatabaseHelper databaseHelper;

    public ApplicationModule(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper() {
        return databaseHelper;
    }

}
