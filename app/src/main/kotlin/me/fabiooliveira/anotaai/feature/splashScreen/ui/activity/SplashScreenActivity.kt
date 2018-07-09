package me.fabiooliveira.anotaai.feature.splashScreen.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.fabiooliveira.anotaai.R
import me.fabiooliveira.anotaai.util.ViewUtil
import android.content.Intent
import me.fabiooliveira.anotaai.feature.notesList.ui.activity.NotesListActivity

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class SplashScreenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewUtil.changeStatusBarColor(this, R.color.colorPrimary, false)

        val intent = Intent(this, NotesListActivity::class.java)
        startActivity(intent)
        finish()
    }
}