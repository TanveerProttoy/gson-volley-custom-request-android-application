package txp.prottoy.shafee.tanveer.gsonvolleycustomrequestapplication;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class GsonGetRequest<T> extends Request<T> {
    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;

    public GsonGetRequest(Gson gson, String url, Type type,
                          Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success((T) gson.fromJson(new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers)), type),
                    HttpHeaderParser.parseCacheHeaders(response));
        }
        catch(UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}