package fr.teiki.aremiti.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import fr.teiki.aremiti.R;

/**
 * Created by Antoine GALTIER on 17/03/2020.
 */

public class Utils {

	public static boolean isNetworkAvailable(Context context) {
		if (context == null)
			return false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert connectivityManager != null;
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isAvailable();
	}

	public static AlertDialog displaySimpleAlert(Context activity, String title, String message) {
		if (activity == null)
			return null;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return alertDialog;
	}
}
