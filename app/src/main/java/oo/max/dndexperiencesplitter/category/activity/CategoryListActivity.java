package oo.max.dndexperiencesplitter.category.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.AddCategoryFragment;
import oo.max.dndexperiencesplitter.category.action.CategorySwipeToDeleteAction;
import oo.max.dndexperiencesplitter.category.action.LoadCategoryAction;
import oo.max.dndexperiencesplitter.category.event.CategoryUpdatedEvent;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;

public class CategoryListActivity extends AbstractBaseActivity {

    @Bind(R.id.categories)
    RecyclerView categoriesRecycleView;

    @Bind(R.id.add_category)
    FloatingActionButton addCategoryButton;

    @Inject
    LoadCategoryAction loadCategoryAction;

    @Inject
    CategorySwipeToDeleteAction categorySwipeToDeleteAction;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        loadCategoryAction.load(categoriesRecycleView);
        itemTouchHelper = new ItemTouchHelper(categorySwipeToDeleteAction);
        itemTouchHelper.attachToRecyclerView(categoriesRecycleView);
    }

    @OnClick(R.id.add_category)
    public void addCategory() {
        AddCategoryFragment.show(getSupportFragmentManager());
    }

    public void onEventMainThread(CategoryUpdatedEvent event) {
        loadCategoryAction.load(categoriesRecycleView);
    }

}