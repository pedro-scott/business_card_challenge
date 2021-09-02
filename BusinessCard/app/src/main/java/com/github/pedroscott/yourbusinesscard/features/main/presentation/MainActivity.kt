package com.github.pedroscott.yourbusinesscard.features.main.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.pedroscott.yourbusinesscard.R
import com.github.pedroscott.yourbusinesscard.databinding.ActivityMainBinding
import com.github.pedroscott.yourbusinesscard.databinding.CardItemBinding
import com.github.pedroscott.yourbusinesscard.extension.hideKeyboard
import com.github.pedroscott.yourbusinesscard.extension.toSearchFormat
import com.github.pedroscott.yourbusinesscard.features.main.adapter.MainAdapter
import com.github.pedroscott.yourbusinesscard.features.newcard.presentation.NewCardFragment
import com.github.pedroscott.yourbusinesscard.model.Card
import com.github.pedroscott.yourbusinesscard.util.ShareImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val newCardFragment: NewCardFragment get() = NewCardFragment()

    private val adapter by lazy {
        MainAdapter(
            onClickCard = { cardBinding -> onClickCard(cardBinding) },
            onClickDelete = { id -> onClickDelete(id) },
            onClickEdit = { card -> onClickEdit(card) },
            onClickShare = { card, filename ->
                onClickShare(card, filename)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.updateCardsList()
        setupObservables()
        setupRecyclerView()
        setupInputLayoutIcon()
        setupSearch()
        setupAddClick()
    }

    private fun setupObservables() {
        viewModel.onSuccessUpdateCardsList.observe(this) { cardList ->
            adapter.submitList(cardList)
        }

        viewModel.onSuccessSearchCards.observe(this) { cardList ->
            if (cardList.isNotEmpty()) {
                adapter.submitList(cardList)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMainActCards.layoutManager = LinearLayoutManager(this)
        binding.rvMainActCards.adapter = adapter
    }

    private fun onClickCard(cardBinding: CardItemBinding) {
        with(cardBinding) {
            if (cvCardItemMoreOptions.isVisible)
                cvCardItemMoreOptions.visibility = View.GONE
            else
                cvCardItemMoreOptions.visibility = View.VISIBLE
        }
    }

    private fun onClickDelete(id: Long) {
        viewModel.deleteCard(id)
        viewModel.updateCardsList()
    }

    private fun onClickEdit(card: Card) {
        with(newCardFragment) {
            setupWithThisCard(card)
            show(supportFragmentManager, "BOTTOM_SHEET_FRAG_2")
        }
    }

    private fun onClickShare(card: View, filename: String) {
        ShareImage.share(this, card, filename)
    }

    private fun setupInputLayoutIcon() {
        with(binding) {
            tietMainActSearch.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    tilMainActSearch.startIconDrawable = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_back)
                } else {
                    tilMainActSearch.startIconDrawable = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_search)
                }
            }

            tilMainActSearch.setStartIconOnClickListener {
                tilMainActSearch.editText?.clearFocus()
                hideKeyboard(tilMainActSearch)
            }
        }
    }

    private fun setupSearch() {
        with(binding) {
            tietMainActSearch.doOnTextChanged { _, _, _, _ ->
                tietMainActSearch.text.toString().toSearchFormat().let { text ->
                    viewModel.searchCards(text)
                }
            }
        }
    }

    private fun setupAddClick() {
        with(binding) {
            fabMainActAdd.setOnClickListener {
                newCardFragment.show(supportFragmentManager, "BOTTOM_SHEET_FRAG_1")
            }
        }
    }

}