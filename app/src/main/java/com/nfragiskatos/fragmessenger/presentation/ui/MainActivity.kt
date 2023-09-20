package com.nfragiskatos.fragmessenger.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.nav_graph)

        graph.startDestination =
            if (Firebase.auth.currentUser == null) R.id.RegisterFragment else R.id.latestMessagesFragment

        navController.graph = graph

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.actionBarTitle.observe(this, Observer {
            supportActionBar?.title = it
        })
    }
}
