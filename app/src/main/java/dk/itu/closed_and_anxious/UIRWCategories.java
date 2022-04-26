package dk.itu.closed_and_anxious;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UIRWCategories extends Fragment {

    // let's make an ArrayList of Categories for our RecycleView

    ArrayList<Category> categories;

    public UIRWCategories() {
        // Required empty public constructor
    }

    public static UIRWCategories newInstance() {
        UIRWCategories fragment = new UIRWCategories();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // let's instantiate our List for the recycleview
        categories = new ArrayList<Category>();
        // let's instantiate the RV!
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.ui_rw_categories, container, false);

        categories.add(new Category("Anxiety", getString(R.string.anx_descr), R.drawable.anxious));
        categories.add(new Category("Noise", getString(R.string.descr_noise), R.drawable.noise));
        categories.add(new Category("Frustration", getString(R.string.descr_frustration), R.drawable.frustration));

        // let's set up the RecyclerView
        RecyclerView catList = v.findViewById(R.id.cat_recyclerView);
        catList.setLayoutManager(new LinearLayoutManager(getActivity()));
        CategoryAdapter mAdapter = new CategoryAdapter();
        catList.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return v;

    }


    private class CategoryHolder extends RecyclerView.ViewHolder {
        private final TextView header, description;
        private final ImageView img;

        public CategoryHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.cat_header);
            description = itemView.findViewById(R.id.cat_descr);
            img = itemView.findViewById(R.id.cat_img);
        }

        public void bind(Category cat, int position) {
            header.setText(cat.getdName());
            description.setText(cat.getDescription());
            img.setImageResource(cat.getImageKey());
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
            View v = layoutInflater.inflate(R.layout.cat_row, parent, false);
            return new CategoryHolder(v);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category cat = categories.get(position);
            holder.bind(cat, position);
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

}