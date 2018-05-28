package com.blovote.surveys.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.blovote.app.App
import com.blovote.R
import com.blovote.app.BlovoteActivity
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.ui.creation.CreateSurveyActivity
import com.blovote.surveys.ui.passing.SurveyActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main_surveys.*
import kotlinx.android.synthetic.main.app_bar_main_surveys.*
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class MainSurveysActivity : BlovoteActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : SurveysAdapter
    private lateinit var layoutManager : RecyclerView.LayoutManager

    private var disposable = CompositeDisposable()

    @Inject
    lateinit var surveysInteractor: SurveysInteractor

    @Inject
    lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi()
        setupUx()
        setupData()
    }

    private fun setupUi() {
        setContentView(R.layout.activity_main_surveys)
        setSupportActionBar(toolbar)
    }

    private fun setupUx() {
        fab.setOnClickListener { view ->
            startActivity(CreateSurveyActivity.getStartIntent(this))
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        App.appComponent.inject(this)

        layoutManager = LinearLayoutManager(this)
        adapter = SurveysAdapter(surveysInteractor)
        disposable.add(adapter.observeSurveyClick()
                .subscribe { onSurveyClicked(it.first, it.second) })

        recyclerView = findViewById(R.id.recycler_view_surveys)
        recyclerView.apply {
            layoutManager = this@MainSurveysActivity.layoutManager
            adapter = this@MainSurveysActivity.adapter
        }

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })
    }

    private fun setupData() {
        adapter.startObservingSurveys(this)
        surveysInteractor.requestSurveysUpdate().subscribe()
    }


    override fun onDestroy() {
        super.onDestroy()
        adapter.stopObservingSurveys()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_surveys, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_update -> {
                Toast.makeText(this, getString(R.string.msg_loading_surveys), Toast.LENGTH_SHORT)
                        .show()
                surveysInteractor.requestSurveysUpdate().subscribe()
                return true
            }
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    fun onSurveyClicked(address: String, index: Int) {
        startActivity(SurveyActivity.getStartIntent(this, address, index))
    }

}
