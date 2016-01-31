package oo.max.dndexperiencesplitter.expsplitting.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.ImmutableList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.model.Category;

public class CategoryView extends LinearLayout {

    @Bind(R.id.category_name)
    TextView categoryName;

    @Bind(R.id.star1)
    ImageView star1;

    @Bind(R.id.star2)
    ImageView star2;

    @Bind(R.id.star3)
    ImageView star3;

    @Bind(R.id.star4)
    ImageView star4;

    @Bind(R.id.star5)
    ImageView star5;

    private List<ImageView> stars;

    @Getter
    private int currentValue = 0;

    @Getter
    private Category category;

    @Setter
    private OnCategoryValuePicked onCategoryValuePicked;

    public CategoryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_splitting_category, this, true);
        ButterKnife.bind(this, view);
        stars = ImmutableList.of(star1, star2, star3, star4, star5);
    }

    public void setCategory(Category category) {
        this.category = category;
        categoryName.setText(category.getName());
    }

    @OnClick(R.id.star1)
    public void start1Clicked() {
        startClicked(1);
    }

    @OnClick(R.id.star2)
    public void start2Clicked() {
        startClicked(2);
    }

    @OnClick(R.id.star3)
    public void start3Clicked() {
        startClicked(3);
    }

    @OnClick(R.id.star4)
    public void start4Clicked() {
        startClicked(4);
    }

    @OnClick(R.id.star5)
    public void start5Clicked() {
        startClicked(5);
    }

    protected void startClicked(int i) {
        if(currentValue == i) {
            currentValue--;
        } else {
            currentValue = i;
        }

        updateStarsDrawables();

        if(onCategoryValuePicked != null) {
            onCategoryValuePicked.onCategoryValuePicked(category, currentValue);
        }
    }

    public void reset() {
        setValue(0);
    }

    public void setValue(int value) {
        currentValue = value;
        updateStarsDrawables();
    }

    private void updateStarsDrawables() {
        for (int i=1; i<= stars.size(); i++) {
            if(i <= currentValue) {
                stars.get(i-1).setImageResource(R.drawable.star_on);
            } else {
                stars.get(i-1).setImageResource(R.drawable.star_off);
            }
        }
    }

    public interface OnCategoryValuePicked {
        void onCategoryValuePicked(Category category, int value);
    }

}
