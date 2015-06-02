/*
 * SmartQrApiInterface.java 1.0 2014/04/24
 * 
 * Touchtastic (c) 2014
 */
package Net;

import retrofit.http.GET;

/**
 * Currency API interface for Retrofit.
 */
public interface CurrencyApiInterface {

    @GET("/live?access_key=610e072a45c42d83cd1b468414c77ffc&currencies=USD,MXN,GBP,EUR&format=1")
    CurrencyPayload getCurrency();

}

