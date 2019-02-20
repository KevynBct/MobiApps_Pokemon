package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource


class MainActivity : AppCompatActivity(), PokeListFragment.OnListFragmentInteractionListener {
    var currentPage = 1
    lateinit  var pageSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPageSpinner()

        this.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, PokeListFragment()).commit()
    }

    override fun onListFragmentInteraction(item: NamedApiResource) {
        Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()
        print(item)
    }

    fun previousPage(v : View) {
        if(currentPage > 1) {
            currentPage--
            pageSpinner.setSelection(currentPage-1)
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    fun nextPage(v : View) {
        if(currentPage < 49) {
            currentPage++
            pageSpinner.setSelection(currentPage-1)
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
        }
    }

    fun initPageSpinner() {
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
}
