package adykb.android.mobiapps

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import adykb.android.mobiapps.PokeListFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_pokeitem.view.*
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class PokeItemAdapter(
    private val mValues: List<NamedApiResource>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PokeItemAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NamedApiResource
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pokeitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = "N°${item.id}"
        holder.mContentView.text = item.name.capitalize()

        with(holder.mCardView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mCardView = mView.cardView
        val mIdView: TextView = mView.number
        val mContentView: TextView = mView.name

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
