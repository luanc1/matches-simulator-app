package me.Dio.simulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.Dio.simulator.databinding.ActivityDetail2Binding

class DetailActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityDetail2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}