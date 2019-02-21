package adykb.android.mobiapps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource


class MainActivity : AppCompatActivity(), PokeListFragment.OnListFragmentInteractionListener {
    var currentPage = 1
    lateinit var navBar : LinearLayout
    lateinit var pageSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBar = findViewById(R.id.navBar)

        initPageSpinner()

        isLoading(true)
        this.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, PokeListFragment()).commit()
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
            pageSpinner.setSelection(currentPage-1)
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    fun nextPage(v : View) {
        if(currentPage < 49) {
            isLoading(false)
            currentPage++
            pageSpinner.setSelection(currentPage-1)
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    private fun initPageSpinner() {
        pageSpinner = findViewById(R.id.page_spinner)

        val totalPages = ArrayList<Int>()
        for (i in 1..49){
            totalPages.add(i)
        }

        val dataAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, totalPages)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pageSpinner.adapter = dataAdapter

        pageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentPage = pageSpinner.selectedItem as Int
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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
}
