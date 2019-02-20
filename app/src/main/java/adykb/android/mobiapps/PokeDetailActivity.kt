package adykb.android.mobiapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PokeDetailActivity : AppCompatActivity() {
    lateinit var textViewId : TextView
    lateinit var textViewName : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedetail)

        textViewId = findViewById(R.id.detail_id)
        textViewName = findViewById(R.id.detail_name)

        textViewId.text = intent.getIntExtra(ARG_ID, -1).toString()
        textViewName.text = intent.getStringExtra(ARG_NAME)

    }

    companion object {
        val ARG_ID = "arg_id"
        val ARG_NAME = "arg_name"
    }
}
