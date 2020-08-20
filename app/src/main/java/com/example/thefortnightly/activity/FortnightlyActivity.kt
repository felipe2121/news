package com.example.thefortnightly.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.entiny.ArticleCategory.Companion.ARTICLE_CATEGORY
import com.example.fortnightly.domain.repository.NewsRemoteRepository
import com.example.thefortnightly.R
import com.example.thefortnightly.fragment.CategoryFragment
import kotlinx.android.synthetic.main.activity_fortnightly.*
import kotlinx.coroutines.launch

class FortnightlyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortnightly)
        setUpUI()

        val response = lifecycleScope.launch {
            NewsRemoteRepository().fetchEverything("BR")
        }
    }

    private fun setUpUI() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val toogle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toogle.syncState()

        drawer.addDrawerListener(toogle)

        navigation.setNavigationItemSelectedListener {

            val fragment = CategoryFragment().apply {
                arguments = bundleOf(ARTICLE_CATEGORY to )
            }

            fragment.articleCategory

            supportFragmentManager.beginTransaction()
                .replace(R.id.conatiner, CategoryFragment())
                .commit()
            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_search) {

            Intent(this, SearchAcitivity::class.java).also {
                startActivity(it)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}


















