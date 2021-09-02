package com.github.pedroscott.yourbusinesscard.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.pedroscott.yourbusinesscard.databinding.CardItemBinding
import com.github.pedroscott.yourbusinesscard.model.Card

class MainAdapter(
    private val onClickCard: (CardItemBinding) -> Unit,
    private val onClickDelete: (Long) -> Unit,
    private val onClickEdit: (Card) -> Unit,
    private val onClickShare: (card: View, filename: String) -> Unit
) : ListAdapter<Card, MainViewHolder>(Card.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).let { binding ->
            MainViewHolder(binding)
        }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position), onClickCard, onClickDelete, onClickEdit, onClickShare)
    }

}