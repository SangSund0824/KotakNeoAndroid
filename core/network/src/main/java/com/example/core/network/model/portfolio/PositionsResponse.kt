package com.example.core.network.model.portfolio

data class PositionsResponse(
    val stat: String? = null,
    val stCode: Int? = null,
    val data: List<Position>? = null
)

data class Position(
    val trdSym: String? = null,
    val sym: String? = null,
    val avgPrc: String? = null,
    val fldQty: Int? = null,
    val flDt: String? = null,
    val exSeg: String? = null,
    val prcTp: String? = null,
    val prod: String? = null,
    val qty: Int? = null,
    val it: String? = null,
    val trnsTp: String? = null,
    val series: String? = null,
    val lotSz: String? = null,
    val multiplier: String? = null,
    val genNum: String? = null,
    val genDen: String? = null,
    val prcNum: String? = null,
    val prcDen: String? = null,
    val precision: String? = null,
    val cfBuyQty: Int? = null,
    val cfSellQty: Int? = null,
    val flBuyQty: Int? = null,
    val flSellQty: Int? = null,
    val cfBuyAmt: Double? = null,
    val cfSellAmt: Double? = null,
    val buyAmt: Double? = null,
    val sellAmt: Double? = null,
    val ltp: String? = null,
    val stkPrc: String? = null,
    val nOrdNo: String? = null,
    val exTm: String? = null
)
