package Net;

import com.google.gson.annotations.SerializedName;


public class Rate {


    @SerializedName("USDUSD")
    private double mUSDRate;

    @SerializedName("USDMXN")
    private double mMXNRate;

    @SerializedName("USDGBP")
    private double mGBPRate;

    @SerializedName("USDEUR")
    private double mEURRate;


    public double getUSDRate() {
        return mUSDRate;
    }

    public double getMXNRate() {
        return mMXNRate;
    }

    public double getGBPRate() {
        return mGBPRate;
    }

    public double getEURRate() {
        return mEURRate;
    }
}
