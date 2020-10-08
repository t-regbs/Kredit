package com.timilehinaregbesola.kredit.data.network

import com.timilehinaregbesola.kredit.data.model.MessageBody
import com.timilehinaregbesola.kredit.data.model.OtpResult
import com.timilehinaregbesola.kredit.data.model.SterlingResult
import com.timilehinaregbesola.kredit.data.model.TransBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Content-Type: application/json",
        "Sandbox-Key: a0ef31771f20d3383766cc684fd5aeaf",
        "Ocp-Apim-Subscription-Key: t",
        "Ocp-Apim-Trace: true",
        "Appid: 69",
        "ipval: 0"
    )
    @POST("sterling/accountapi/api/Spay/InterbankTransferReq")
    suspend fun makeTransfer(@Body userData: TransBody): SterlingResult

    @Headers(
        "Sandbox-Key: a0ef31771f20d3383766cc684fd5aeaf",
        "Content-Type: application/json"
    )
    @POST("atlabs/messaging")
    suspend fun sendText(@Body messageBody: MessageBody): OtpResult
}