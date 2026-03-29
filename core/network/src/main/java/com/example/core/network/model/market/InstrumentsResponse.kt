package com.example.core.network.model.market

data class InstrumentsResponse(
    val data: List<InstrumentData>? = null,
    val error: String? = null,
    val status: Int? = null,
    val response: String? = null
)

data class InstrumentData(
    val pSymbol: String,
    val pTrdSymbol: String,
    val pGroup: String,
    val pEXSeg: String,
    val pInstType: String,
    val pSymbolName: String
)
