package com.leary.littlefairy.beautifulapp.model.network.api;

import android.os.Build;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.JsonObject;
import com.leary.littlefairy.beautifulapp.BuildConfig;
import com.leary.littlefairy.beautifulapp.app.AppController;
import com.leary.littlefairy.beautifulapp.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/6.
 */

public final class ApiClient {
    private ApiClient() {}
    private static ClearableCookieJar cookieJar =
            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AppController.getInstance().getBaseContext()));
    private static final OkHttpClient client = getSafeOkhttpClient1();
    public static ApiService service = new Retrofit.Builder()
            .client(client)
            .baseUrl("http://171.221.228.25:9200/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(ApiService.class);
    /**
     * 单向认证，非安全的连接
     *
     * @return
     */
    public static OkHttpClient getUnsafeOkhttpClient() {
        OkHttpClient.Builder unsafeClientBuilder = new OkHttpClient.Builder();

        try {
            List<InputStream> certificates = new ArrayList<InputStream>();
            certificates.add(AppController.getInstance().getAssets().open("server_der.cer"));

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(trustManagerFactory.getTrustManagers());

            sslContext.init(null, wrappedTrustManagers, new SecureRandom());
            unsafeClientBuilder
                    .addInterceptor(createRequestInterceptor())
                    .addInterceptor(createHttpLoggingInterceptor())
                    .cookieJar(cookieJar)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return unsafeClientBuilder.build();
    }

    public static OkHttpClient getSafeOkhttpClient1() {
        OkHttpClient.Builder b = new OkHttpClient.Builder();

        try {
            List<InputStream> certificates = new ArrayList<InputStream>();
            certificates.add(AppController.getInstance().getAssets().open("server_der.cer"));

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(trustManagerFactory.getTrustManagers());

            //初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                clientKeyStore.load(AppController.getInstance().getApplicationContext().getAssets().open("client.bks"), "123456".toCharArray());
            } else {
                clientKeyStore.load(AppController.getInstance().getApplicationContext().getAssets().open("client_v1.bks"), "123456".toCharArray());
            }

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());

            sslContext.init(keyManagerFactory.getKeyManagers(), wrappedTrustManagers, new SecureRandom());
            b
                    .addInterceptor(createRequestInterceptor())
                    .addInterceptor(createHttpLoggingInterceptor())
                    .cookieJar(cookieJar)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b.build();
    }
    private static Interceptor createRequestInterceptor() {
        return new Interceptor() {
            private static final String NAME_USER_AGENT = "User-Agent";
            private static final String PARAMETER_UID = "uid";
            private static final String PARAMETER_APP_IDENTIFY = "appIdentify";
            private static final String PARAMETER_TIMESTAMP = "timestamp";
            private static final String PARAMETER_TOKEN = "token";
            private static final String PARAMETER_SCHOOL_CODE = "schoolCode";

            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                final Request.Builder newBuilder = chain.request().newBuilder()
                        .header(NAME_USER_AGENT, ApiDefine.USER_AGENT);

                String httpPath = request.url().toString();
                String strTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
                String token = "2BC3D5AB-5E71-191C-46B8-4E24E6DD4F70";
                int uid = ApiDefine.USER_ID;
                String appIdentify = ApiDefine.ID_APP_CLIENT_COMMON_PARENT;
                String schoolCode = ApiDefine.ID_APP_SCHOOL_CODE;
                if (request.method().equals("POST")) {
                    if (request.body() instanceof FormBody) {
                        FormBody oidFormBody = (FormBody) request.body();
                        JsonObject jsonObject = new JsonObject();
                        for (int i = 0; i < oidFormBody.size(); i++) {
                            jsonObject.addProperty(oidFormBody.name(i), oidFormBody.value(i));
                        }
                        //每次请求必传:
                        jsonObject.addProperty(PARAMETER_UID,uid);//uid 用来反差token
                        jsonObject.addProperty(PARAMETER_TIMESTAMP,strTimestamp);//时间戳 用来限制请求时效
                        jsonObject.addProperty(PARAMETER_APP_IDENTIFY,appIdentify);//APP唯一标识
                        jsonObject.addProperty(PARAMETER_SCHOOL_CODE,schoolCode);//学校编码,用来区分学校
                        jsonObject.addProperty(PARAMETER_TOKEN,token);//token 本地保存
                        LogUtils.e(httpPath + "$$" + jsonObject.toString());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                jsonObject.toString());
                        newBuilder.method(request.method(), requestBody);
                    }

                }
                request = newBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {

        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };
    }
    private static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

}
