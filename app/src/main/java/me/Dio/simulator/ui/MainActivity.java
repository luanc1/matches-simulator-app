package me.Dio.simulator.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import me.Dio.simulator.R;
import me.Dio.simulator.data.MatchesAPI;
import me.Dio.simulator.databinding.ActivityMainBinding;
import me.Dio.simulator.domain.Match;
import me.Dio.simulator.ui.adapter.MatchesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MatchesAPI matchesApi;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceStete){
        super.onCreate(savedInstanceStete);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();
    }

    private void setupHttpClient() {
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://luanc1.github.io/mtches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
      matchesApi =   retrofit.create(MatchesAPI.class);
    }

    private void setupMatchesList() {

        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this ));
        //Todo: listar as partidas cosumidas pela API
      findMatchesFromAPI();
    }
    private void setupMatchesRefresh() {
        //Todo: Atualizar a partidas na ação do swipe
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromAPI);
    }

    private void showErrorMessage() {
        Snackbar.make(binding.fabSimolate, R.string.error_api,Snackbar.LENGTH_LONG).show();
    }




    private void setupFloatingActionButton() {
        //Todo:Criar um evento de click e de simulação de partidas
        binding.fabSimolate.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Random random = new Random();
                    for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                        Match match = matchesAdapter.getMatches().get(i);
                        match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                        match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                        matchesAdapter.notifyItemChanged(i);
                    }
                }
            });
        });
    }

    private void findMatchesFromAPI() {
        binding.srlMatches.setRefreshing(true);
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()){
                    List<Match>  matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else{
                    showErrorMessage();
                    binding.srlMatches.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
                binding.srlMatches.setRefreshing(false);
            }
        });
    }



}
