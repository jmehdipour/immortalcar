package ir.jmehdipour.immortalcar.Categories;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.jmehdipour.immortalcar.R;
import ir.jmehdipour.immortalcar.data.model.Car;
import ir.jmehdipour.immortalcar.list.CarListActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private String[] categories;

    public CategoryAdapter(String[] categories) {
        this.categories = categories;
    }

    public void setCategories(String[] categories) {

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.bindCategory(categories[i]);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTitle;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.tv_categoryItem_title);
        }

        public void bindCategory(String title){
            categoryTitle.setText(title);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), CarListActivity.class);
                intent.putExtra(CarListActivity.EXTRA_KEY_CATEGORY, title);
                intent.putExtra(CarListActivity.EXTRA_KEY_CATEGORY_TYPE, modelOrBrand(title));
                intent.putExtra(CarListActivity.EXTRA_KEY_TITLE, title + " cars");
                itemView.getContext().startActivity(intent);
            });
        }

        public String modelOrBrand(String category){
            try {
                Integer.parseInt(category);
                return Car.ON_MODELS;
            }catch (NumberFormatException | NullPointerException nfe){
                return Car.ON_BRANDS;
            }

        }


    }


}
