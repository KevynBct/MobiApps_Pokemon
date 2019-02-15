package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class MainActivity : AppCompatActivity(), PokeListFragment.OnListFragmentInteractionListener {
    var currentPage = 1
    lateinit  var pageCounter : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageCounter = findViewById(R.id.editPageCounter)
        pageCounter.setText(currentPage.toString())

        pageCounter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Toast.makeText(applicationContext, "1", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(applicationContext, "2", Toast.LENGTH_SHORT).show()
            }
        })

        pageCounter.setOnFocusChangeListener { v, hasFocus ->
            run {
                if (!hasFocus) {
                    Toast.makeText(applicationContext, "3", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "4", Toast.LENGTH_SHORT).show()
                }
            }
        }

        var pokeListFragment = PokeListFragment()

        this.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, pokeListFragment).commit()

    }

    override fun onListFragmentInteraction(item: NamedApiResource) {
        Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()
        print(item)
    }

    fun previousPage(v : View) {
        if(currentPage > 1) {
            currentPage--
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
            pageCounter.setText(currentPage.toString())
        }
    }

    fun nextPage(v : View) {
        if(currentPage < 49) {
            currentPage++
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout, PokeListFragment.newInstance(currentPage)).commit()
            pageCounter.setText(currentPage.toString())
        }
    }
}
