package com.example.dogapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.databinding.DogsItemBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> implements Filterable {

    private static ArrayList<DogBreed> dogBreeds;
    private static ArrayList<DogBreed> dogBreedsCopy;

    public DogsAdapter(ArrayList<DogBreed> dogBreeds) {
        DogsAdapter.dogBreeds = dogBreeds;
        DogsAdapter.dogBreedsCopy = dogBreeds;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String input = charSequence.toString().toLowerCase();
                List<DogBreed> filteredDogs = new ArrayList<DogBreed>();
                if(input.isEmpty()){
                    filteredDogs.addAll(dogBreedsCopy);
                }else {
                    for (DogBreed dog : dogBreedsCopy) {
                        if (dog.getName().toLowerCase().contains(input)) {
                            filteredDogs.add(dog);
                        }
                    }
                }
                FilterResults filteredResults = new FilterResults();
                filteredResults.values = filteredDogs;
                return filteredResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogBreeds = new ArrayList<>();
                dogBreeds.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public DogsItemBinding binding;

        public ViewHolder(DogsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            // Define click listener for the ViewHolder's View
            this.binding = itemBinding;

            binding.dogName.setOnClickListener(view1 -> {
                DogBreed dog = dogBreeds.get(getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putSerializable("dogBreed", dog);
                Navigation.findNavController(view1).navigate(R.id.detailFragment, bundle);
            });

            itemView.setOnTouchListener(new OnSwipeTouchListener(){
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    if (binding.layout1.getVisibility() == View.GONE){
                        binding.layout1.setVisibility(View.VISIBLE);
                        binding.layout2.setVisibility(View.GONE);
                }
                    else{
                        binding.layout2.setVisibility(View.VISIBLE);
                        binding.layout1.setVisibility(View.GONE);
                    }
                    Log.d("DEBUG", "Swiped left");
                }
            });
        }



    }

    @NonNull
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dogs_item, parent, false);
        DogsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dogs_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setDog(dogBreeds.get(position));
        Picasso.get().load(dogBreeds.get(position).getUrl()).into(holder.binding.dogImg);
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public class OnSwipeTouchListener implements View.OnTouchListener{

        private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }


        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 50;
            private static final int SWIPE_VELOCITY_THRESHOLD = 50;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick();
                return true;
            }



            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }

        public void onClick() {
        }

        public void onLongPress() {
        }
    }
}
