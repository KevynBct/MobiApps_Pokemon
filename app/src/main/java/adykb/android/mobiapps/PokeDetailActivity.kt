package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeDetailActivity : AppCompatActivity(){

    lateinit var textViewId : TextView
    lateinit var textViewName : TextView
    lateinit var pokemon : Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedetail)

        textViewId = findViewById(R.id.detail_id)
        textViewName = findViewById(R.id.detail_name)

        textViewId.visibility = View.INVISIBLE
        textViewName.visibility = View.INVISIBLE

        Thread {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
            pokemon = PokeApiClient().getPokemon(intent.getIntExtra(ARG_ID, -1))

            runOnUiThread {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE

                textViewId.text = pokemon.id.toString()
                textViewName.text = pokemon.name

                textViewId.visibility = View.VISIBLE
                textViewName.visibility = View.VISIBLE
            }
        }.start()
    }

    companion object {
        val ARG_ID = "arg_id"
        val ARG_NAME = "arg_name"
    }
}
