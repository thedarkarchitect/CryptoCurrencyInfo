package com.example.cryptocurrency.domain.use_case.get_coins

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.data.remote.dto.toCoin
import com.example.cryptocurrency.domain.model.Coin
import com.example.cryptocurrency.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }//this converts the results from server to only what you need
            emit(Resource.Success(coins))
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){//this error occurs only when the hand shack to the server doesn't happen cause of internet connection issuses
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}