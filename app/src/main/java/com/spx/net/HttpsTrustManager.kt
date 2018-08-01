package com.spx.net

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * 正常是不需要这个类的, 有时候用了fiddler代理抓包, 就需要设置一下了
 */
class HttpsTrustManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }

    class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(s: String, sslSession: SSLSession): Boolean {
            return true
        }
    }

    companion object {

        fun createSSLSocketFactory(): SSLSocketFactory? {
            var sSLSocketFactory: SSLSocketFactory? = null
            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(HttpsTrustManager()), SecureRandom())
                sSLSocketFactory = sc.socketFactory
            } catch (e: Exception) {
            }

            return sSLSocketFactory
        }
    }
}
