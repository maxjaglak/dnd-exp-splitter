package oo.max.dndexperiencesplitter.category.action;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import javax.inject.Inject;

import oo.max.dndexperiencesplitter.category.adapter.CategoryViewHolder;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;

public class CategorySwipeToDeleteAction extends ItemTouchHelper.Callback {

    private final CategoryDao categoryDao;

    @Inject
    public CategorySwipeToDeleteAction(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END | ItemTouchHelper.START;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
        categoryDao.remove(((CategoryViewHolder) viewHolder).getCategory());
    }
}
