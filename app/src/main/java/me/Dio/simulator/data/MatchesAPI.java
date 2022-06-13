package me.Dio.simulator.data;

import java.util.List;

import me.Dio.simulator.domain.Match;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesAPI {
    @GET("mathes.json")
    Call<List<Match>>  getMatches();
}
