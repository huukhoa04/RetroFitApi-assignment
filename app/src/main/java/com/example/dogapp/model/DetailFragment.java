package com.example.dogapp.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.R;
import com.example.dogapp.databinding.FragmentDetailBinding;
import com.example.dogapp.viewmodel.DogsApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DetailFragment extends Fragment {


    private DogBreed dogBreed;
    private FragmentDetailBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get serializable object
            dogBreed = (DogBreed) getArguments().getSerializable("dogBreed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //data binding for 2 ways
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, null, false);
        View viewRoot = binding.getRoot();
        binding.setDog(dogBreed);
        Picasso.get().load(dogBreed.getUrl()).into(binding.dogImg1);
        return viewRoot;
    }


}