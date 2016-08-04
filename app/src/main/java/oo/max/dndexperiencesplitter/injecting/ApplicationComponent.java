package oo.max.dndexperiencesplitter.injecting;

import javax.inject.Singleton;

import dagger.Component;
import oo.max.dndexperiencesplitter.category.AddCategoryFragment;
import oo.max.dndexperiencesplitter.category.activity.CategoryListActivity;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.expsplitting.activity.MaxExpActivity;
import oo.max.dndexperiencesplitter.expsplitting.activity.ResultPreviewActivity;
import oo.max.dndexperiencesplitter.expsplitting.activity.SurveyActivity;
import oo.max.dndexperiencesplitter.expsplitting.view.AssessPlayerView;
import oo.max.dndexperiencesplitter.history.activity.HistoryResultsActivity;
import oo.max.dndexperiencesplitter.license.LicenseActivity;
import oo.max.dndexperiencesplitter.menu.activity.MainActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;
import oo.max.dndexperiencesplitter.player.dialog.AddPlayerFragment;
import oo.max.dndexperiencesplitter.playerpicking.activity.PickPlayersActivity;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(AbstractBaseActivity abstractBaseActivity);

    void inject(PlayerListActivity playerListActivity);

    void inject(AddPlayerFragment addPlayerFragment);

    void inject(AddCategoryFragment addCategoryFragment);
    void inject(CategoryListActivity categoryListActivity);
    void inject(PickPlayersActivity pickPlayersActivity);
    void inject(MaxExpActivity maxExpActivity);
    void inject(SurveyActivity surveyActivity);

    void inject(AssessPlayerView assessPlayerView);
    void inject(ResultPreviewActivity resultPreviewActivity);
    void inject(HistoryResultsActivity historyResultsActivity);
    void inject(LicenseActivity licenseActivity);

}
