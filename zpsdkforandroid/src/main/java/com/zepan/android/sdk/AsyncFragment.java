package com.zepan.android.sdk;import java.util.Iterator;import org.json.JSONException;import org.json.JSONObject;import android.app.Dialog;import android.os.Bundle;import android.support.annotation.Nullable;import android.util.Log;import com.android.volley.DefaultRetryPolicy;import com.android.volley.Request.Method;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.JsonObjectRequest;import com.android.volley.toolbox.Volley;import com.zepan.android.widget.LoadingDialog;/** * 如果当前Fragment中需要处理网络请求，请使用此类。 * */public class AsyncFragment extends BaseFragment {	// ===================================属性实例化开始===================	/**	 * volley请求队列RequestQueue。	 * */	private RequestQueue mRequestQueue = null;	/**	 * 等待框视图	 * */	private Dialog mLoadingDialog = null;	// ===================================属性实例化结束===================	// ==================================自定义方法开始======================	/**	 * 处理json网络请求post。	 * 	 * @param url	 *            请求Url	 * @param paramJson	 *            请求参数	 * @param method	 *            请求方法	 * @see Method.GET,Method.POST	 * @param callBack	 *            处理请求结果	 * */	protected void request(final String url, JSONObject paramJson, int method,			final IRequestCallBack callBack) {		// 如果当前无网络，显示提示信息，结束处理。		if (!isNetworkAvailable()) {			return;		}		// 执行请求前的处理		preRequest();		// 打印请求印象。		printLog("请求数据：url=" + url + ",paramJson=" + paramJson);		// 如果是get请求，拼接参数到url		String newUrl = url;		if (Method.GET == method) {			newUrl = convertJsonToGetParam(url, paramJson);		}		// 传入请求处理		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method,				newUrl, paramJson, new Response.Listener<JSONObject>() {					@Override					public void onResponse(JSONObject response) {						// 打印返回数据。						printLog("返回数据：response=" + response);						// 执行请求结束后的处理						postSuccess(response, callBack);						// 请求成功后的处理						// if (callBack != null) {						// callBack.onResponse(response);						// }					}				}, new Response.ErrorListener() {					@Override					public void onErrorResponse(VolleyError error) {						Log.e(TAG, "返回数据：error="								+ (error == null ? null : error.getMessage()));						Log.e(TAG, "可能原因如下：1､请求URL不存在；\n2､服务器返回未返回任何数据");						// 请求后的处理						postError(url, callBack, error);					}				});		// 设置超时时间10秒		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1,				1.0f));		// 设置请求标识，取消请求时需要		jsonObjectRequest.setTag(TAG);		// 添加到请求队列		mRequestQueue.add(jsonObjectRequest);	}		/**	 * 自定义等待对话框，默认返回ZPSDK自定义dialog	 * @see LoadingDialog	 * */	protected Dialog defineDialog(){		return new LoadingDialog(getActivity());	}	/**	 * 网络请求开始/过程中执行的处理，默认展示等待视图，用户可重写此方法。	 * */	protected void preRequest() {		// 实例化等待框控件并显示		if (mLoadingDialog == null) {			mLoadingDialog = defineDialog();		}		if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {			mLoadingDialog.show();		}	}	/**	 * 请求成功后的处理	 * 	 * @param response	 *            服务器返回的json	 * @param callBack	 *            回调	 * @see IRequestCallBack	 */	protected void postSuccess(JSONObject response, IRequestCallBack callBack) {		// 如果mLoadingDialog不为空并且为显示状态，则隐藏。		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {			mLoadingDialog.hide();		}	}	/**	 * 请求成功后的处理	 * 	 * @param response	 *            服务器返回的json	 * @param callBack	 *            回调	 * @param error	 *            错误信息	 * @see IRequestCallBack	 * 	 */	protected void postError(String url, IRequestCallBack callBack,			VolleyError error) {		// 如果mLoadingDialog不为空并且为显示状态，则隐藏。		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {			mLoadingDialog.hide();		}		// 如果当前是debug状态，打印返回数据。		if (isDebug()) {			// 从url中获得action			String action = url.substring(url.lastIndexOf("/") + 1);			// 如果action不为null并且当前是debug状态，调用测试数据			if (action != null) {				String debugResultStr = TestDataFactory.getValue(action);				if (debugResultStr != null) {					try {						Log.e(TAG, "当前正使用测试数据:" + debugResultStr);						JSONObject debugResultJson = new JSONObject(								debugResultStr);						callBack.onResponse(debugResultJson);						return;					} catch (JSONException e) {						printLog("返回数据(测试数据)解析成json报错，请检查文件testdata.properties中测试json格式是否正确。");					}				} else {					Log.i(TAG, "testdata.properties中不存在方法" + action							+ "对应的测试数据。");				}			}		}	}	/**	 * 将json格式的参数转换为get中带参数的url	 * 	 * @param url	 * @param paramJson	 *            json	 * @return 转换后的url	 */	private String convertJsonToGetParam(String url, JSONObject paramJson) {		if (paramJson != null) {			url += "?";		} else {			return url;		}		Iterator<String> keyIt = paramJson.keys();		try {			while (keyIt.hasNext()) {				String key = keyIt.next();				String value = paramJson.getString(key);				url += (key + "=" + value + "&");			}		} catch (JSONException e) {			e.printStackTrace();		}		return url.substring(0, url.length() - 1);	}	// ==================================自定义方法结束======================	// ===================================生命周期开始=====================	@Override	public void onCreate(Bundle savedInstanceState) {		mRequestQueue = Volley.newRequestQueue(getActivity());		super.onCreate(savedInstanceState);	}	@Override	public void onActivityCreated(@Nullable Bundle savedInstanceState) {		// TODO Auto-generated method stub		super.onActivityCreated(savedInstanceState);	}	/**	 * 在onStop中取消所有请求。	 * */	@Override	public void onStop() {		super.onStop();		// 取消当前界面所以请求。		if (mRequestQueue != null) {			mRequestQueue.cancelAll(TAG);			if (isDebug()) {				Log.i(TAG, "标识为" + TAG + "的请求已结束");			}		}		// dismiss等待框		if (mLoadingDialog != null) {			mLoadingDialog.dismiss();		}	}	// ===================================生命周期结束======================}