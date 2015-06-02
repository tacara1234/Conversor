/*
 * SmartQrApiBaseUrl.java 1.0 2014/04/24
 * 
 * Touchtastic (c) 2014
 */
package Net;

public final class CurrencyApiBaseUrl {

    private static final String PROTOCOL = "http";
    private static final String DOMAIN_NAME = getDomain();
    public static final String URL_BASE = PROTOCOL + "://" + DOMAIN_NAME;
    public static final String URL_API_BASE = URL_BASE + "/api";

    private CurrencyApiBaseUrl() {
        /* Do nothing */
    }

    private static String getDomain() {
        return "apilayer.net";
    }

}
