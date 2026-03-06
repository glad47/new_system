package com.ruoyi.api.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * RestTemplate Configuration for D365 API calls.
 *
 * Bypasses SSL certificate validation for D365 OneBox / self-signed environments.
 * Uses only JDK built-in classes — NO extra dependencies needed.
 *
 * For production with valid SSL certs, replace with: return new RestTemplate();
 *
 * إعداد RestTemplate — يتجاوز التحقق من شهادات SSL لبيئات D365 الداخلية
 */
@Configuration
public class RestTemplateConfig
{
    private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate()
    {
        try
        {
            // Create a trust manager that trusts ALL certificates
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            // Install the trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Set as default for all HTTPS connections
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            // Configure RestTemplate with timeouts
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException
                {
                    if (connection instanceof HttpsURLConnection)
                    {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
                        httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
                        httpsConn.setHostnameVerifier((hostname, session) -> true);
                    }
                    super.prepareConnection(connection, httpMethod);
                }
            };
            factory.setConnectTimeout(30000);   // 30 seconds
            factory.setReadTimeout(300000);     // 5 minutes (D365 OneBox can be slow)

            log.info("RestTemplate configured with SSL trust-all for D365 API calls");
            return new RestTemplate(factory);
        }
        catch (NoSuchAlgorithmException | KeyManagementException e)
        {
            log.warn("Failed to configure SSL trust-all, falling back to default RestTemplate: {}", e.getMessage());
            return new RestTemplate();
        }
    }
}