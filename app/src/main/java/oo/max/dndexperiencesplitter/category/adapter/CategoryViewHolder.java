package oo.max.dndexperiencesplitter.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.model.Category;

@Getter
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.value)
    TextView value;

    @Setter
    private Category category;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
