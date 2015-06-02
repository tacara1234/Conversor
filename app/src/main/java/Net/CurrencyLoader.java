package Net;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import retrofit.RetrofitError;

public class CurrencyLoader extends AsyncTaskLoader<CurrencyPayload> {

	private CurrencyPayload mResult;

	public CurrencyLoader(Context context) {
        super(context);
	}

	@Override
	public void onStartLoading() {
		if (mResult != null) {
			deliverResult(mResult);
		}
		if (takeContentChanged() || mResult == null) {
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public CurrencyPayload loadInBackground(){
        try{
            CurrencyApiInterface apiInterface = CurrencyApiSingleton.getApiInterfaceInstance();
            return apiInterface.getCurrency();
        }catch( RetrofitError e ){
            e.printStackTrace();
            return null; // Nothing to do
        }catch ( Exception e ){
            e.printStackTrace();
            return null; // Nothing to do
        }
    }

	@Override
	public void deliverResult(CurrencyPayload data) {
		if (isReset()) {
				/*
				 * Since this is a simple list, we don't need to release any
				 * resources
				 */
			return;
		}

			/*
			 * We don't hold a reference to old data because we don't need to
			 * release any resources
			 */

		mResult = data;

		if (isStarted()) {
			super.deliverResult(data);
		}

			/*
			 * Since this is a simple list, we don't need to release any
			 * resources
			 */
	}

	@Override
	protected void onReset() {
		onStopLoading(); // Ensure the loader has been stopped.
		if (mResult != null) {

				/*
				 * Since this is a simple list, we don't need to release any
				 * resources
				 */
			mResult = null;
		}

			/* We don't use an observer, so we skip unregistering the observer */
	}

	@Override
	public void onCanceled(CurrencyPayload data) {
		super.onCanceled(data);

			/*
			 * Since this is a simple list, we don't need to release any
			 * resources
			 */
	}

}	