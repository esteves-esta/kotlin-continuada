package fernanda.esteves.projetoac2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fernanda.esteves.projetoac2.R
import fernanda.esteves.projetoac2.models.Dream
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class DreamAdapter(var list: ArrayList<Dream>, var clickListener: (Dream) -> Unit) :
    RecyclerView.Adapter<DreamAdapter.ViewHolder>() {

    fun update(new: List<Dream>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.card_sonho_titulo
        private val type = view.card_sonho_tipo
        private val card = view.dream_card

        fun bind(dream: Dream, clickListener: (Dream) -> Unit) {

            title.text = dream.titulo
            type.text = dream.tipo

            card.setOnClickListener {
                clickListener(dream)
            }
        }
    }
}


