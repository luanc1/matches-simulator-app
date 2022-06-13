package me.Dio.simulator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import me.Dio.simulator.databinding.ActivityDetail2Binding
import me.Dio.simulator.domain.Match

class DetailActivity2 : AppCompatActivity() {

    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }


    private lateinit var binding: ActivityDetail2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.nome

            binding.tvDescription.text = it.description

            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if (it.homeTeam.score != null) {
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.awayTeam.name
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if (it.awayTeam.score != null) {
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }
        }
    }
}


