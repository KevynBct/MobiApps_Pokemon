package adykb.android.mobiapps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource


class MainActivity : AppCompatActivity(), PokeListFragment.OnListFragmentInteractionListener {
    var currentPage = 1
    lateinit var navBar : ConstraintLayout
    lateinit var pageCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBar = findViewById(R.id.navBar)
        pageCounter = findViewById(R.id.page_counter)

        if(savedInstanceState !== null) {
            isLoading(false)
            currentPage = savedInstanceState.getInt(CURRENT_PAGE)
            this.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        } else {
            isLoading(true)
            this.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, PokeListFragment()).commit()
        }

        pageCounter.text = "${currentPage} / 49"
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(CURRENT_PAGE, currentPage)
        super.onSaveInstanceState(outState)
    }

    override fun onListFragmentInteraction(item: NamedApiResource) {
        var intent = Intent(this, PokeDetailActivity::class.java)
        intent.putExtra(PokeDetailActivity.ARG_ID, item.id)
        intent.putExtra(PokeDetailActivity.ARG_NAME, item.name)
        startActivity(intent)
    }

    override fun listLoaded() {
        isLoaded()
    }


    fun previousPage(v : View) {
        if(currentPage > 1) {
            isLoading(false)
            currentPage--
            pageCounter.text = "${currentPage} / 49"
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    fun nextPage(v : View) {
        if(currentPage < 49) {
            isLoading(false)
            currentPage++
            pageCounter.text = "${currentPage} / 49"
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    private fun isLoading(firstInit: Boolean) {
        findViewById<ProgressBar>(R.id.loaderList).visibility = View.VISIBLE

        if(firstInit){
            navBar.visibility = View.INVISIBLE
        }
    }

    private fun isLoaded() {
        findViewById<ProgressBar>(R.id.loaderList).visibility = View.INVISIBLE

        navBar.visibility = View.VISIBLE
    }

    companion object {
        val CURRENT_PAGE = "current_page"
    }
}
