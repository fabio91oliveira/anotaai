package me.fabiooliveira.getnotes.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import org.koin.android.ext.android.inject

internal class StartActivity : AppCompatActivity() {

    private val listNotesNavigation: ListNotesNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        listNotesNavigation.navigateToFeature(this)
        finish()
    }
}