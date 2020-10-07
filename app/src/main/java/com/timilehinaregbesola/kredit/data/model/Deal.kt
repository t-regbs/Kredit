package com.timilehinaregbesola.kredit.data.model

data class Deal(
    val id: Int,
    val category: String,
    val amount: String,
    val interest: Int,
    val payBackDays: Int
)