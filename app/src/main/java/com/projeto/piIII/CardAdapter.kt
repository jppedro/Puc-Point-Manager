package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projeto.piIII.R
import com.projeto.piIII.model.Point

class CardAdapter(private val cards: List<CardData>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*val textViewDay: TextView = itemView.findViewById(R.id.textViewDay)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewHours: TextView = itemView.findViewById(R.id.textViewHours)
        val textViewType: TextView = itemView.findViewById(R.id.textViewType)
        // Referências a outros elementos do layout do card

        fun bind(cardData: CardData) {
            textViewDay.text = cardData.day
            textViewAddress.text = cardData.address
            textViewHours.text = "${cardData.hours} horas"
            textViewType.text = cardData.type
        }*/

        private val pointTypeTextView: TextView = itemView.findViewById(R.id.textViewType)
        private val registerDateTextView: TextView = itemView.findViewById(R.id.textViewHours)

        fun bind(card: CardData) {
            pointTypeTextView.text = "Point Type: ${card.pointType}"
            registerDateTextView.text = "Register Date: ${card.registerDate}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardData = cards[position]
        holder.bind(cardData)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}
