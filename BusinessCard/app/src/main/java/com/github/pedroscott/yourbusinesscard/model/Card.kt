package com.github.pedroscott.yourbusinesscard.model

import androidx.recyclerview.widget.DiffUtil

data class Card(
    var id: Long,
    var name: String,
    var companyName: String,
    var phone: String,
    var email: String,
    var colorBackground: Int,
    var colorText: Int,
    var logo: String
) {

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Card>() {
                override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem.id == newItem.id
            }
    }

}