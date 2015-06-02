package com.currency.app.currencyconverter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import Net.CurrencyLoader;
import Net.CurrencyPayload;


public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<CurrencyPayload>, View.OnClickListener{

    //constantes para representar los valores
    private static final int USD = 1;
    private static final int MXN = 2;
    private static final int EUR = 3;
    private static final int GBP = 4;


    private static int LOADER_ID = 1;

    private EditText mCurrencyInput;
    private TextView mDisplayConversion;
    private RadioGroup mRadioGroupFrom;
    private RadioGroup mRadioGroupTo;


    private CurrencyPayload mCurrencyPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set up the callback for the convert button */
        findViewById( R.id.bttn_convert ).setOnClickListener( MainActivity.this );

        mCurrencyInput = ( EditText ) findViewById( R.id.edtxt_currency );
        mDisplayConversion = ( TextView ) findViewById( R.id.txt_conversion);

        mRadioGroupFrom = ( RadioGroup ) findViewById( R.id.rdio_group_from );
        mRadioGroupTo = ( RadioGroup ) findViewById( R.id.rdio_group_to );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void requestCurrencyRates(){
        /* starting request for the api data */
        getSupportLoaderManager().restartLoader( LOADER_ID, null /* bundle */, MainActivity.this /* loader callback */);
    }
    public boolean isNumber(String a ){
        try{
            Float.parseFloat(a);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    private void convertCurrency() {
        int fromCurrency = parseRadioIdToCurrency(mRadioGroupFrom.getCheckedRadioButtonId());
        int toCurrency = parseRadioIdToCurrency(mRadioGroupTo.getCheckedRadioButtonId());

        if (!isNumber(mCurrencyInput.getText().toString())) {
            Toast.makeText(MainActivity.this, "Numero invalido", Toast.LENGTH_SHORT).show();

        }else{
        double quantity = Double.parseDouble(mCurrencyInput.getText().toString());

        double conversion;

        switch (fromCurrency) {
            case EUR:
                conversion = convertEuro(toCurrency, quantity);
                break;
            case USD:
                conversion = convertUSD(toCurrency, quantity);
                break;
            case MXN:
                conversion = convertMXN(toCurrency, quantity);
                break;
            case GBP:
                conversion = convertGBP(toCurrency, quantity);
                break;
            default:
                throw new IllegalStateException("Invalid currency ID " + fromCurrency);
        }

        /* displaying the result */
        DecimalFormat format = new DecimalFormat("##.##");
        mDisplayConversion.setText(format.format(conversion));
    }
    }

    private double convertEuro( int currencyCode, double quantity ){
        switch ( currencyCode ){
            case USD:
                return quantity / mCurrencyPayload.getRates().getEURRate();
            case MXN:
                return convertUSD( MXN, quantity ) / mCurrencyPayload.getRates().getEURRate();
            case GBP:
                return convertUSD( GBP, quantity ) / mCurrencyPayload.getRates().getEURRate();
            default:
                return quantity;
        }
    }


    private double convertMXN( int currencyCode, double quantity ){
        switch ( currencyCode ){
            case EUR:
                return convertUSD( EUR, quantity ) / mCurrencyPayload.getRates().getMXNRate();
            case USD:
                return quantity / mCurrencyPayload.getRates().getMXNRate();
            case GBP:
                return convertUSD( GBP, quantity ) / mCurrencyPayload.getRates().getMXNRate();
            default:
                return quantity;
        }
    }

    private double convertGBP( int currencyCode, double quantity ){
        switch ( currencyCode ){
            case EUR:
                return convertUSD( EUR, quantity ) /  mCurrencyPayload.getRates().getGBPRate();
            case USD:
                return quantity / mCurrencyPayload.getRates().getGBPRate();
            case MXN:
                return convertUSD( MXN, quantity ) / mCurrencyPayload.getRates().getGBPRate();
            default:
                return quantity;
        }
    }

    private double convertUSD( int currencyCode, double quantity ){
        switch ( currencyCode ){
            case EUR:
                return quantity * mCurrencyPayload.getRates().getEURRate();
            case MXN:
                return quantity * mCurrencyPayload.getRates().getMXNRate();
            case GBP:
                return quantity * mCurrencyPayload.getRates().getGBPRate();
            default:
                return quantity;
        }
    }


    private int parseRadioIdToCurrency( int radioId ){
        switch ( radioId ){
            case R.id.chk_from_eur:case R.id.chk_to_eur:
                return EUR;
            case R.id.chk_from_gbp:case R.id.chk_to_gbp:
                return GBP;
            case R.id.chk_from_mxn:case R.id.chk_to_mxn:
                return MXN;
            case R.id.chk_from_usd:case R.id.chk_to_usd:
                return USD;
            default:
                throw new IllegalStateException("Invalid radio id given " + radioId );
        }
    }


    @Override
    public Loader<CurrencyPayload> onCreateLoader(int i, Bundle bundle) {
        return new CurrencyLoader( MainActivity.this /* context */ );
    }

    @Override
    public void onLoadFinished(Loader<CurrencyPayload> loader, CurrencyPayload currencyPayload) {
        if( currencyPayload == null ){
            Toast.makeText( MainActivity.this, "Currency rates not available", Toast.LENGTH_SHORT).show();
            return;
        }

        mCurrencyPayload = currencyPayload;
        convertCurrency();
    }

    @Override
    public void onLoaderReset(Loader<CurrencyPayload> loader) {
        /* do nothing */
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            case R.id.bttn_convert:



                requestCurrencyRates();

                break;
            default:
                return;
        }
    }
}
