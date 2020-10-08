package com.timilehinaregbesola.kredit.data.model

import com.google.gson.annotations.SerializedName

data class TransBody(
    @SerializedName("Amount") val amount: String,
    @SerializedName("BenefiName") val benefiName: String,
    @SerializedName("DestinationBankCode") val destinationBankCode: String,
    @SerializedName("FromAccount") val fromAccount: String,
    @SerializedName("NEResponse") val nerResponse: String,
    @SerializedName("OriginatorAccountName") val originatorAccountName: String,
    @SerializedName("PaymentReference") val paymentReference: String,
    @SerializedName("Referenceid")  val referenceId: String,
    @SerializedName("RequestType")  val requestType: String,
    @SerializedName("SessionID")  val sessionID: String,
    @SerializedName("ToAccount")  val toAccount: String,
    @SerializedName("Translocation")  val translocationBig: String,
    @SerializedName("translocation")  val translocationSmall: String
)