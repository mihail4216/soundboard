package com.example.mihail.soundboards.service

import android.support.v4.util.LruCache
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.example.mihail.soundboards.App

/**
 * Created by artem on 31.08.17.
 */

class FileLoader {

    companion object {

        private var mCashe = MyCash()
        private val LOG_TAG = "FileLoader"

        fun load(strUri: String, onSuccess: (String?, ByteArray?) -> Unit) {

            val byteArr = mCashe.get(strUri)
            if (byteArr != null) {
                onSuccess(null, byteArr)
                return
            }

            val request = InputStreamVolleyRequest(Request.Method.GET, strUri,
                Response.ErrorListener { error ->
                    onSuccess("${error.message}", null)
                }) { response ->
                mCashe.put(strUri, response)

                onSuccess(null, response)

            }

            App.instance.requestQueue!!.add(request)

        }

    }

    class MyCash : LruCache<String, ByteArray>((Runtime.getRuntime().maxMemory() / 8).toInt()) {
        override fun sizeOf(key: String, baytes: ByteArray): Int {
            return baytes.size
        }
    }

    class InputStreamVolleyRequest(
        method: Int, mUrl: String, errorListener: Response.ErrorListener, private val onSuccess: (ByteArray) -> Unit/*private val mListener: Response.Listener<ByteArray>,
                                                  errorListener: Response.ErrorListener, params: HashMap<String, String>*/
    ) : Request<ByteArray>(method, mUrl, errorListener) {
        var mParams: Map<String, String>? = null

        //create a static map for directly accessing headers
        var mResponseHeaders: Map<String, String>? = null

        init {
            // this request would never use cache.
            setShouldCache(false)
            mParams = params
        }// TODO Auto-generated constructor stub


        override fun deliverResponse(response: ByteArray) {
            onSuccess(response)
            //mListener.onResponse(response)
        }

        override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray> {

            //Initialise local responseHeaders map with response headers received
            mResponseHeaders = response.headers

            //Pass the response data here
            return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response))
        }
    }
}