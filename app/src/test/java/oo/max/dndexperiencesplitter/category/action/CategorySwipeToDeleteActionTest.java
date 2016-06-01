package oo.max.dndexperiencesplitter.category.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.greenrobot.event.EventBus;
import oo.max.dndexperiencesplitter.category.adapter.CategoryViewHolder;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.category.model.Category;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategorySwipeToDeleteActionTest {

    @Mock
    CategoryDao categoryDao;

    @Mock
    CategoryViewHolder categoryViewHolder;

    @Mock
    EventBus eventBus;

    private CategorySwipeToDeleteAction categorySwipeToDeleteAction;

    @Before
    public void setup() {
        categorySwipeToDeleteAction = new CategorySwipeToDeleteAction(categoryDao, eventBus);
    }

    @Test
    public void shouldDeleteCategoryOnSwipeAction() {
        //given
        Category category = new Category();

        when(categoryViewHolder.getCategory()).thenReturn(category);

        //when
        categorySwipeToDeleteAction.onSwiped(categoryViewHolder, 0);

        //then
        verify(categoryDao, times(1)).remove(eq(category));
    }

}