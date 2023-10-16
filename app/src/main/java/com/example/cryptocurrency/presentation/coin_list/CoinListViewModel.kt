package com.example.cryptocurrency.presentation.coin_list


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {
    private val _state = mutableStateOf(CoinListState())
    val state = _state

    init{
        //this will be called on app initialization to get all the coins
        getCoins()
    }


    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = CoinListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                        _state.value = CoinListState(
                            coins = result.data ?: emptyList()
                        )
                }
            }

        }
    }
}