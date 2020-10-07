package com.timilehinaregbesola.kredit.data.model

data class Loan(
    val id: String,
    val type: String,
    val date: String,
    val info: String = ""
)