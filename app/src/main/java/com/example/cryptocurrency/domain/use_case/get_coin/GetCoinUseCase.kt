package com.example.cryptocurrency.domain.use_case.get_coin


import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.data.remote.dto.toCoinDetails
import com.example.cryptocurrency.domain.model.CoinDetails
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetails>> = flow {
        try {
            emit(Resource.Loading<CoinDetails>())
            val coin = repository.getCoinsById(coinId).toCoinDetails()//this converts the results from server to only what you need
            emit(Resource.Success<CoinDetails>(coin))
        }catch (e: HttpException){
            emit(Resource.Error<CoinDetails>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){//this error occurs only when the hand shack to the server doesn't happen cause of internet connection issuses
            emit(Resource.Error<CoinDetails>("Couldn't reach server. Check your internet connection"))
        }
    }
}