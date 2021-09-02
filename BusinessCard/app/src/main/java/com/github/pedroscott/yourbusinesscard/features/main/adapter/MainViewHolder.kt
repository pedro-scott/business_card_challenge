package com.github.pedroscott.yourbusinesscard.features.main.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.github.pedroscott.yourbusinesscard.R
import com.github.pedroscott.yourbusinesscard.databinding.CardItemBinding
import com.github.pedroscott.yourbusinesscard.model.Card

class MainViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        card: Card,
        onClickCard: (CardItemBinding) -> Unit,
        onClickDelete: (Long) -> Unit,
        onClickEdit: (Card) -> Unit,
        onClickShare: (card: View, filename: String) -> Unit
    ) {
        with(binding) {
            with(card) {
                setupLogo(ivCardItemLogo, logo)
                setupFields(card)

                cvCardItem.setCardBackgroundColor(colorBackground)

                btCardItemShare.setColorFilter(colorText)

                cvCardItem.setOnClickListener {
                    cvCardItem.requestFocusFromTouch()
                    onClickCard(binding)
                }

                btCardItemDelete.setOnClickListener {
                    onClickDelete(id)
                }

                btCardItemEdit.setOnClickListener {
                    onClickEdit(card)
                    cvCardItemMoreOptions.visibility = View.GONE
                }

                btCardItemShare.setOnClickListener {
                    onClickShare(cvCardItem, "$name - $companyName")
                }
            }
        }
    }

    private fun setupFields(card: Card) {
        with(binding) {
            with(card) {
                tvCardItemName.text = name
                tvCardItemName.setTextColor(colorText)

                tvCardItemPhone.text = phone
                tvCardItemPhone.setTextColor(colorText)

                tvCardItemEmail.text = email
                tvCardItemEmail.setTextColor(colorText)

                tvCardItemCompany.text = companyName
                tvCardItemCompany.setTextColor(colorText)
            }
        }
    }

    private fun setupLogo(
        ivLogo: ImageView,
        logoUri: String
    ) {
        try {
            if (logoUri == "") {
                ivLogo.setImageResource(R.drawable.no_image)
            } else {
                ivLogo.setImageURI(logoUri.toUri())
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            ivLogo.setImageResource(R.drawable.no_image)
        }
    }

}