package txp.prottoy.shafee.tanveer.gsonvolleycustomrequestapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CreatePostFragment extends DialogFragment {
    private EditText userIdEditText, titleEditText, bodyEditText;
    private Gson gson;
    private OnFragmentInteractionListener mListener;

    public CreatePostFragment() {
    }

    public static CreatePostFragment newInstance() {
        return new CreatePostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        userIdEditText = view.findViewById(R.id.create_edit_text0);
        titleEditText = view.findViewById(R.id.create_edit_text1);
        bodyEditText = view.findViewById(R.id.create_edit_text2);
        Button submitButton = view.findViewById(R.id.create_btn);
        submitButton.setOnClickListener(submitOcl);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void postData(int userId, String title, String body) {
        Post postCreate = new Post();
        postCreate.setUserId(userId);
        postCreate.setTitle(title);
        postCreate.setBody(body);
        GsonPostRequest<Post> gsonPostRequest = new GsonPostRequest<>(gson, Constant.URI,
                postCreate, new TypeToken<Post>() { }.getType(), new Response.Listener<Post>() {
            @Override
            public void onResponse(Post post) {
                mListener.onAddPost(post);
                CreatePostFragment.this.getDialog().dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonNetwork.getInstance(getActivity().getApplicationContext()).addToRequestQueue(gsonPostRequest);
    }

    public interface OnFragmentInteractionListener {
        void onAddPost(Post post);
    }

    private View.OnClickListener submitOcl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                postData(Integer.parseInt(userIdEditText.getText().toString()), titleEditText.getText().toString(), bodyEditText.getText().toString());
            }
            catch(NumberFormatException n) {

            }
        }
    };
}
