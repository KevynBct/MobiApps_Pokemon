package adykb.android.mobiapps

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient

import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokeListFragment : Fragment() {
    private var pageNumber = 0

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pageNumber = (it.getInt(ARG_PAGE_NUMBER) - 1)*20
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokelist, container, false)

        if (view is RecyclerView) {
            with(view) {
                Thread {
                    var pokeList = PokeApiClient().getPokemonList(pageNumber, 20).results
                    print(pokeList)

                    activity?.runOnUiThread {
                        adapter = PokeItemAdapter(pokeList, listener)
                        listener?.listLoaded()
                    }
                }.start()
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun listLoaded()
        fun onListFragmentInteraction(item: NamedApiResource)
    }

    companion object {
        const val ARG_PAGE_NUMBER = "page_number"

        @JvmStatic
        fun newInstance(pageNumber: Int) =
            PokeListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PAGE_NUMBER, pageNumber)
                }
            }
    }
}
