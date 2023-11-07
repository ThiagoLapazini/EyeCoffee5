package com.example.eyecoffee

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.bottomBarVisible.observe(this) { visible ->
            binding.footer.visibility = if (visible) View.VISIBLE else View.GONE
        }

    }
}