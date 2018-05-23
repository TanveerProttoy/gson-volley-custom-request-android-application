package txp.prottoy.shafee.tanveer.gsonvolleycustomrequestapplication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonNetwork {
    private static final int SOCKET_TIME_OUT = 60000;
    private static SingletonNetwork singletonNetwork;
    private RequestQueue requestQueue;
    private Context context;

    private SingletonNetwork(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonNetwork getInstance(Context context) {
        if(singletonNetwork == null) {
            singletonNetwork = new SingletonNetwork(context);
        }
        return singletonNetwork;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIME_OUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = getRequestQueue();
        requestQueue.getCache().clear();
        requestQueue.add(request);
    }
}
