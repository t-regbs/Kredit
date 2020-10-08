package com.timilehinaregbesola.kredit.data.network

import com.timilehinaregbesola.kredit.data.model.MessageBody
import com.timilehinaregbesola.kredit.data.model.OtpResult
import com.timilehinaregbesola.kredit.data.model.SterlingResult
import com.timilehinaregbesola.kredit.data.model.TransBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultRetriever {
    private val service: ApiService

    companion object {
        const val BASE_URL = "https://sandboxapi.fsi.ng/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ApiService::class.java)
    }

    suspend fun getResult(): SterlingResult {
        val userData = TransBody(
            referenceId = "0101",
            requestType = "01",
            translocationBig = "0101",
            sessionID = "01",
            fromAccount = "01",
            toAccount = "01",
            amount = "01",
            destinationBankCode = "01",
            nerResponse = "01",
            benefiName = "01",
            paymentReference = "01",
            originatorAccountName = "01",
            translocationSmall = "01"
        )
        return service.makeTransfer(userData)
    }

    suspend fun getOtpResult(to: String): OtpResult {
        val data = MessageBody(to, "FSI", "Your One Time Password is 0000")
        return service.sendText(data)
    }
}