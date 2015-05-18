package com.qunl.qunlerih.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断网络连接是wifi还是手机网络
 * 
 * @author Zhaosha
 *
 */
public class NetUtil {
	private static final String TAG = NetUtil.class.getSimpleName();

	/**
	 * 检查用户是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		// 判断：wifi连接
		boolean isWIFI = isWIFIConnection(context);
		// 判断手机连接
		boolean isMobile = isMobileConnection(context);

		if (!isWIFI && !isMobile) {
			return false;// 此处表示没有网络
		}
		return true;
	}

	/**
	 * 判断mobile连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isMobileConnection(Context context) {
		ConnectivityManager mannager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = mannager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}

		return false;
	}

	/**
	 * 判断wife连接
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
