package com.example.alvaro.client_audit.core.networks;

import android.util.Log;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;

public class SslUtil {

    private static final String password = "password";

    public static SSLSocketFactory getSocketFactory(InputStream jks_stream) {
        try {
            KeyStore jks = KeyStore.getInstance("PKCS12");
            jks.load(jks_stream, password.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(jks);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(jks, password.toCharArray());

            /*
             * Create SSL socket factory
             */
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            /*
             * Return the newly created socket factory object
             */
            return context.getSocketFactory();

        } catch (Exception e) {
            Log.e("SSL::Factory", Arrays.toString(e.getStackTrace()));
            Log.e("SSL::FACTORY",e.getMessage());
        }

        return null;
    }
}