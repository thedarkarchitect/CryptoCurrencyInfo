package com.example.cryptocurrency.domain.use_case.get_coins


import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.data.remote.dto.toCoin
import com.example.cryptocurrency.domain.model.Coins
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coins>>> = flow {
        try {
            emit(Resource.Loading<List<Coins>>())
            val coins = repository.getCoins().map { it.toCoin() }//this converts the results from server to only what you need
            emit(Resource.Success<List<Coins>>(coins))
        }catch (e: HttpException){
            emit(Resource.Error<List<Coins>>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){//this error occurs only when the hand shack to the server doesn't happen cause of internet connection issuses
            emit(Resource.Error<List<Coins>>("Couldn't reach server. Check your internet connection"))
        }
    }
}