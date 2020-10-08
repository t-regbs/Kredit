package com.timilehinaregbesola.kredit.data.model

data class Loan(
    val id: String,
    val type: String,
    val date: String,
    val interest: String = "20",
    val info: String = ""
)