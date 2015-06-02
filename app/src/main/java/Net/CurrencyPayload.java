package Net;

import com.google.gson.annotations.SerializedName;

/**
 * Currency base
 *
 * Created by Alejandro on 5/14/15.
 */
public class CurrencyPayload {

    @SerializedName("quotes")
    private Rate mRates;

    public Rate getRates() {
        return mRates;
    }
}
