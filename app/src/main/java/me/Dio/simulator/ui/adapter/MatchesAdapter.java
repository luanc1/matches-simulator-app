package me.Dio.simulator.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.Dio.simulator.databinding.MatchItemBinding;
import me.Dio.simulator.domain.Match;
import me.Dio.simulator.ui.DetailActivity2;


public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    public List<Match> getMatches() {
        return matches;
    }

    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Match match = matches.get(position);

        // Adapta os dados da partida (recuperada da API) para o nosso layout.
        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.imageViewTeam);
        holder.binding.tvHomeTeamName.setText(match.getHomeTeam().getName());
        if(match.getHomeTeam().getScore() != null){
            holder.binding.tvHomeTeamScore.setText(String.valueOf(match.getHomeTeam().getScore()));
        }
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivAwayTeam);
        holder.binding.tvAwayTeam.setText(match.getAwayTeam().getName());
        //local de atribuição de nomes
        //holder.binding.tvAwayTeam.setText(match.getAwayTeam().getName());
        if(match.getAwayTeam().getScore() != null){
            holder.binding.tvAwayScore.setText(String.valueOf(match.getAwayTeam().getScore()));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity2.class);
            intent.putExtra(DetailActivity2.Extras.MATCH, match);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
       //pega o tamanho da lista
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
