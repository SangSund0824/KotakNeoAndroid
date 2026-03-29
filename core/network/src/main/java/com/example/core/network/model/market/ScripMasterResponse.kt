package com.example.core.network.model.market

data class ScripMasterResponse(
    val count: Int? = null,
    val segment: String? = null,
    val stocks: List<Map<String, String?>>? = null
)
