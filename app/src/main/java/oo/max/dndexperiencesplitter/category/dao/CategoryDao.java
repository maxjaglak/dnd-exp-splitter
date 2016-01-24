package oo.max.dndexperiencesplitter.category.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;

@Singleton
public class CategoryDao extends AbstractDao<Category, String>{

    @Inject
    public CategoryDao(DatabaseHelper databaseHelper) {
        super(databaseHelper, Category.class);
    }

    @Override
    protected String getId(Category item) {
        return item.getName();
    }
}
