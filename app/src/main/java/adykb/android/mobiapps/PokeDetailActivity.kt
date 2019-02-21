package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import android.content.Intent


class PokeDetailActivity : AppCompatActivity(){
    var pokemonId = -1
    var pokemonName = ""
    var pokemonHeight = -1
    var pokemonWeight = -1
    var pokemonTypes = ""
    var pokemonMoves = arrayListOf<String>()
    var pokemonSprite = ""
    lateinit var imagePokemon : ImageView
    lateinit var typesTitle : TextView
    lateinit var typesContent : TextView
    lateinit var heightTitle : TextView
    lateinit var heightContent : TextView
    lateinit var weightTitle : TextView
    lateinit var weightContent : TextView
    lateinit var movesTitle : TextView
    lateinit var movesButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedetail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initViewElements()

        isLoading()
        if(savedInstanceState !== null) {
            pokemonId = savedInstanceState.getInt(POKEMON_ID)
            pokemonName = savedInstanceState.getString(POKEMON_NAME)
            pokemonHeight = savedInstanceState.getInt(POKEMON_HEIGHT)
            pokemonWeight = savedInstanceState.getInt(POKEMON_WEIGHT)
            pokemonTypes = savedInstanceState.getString(POKEMON_TYPES)
            pokemonMoves = savedInstanceState.getStringArrayList(POKEMON_MOVES)
            pokemonSprite = savedInstanceState.getString(POKEMON_SPRITE)
            initViewContent()
            isLoaded()
        } else {
            Thread {
                initPokemon(PokeApiClient().getPokemon(intent.getIntExtra(ARG_ID, -1)))

                runOnUiThread {
                    initViewContent()
                    isLoaded()
                }
            }.start()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(POKEMON_ID, pokemonId)
        outState?.putString(POKEMON_NAME, pokemonName)
        outState?.putString(POKEMON_TYPES, pokemonTypes)
        outState?.putInt(POKEMON_HEIGHT, pokemonHeight)
        outState?.putInt(POKEMON_WEIGHT, pokemonWeight)
        outState?.putStringArrayList(POKEMON_MOVES, pokemonMoves)
        outState?.putString(POKEMON_SPRITE, pokemonSprite)
        super.onSaveInstanceState(outState)
    }

    private fun initPokemon(pokemon: Pokemon) {
        pokemonId = pokemon.id
        pokemonName = pokemon.name
        pokemonHeight = pokemon.height
        pokemonWeight = pokemon.weight
        var types = ""
        for (type in pokemon.types){
            types += type.type.name.capitalize()+"\n"
        }
        pokemonTypes = types.trim()
        for(move in pokemon.moves){
            pokemonMoves.add(move.move.name.capitalize())
        }
        pokemonSprite = pokemon.sprites.frontDefault.toString()
    }

    private fun initViewContent(){
        title = "#${pokemonId} : ${pokemonName.capitalize()}"

        Picasso.get().load(pokemonSprite).into(imagePokemon)

        typesContent.text = pokemonTypes
        heightContent.text = "${pokemonHeight / 10F}m"
        weightContent.text = "${pokemonWeight / 10F}kg"
    }

    // Affiche la liste des techniques
    fun movesButton(v: View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.moves_list))

        builder.setItems(pokemonMoves.toTypedArray(), null)

        val dialog = builder.create()
        dialog.show()
    }

    // Ajout de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_detail, menu)
        return true
    }

    // Action des boutons de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.share_button -> {
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = "#${pokemonId} : ${pokemonName.capitalize()}\n${pokemonSprite}"
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
                return true
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    // Cache le contenu le temps du chargement
    private fun isLoading() {
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

    // Affiche le contenu après le chargement
    private fun isLoaded() {
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

    private fun initViewElements(){
        imagePokemon = findViewById(R.id.image_pokemon)
        typesTitle = findViewById(R.id.types_title)
        typesContent = findViewById(R.id.types_content)
        heightTitle = findViewById(R.id.height_title)
        heightContent = findViewById(R.id.height_content)
        weightTitle = findViewById(R.id.weight_title)
        weightContent = findViewById(R.id.weight_content)
        movesTitle = findViewById(R.id.moves_title)
        movesButton = findViewById(R.id.moves_button)
    }

    companion object {
        val ARG_ID = "arg_id"
        val ARG_NAME = "arg_name"
        val POKEMON_ID = "pokemon_id"
        val POKEMON_NAME = "pokemon_name"
        val POKEMON_HEIGHT = "pokemon_height"
        val POKEMON_WEIGHT = "pokemon_weight"
        val POKEMON_TYPES = "pokemon_types"
        val POKEMON_MOVES = "pokemon_moves"
        val POKEMON_SPRITE = "pokemon_sprite"
    }
}
