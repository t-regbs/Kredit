package com.timilehinaregbesola.kredit.data.model

data class SMSMessageData(
    val Message: String,
    val Recipients: List<Recipient>
)