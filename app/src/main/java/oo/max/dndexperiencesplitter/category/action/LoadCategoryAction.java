package oo.max.dndexperiencesplitter.category.action;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import oo.max.dndexperiencesplitter.category.adapter.CategoryAdapter;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.core.action.AbstractLoadAction;

public class LoadCategoryAction extends AbstractLoadAction {

    private final CategoryDao categoryDao;
    private List<Category> categories;

    @Inject
    public LoadCategoryAction(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void loadInBackground() {
        this.categories = categoryDao.get();
    }

    @Override
    public void after() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(target.getContext(), categories);
        target.setAdapter(categoryAdapter);
        target.setLayoutManager(new LinearLayoutManager(target.getContext()));
    }
}
