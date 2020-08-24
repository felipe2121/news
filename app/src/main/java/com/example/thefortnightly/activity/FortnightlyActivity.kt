package com.example.thefortnightly.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.fortnightly.data.entiny.ArticleCategory
import com.example.fortnightly.data.entiny.ArticleCategory.Companion.ARTICLE_CATEGORY
import com.example.thefortnightly.R
import com.example.thefortnightly.fragment.ArticlesByCategoryFragment
import com.example.thefortnightly.fragment.FirstPageFragment
import kotlinx.android.synthetic.main.activity_fortnightly.*

class FortnightlyActivity : AppCompatActivity() {

    companion object {
        const val NAVIGATION_MENU_ID = "navigation_id_menu"
    }

    private var lastNavigation: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortnightly)
        setUpUI()

        if (savedInstanceState == null) navigateTo(R.id.navigation_first_page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_search) {
            Intent(this, SearchArticleAcitivity::class.java).also {
                startActivity(it)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lastNavigation !=  null) outState.putInt(NAVIGATION_MENU_ID, lastNavigation!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt(NAVIGATION_MENU_ID, -1).takeIf { it != -1 }?.let { id --> navigateTo(id) }
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

        navigation.setNavigationItemSelectedListener { item ->

            navigateTo(item.itemId)
            true
        }
    }

    private fun navigateTo(id: Int) {

        @Suppress("IMPLICIT_CAST_TO_ANY")
        val fragment = if (id == R.id.navigation_first_page){
            FirstPageFragment()
        } else {
            val navigationItem = navigation.menu.findItem(id)
            ArticlesByCategoryFragment().apply {
                arguments = bundleOf(ARTICLE_CATEGORY to ArticleCategory.getByOptionsName(navigationItem.title.toString()))
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.conatiner, (fragment as? Fragment) ?: Fragment())
            .commit()

        drawer.closeDrawer(GravityCompat.START)
        navigation.setCheckedItem(id)

        lastNavigation = id
    }
}


















