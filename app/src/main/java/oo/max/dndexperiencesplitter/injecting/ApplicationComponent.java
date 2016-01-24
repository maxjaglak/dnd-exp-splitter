package oo.max.dndexperiencesplitter.injecting;

import javax.inject.Singleton;

import dagger.Component;
import oo.max.dndexperiencesplitter.category.AddCategoryFragment;
import oo.max.dndexperiencesplitter.category.activity.CategoryListActivity;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.menu.activity.MainActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;
import oo.max.dndexperiencesplitter.player.dialog.AddPlayerFragment;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(AbstractBaseActivity abstractBaseActivity);

    void inject(PlayerListActivity playerListActivity);

    void inject(AddPlayerFragment addPlayerFragment);

    void inject(AddCategoryFragment addCategoryFragment);
    void inject(CategoryListActivity categoryListActivity);
}
