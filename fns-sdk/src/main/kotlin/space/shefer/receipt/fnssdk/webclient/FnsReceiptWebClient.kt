package space.shefer.receipt.fnssdk.webclient

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*

@Component
class FnsReceiptWebClient {

    @Value("\${fns.login}")
    lateinit var login: String

    @Value("\${fns.password}")
    lateinit var password: String

    fun get(fn: String, fd: String, fp: String): String? {
        val uri = urlGet(fn, fd, fp)
        val headers = HttpHeaders()
        headers.add("device-id", "")
        headers.add("device-os", "")
        headers.add("Authorization", getAuthHeader(login, password))
        val responseEntity = RestTemplate().exchange(
                URI.create(uri),
                HttpMethod.GET,
                HttpEntity<String>(headers),
                String::class.java
        )
        val statusCode = responseEntity.statusCode

        if (responseEntity.statusCode.equals(200)) {
            return responseEntity.body
        }
        return null
    }

    fun getReceiptExists(fn: String, fd: String, fp: String, time: String, money: Float): Boolean {
        val uri = getReceiptExistsUrl(fn, fd, fp, time, money)
        val headers = HttpHeaders()
        headers.add("device-id", "")
        headers.add("device-os", "")
        headers.add("Authorization", getAuthHeader(login, password))
        val responseEntity = RestTemplate().exchange(
                URI.create(uri),
                HttpMethod.GET,
                HttpEntity<String>(headers),
                String::class.java
        )
        return responseEntity.statusCode?.value() == 204

    }

    private fun getAuthHeader(login: String, password: String): String {
        val plainCredentials = "$login:$password"
        val plainCredentialsBytes = plainCredentials.toByteArray()
        val base64CredentialsBytes = Base64.getEncoder().encode(plainCredentialsBytes)
        val base64Credentials = String(base64CredentialsBytes)
        return "Basic $base64Credentials"
    }

    companion object {
        private const val HOST = "https://proverkacheka.nalog.ru:9999"
        private fun urlGet(fn: String, fd: String, fp: String): String {
            return HOST + "/v1/inns/*/kkts/*" +
                    "/fss/" + fn +
                    "/tickets/" + fd +
                    "?fiscalSign=" + fp +
                    "&sendToEmail=no"
        }

        private fun getReceiptExistsUrl(fn: String, fd: String, fp: String, time: String, money: Float): String {
            val moneyForUrl: Int = (money * 100).toInt()
            return HOST + "/v1/ofds/*/inns/*" +
                    "/fss/" + fn +
                    "/operations/1" +
                    "/tickets/" + fd +
                    "?fiscalSign=" + fp +
                    "&date=" + time +
                    "&sum=" + moneyForUrl
        }
    }
}
