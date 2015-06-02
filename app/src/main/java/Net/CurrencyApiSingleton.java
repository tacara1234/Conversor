/*
 * SmartQrApiSingleton.java 1.0 2014/04/24
 * 
 * Touchtastic (c) 2014
 */
package Net;

import com.currency.app.currencyconverter.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;


public final class CurrencyApiSingleton {

    private static CurrencyApiInterface sCurrencyApiSingleton;
    private static final int TIMEOUT = 10;

    private CurrencyApiSingleton() {
        /* Do nothing */
    }

    public synchronized static final CurrencyApiInterface getApiInterfaceInstance() {
        if (sCurrencyApiSingleton == null) {
        	OkHttpClient client = new OkHttpClient();
        	client.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS );
        	
            final RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(CurrencyApiBaseUrl.URL_API_BASE)
                    .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                    .setClient(new OkClient( client )).build();
            sCurrencyApiSingleton = restAdapter.create(CurrencyApiInterface.class);
        }
        return sCurrencyApiSingleton;
    }

}
