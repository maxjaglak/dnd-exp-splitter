package oo.max.dndexperiencesplitter.injecting;

import dagger.Component;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.menu.activity.MainActivity;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(AbstractBaseActivity abstractBaseActivity);

}
