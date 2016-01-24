package oo.max.dndexperiencesplitter.category.action;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import oo.max.dndexperiencesplitter.category.adapter.CategoryAdapter;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.category.model.Category;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LoadCategoryActionTest {

    @Mock
    CategoryDao categoryDao;

    @Mock
    RecyclerView recyclerView;

    private LoadCategoryAction loadCategoryAction;

    @Before
    public void setup() {
        loadCategoryAction = new LoadCategoryAction(categoryDao);
    }

    @Test
    public void shouldSetCategoriesFromDaoToTargetRecycleView() {
        //given
        final Category category = Category.builder()
                .name("some category")
                .value(1)
                .build();

        Mockito.when(categoryDao.get()).thenReturn(ImmutableList.of(category));

        //when
        loadCategoryAction.load(recyclerView);
        loadCategoryAction.loadInBackground();
        loadCategoryAction.after();

        //then
        Mockito.verify(recyclerView, times(1)).setAdapter(Matchers.argThat(new ArgumentMatcher<RecyclerView.Adapter>() {
            @Override
            public boolean matches(Object argument) {
                CategoryAdapter categoryAdapter = (CategoryAdapter) argument;
                return categoryAdapter.getCategories().get(0).equals(category);
            }
        }));

        Mockito.verify(recyclerView, times(1)).setLayoutManager(any(LinearLayoutManager.class));
    }

}