package com.example.cryptocurrency.presentation.coin_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cryptocurrency.common.Constants
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle//this cointains info about the saved state and it hold state of nav state where can get the id of a particular coin
): ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state = _state

    init{
        //we use the savedStatehandle to access the id saved in the state of the application from the api
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {coinId ->
            getCoin(coinId)
        }
    }


    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when(result) {
                is Resource.Error -> {
                    _state.value = CoinDetailState(
                        error = result.message ?: "An Unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = CoinDetailState(
                        coin = result.data
                    )
                }
            }

        }
    }
}