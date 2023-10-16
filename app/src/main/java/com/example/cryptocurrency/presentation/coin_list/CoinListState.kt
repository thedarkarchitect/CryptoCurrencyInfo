package com.example.cryptocurrency.presentation.coin_list


import com.example.cryptocurrency.domain.model.Coins

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coins> = emptyList(),
    val error: String = ""//this is the message recieved by Resource error
)
