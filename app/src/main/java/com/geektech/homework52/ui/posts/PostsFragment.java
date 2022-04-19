package com.geektech.homework52.ui.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.geektech.homework52.App;
import com.geektech.homework52.R;
import com.geektech.homework52.data.models.Post;
import com.geektech.homework52.data.remote.OnClick;
import com.geektech.homework52.data.remote.PostApiClient;
import com.geektech.homework52.databinding.FragmentPostsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment implements OnClick {
    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        adapter.setOnClick(this);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        binding.btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            navController.navigate(R.id.action_postsFragment_to_formFragment);
            }
        });
    }

    @Override
    public void click(Post post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        Navigation.findNavController(requireView()).navigate(R.id.formFragment, bundle);
    }
}
