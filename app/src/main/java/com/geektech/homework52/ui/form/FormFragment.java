package com.geektech.homework52.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.homework52.App;
import com.geektech.homework52.R;
import com.geektech.homework52.data.models.Post;
import com.geektech.homework52.databinding.FragmentFormBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    private static final int userId = 10;
    private static final int groupId = 41;
    private Post post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public FormFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundle();
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                if (getArguments() != null){
                    updatePost(title, content);
                }else {
                    createPost(title, content);
                }

            }
        });
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (getArguments() != null){
            post = (Post) bundle.getSerializable("post");
            binding.etContent.setText(post.getContent());
            binding.etTitle.setText(post.getTitle());
        }
    }
    private void createPost(String title, String content){
        Post post = new Post(title, content, userId, groupId);

        App.api.createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    requireActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void updatePost(String title, String content){
        post.setContent(content);
        post.setTitle(title);
        App.api.updatePost(post.getId(), post).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    requireActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}