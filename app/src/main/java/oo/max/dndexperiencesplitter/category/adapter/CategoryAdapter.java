package oo.max.dndexperiencesplitter.category.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final Context context;

    @Getter
    private final List<Category> categories;

    private final LayoutInflater inflater;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = new ArrayList<>(categories);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.value.setText(String.valueOf(category.getValue()));
        holder.setCategory(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
