package com.example.cryptocurrency.data.remote.dto



import com.example.cryptocurrency.domain.model.Coins
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    @SerialName("id")
    val id: String,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("is_new")
    val isNew: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String
)

fun CoinDto.toCoin(): Coins {
    return Coins(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}
