package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import android.support.v7.app.AlertDialog
import android.widget.Button


class PokeDetailActivity : AppCompatActivity(){
    lateinit var pokemon : Pokemon
    lateinit var imagePokemon : ImageView
    lateinit var typesTitle : TextView
    lateinit var typesContent : TextView
    lateinit var heightTitle : TextView
    lateinit var heightContent : TextView
    lateinit var weightTitle : TextView
    lateinit var weightContent : TextView
    lateinit var movesTitle : TextView
    lateinit var movesButton : Button
    var movesList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedetail)

        imagePokemon = findViewById(R.id.image_pokemon)
        typesTitle = findViewById(R.id.types_title)
        typesContent = findViewById(R.id.types_content)
        heightTitle = findViewById(R.id.height_title)
        heightContent = findViewById(R.id.height_content)
        weightTitle = findViewById(R.id.weight_title)
        weightContent = findViewById(R.id.weight_content)
        movesTitle = findViewById(R.id.moves_title)
        movesButton = findViewById(R.id.moves_button)

        loadPokemon()


        Thread {
            pokemon = PokeApiClient().getPokemon(intent.getIntExtra(ARG_ID, -1))

            runOnUiThread {
                initContent()
                pokemonLoaded()
            }
        }.start()
    }

    private fun loadPokemon() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE

        imagePokemon.visibility = View.INVISIBLE
        typesTitle.visibility = View.INVISIBLE
        typesContent.visibility = View.INVISIBLE
        heightTitle.visibility = View.INVISIBLE
        heightContent.visibility = View.INVISIBLE
        weightTitle.visibility = View.INVISIBLE
        weightContent.visibility = View.INVISIBLE
        movesTitle.visibility = View.INVISIBLE
        movesButton.visibility = View.INVISIBLE
    }

    private fun pokemonLoaded() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE

        imagePokemon.visibility = View.VISIBLE
        typesTitle.visibility = View.VISIBLE
        typesContent.visibility = View.VISIBLE
        heightTitle.visibility = View.VISIBLE
        heightContent.visibility = View.VISIBLE
        weightTitle.visibility = View.VISIBLE
        weightContent.visibility = View.VISIBLE
        movesTitle.visibility = View.VISIBLE
        movesButton.visibility = View.VISIBLE

    }

    private fun initContent(){
        title = "#${pokemon.id} : ${pokemon.name.capitalize()}"

        Picasso.get().load(pokemon.sprites.frontDefault).into(imagePokemon)

        var types = ""
        for (type in pokemon.types){
            types += type.type.name.capitalize()+"\n"
        }
        typesContent.text = types



        heightContent.text = "${pokemon.height / 10F}m"

        weightContent.text = "${pokemon.weight / 10F}kg"

        for(move in pokemon.moves){
            movesList.add(move.move.name.capitalize())
        }
    }

    fun movesButton(v: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Moves list")

        builder.setItems(movesList.toTypedArray(), null)

        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        val ARG_ID = "arg_id"
        val ARG_NAME = "arg_name"
    }
}
