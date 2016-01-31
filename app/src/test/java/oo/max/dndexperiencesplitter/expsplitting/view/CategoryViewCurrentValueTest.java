package oo.max.dndexperiencesplitter.expsplitting.view;

import com.google.common.collect.ImmutableList;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(ParameterizedRobolectricTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml", sdk = 21)
public class CategoryViewCurrentValueTest {

    @ParameterizedRobolectricTestRunner.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][] {
                {ImmutableList.of(), 0},
                {ImmutableList.of(1), 1} ,
                {ImmutableList.of(2), 2} ,
                {ImmutableList.of(3), 3} ,
                {ImmutableList.of(4), 4} ,
                {ImmutableList.of(5), 5} ,
                {ImmutableList.of(5, 5), 4} ,
                {ImmutableList.of(1, 1), 0} ,
                {ImmutableList.of(1, 4), 4} ,
                {ImmutableList.of(4, 2), 2} ,
        });
    }

    private CategoryView categoryView;

    private List<Integer> clickedStars;
    private int expectedValue;

    public CategoryViewCurrentValueTest(List<Integer> clickedStars, int expectedValue) {
        this.clickedStars = clickedStars;
        this.expectedValue = expectedValue;
    }

    @Before
    public void setup() {
        categoryView = new CategoryView(RuntimeEnvironment.application);
    }

    @Test
    public void shouldValueBeCorrectAfterClickingStart() {
        //when
        for (Integer clickedStar : clickedStars) {
            categoryView.startClicked(clickedStar);
        }

        //then
        Assertions.assertThat(categoryView.getCurrentValue()).isEqualTo(expectedValue);
    }
}