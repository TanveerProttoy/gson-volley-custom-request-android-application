package txp.prottoy.shafee.tanveer.gsonvolleycustomrequestapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreatePostFragment.OnFragmentInteractionListener {
    private RecyclerView recyclerView;
    private Gson gson;
    private List<Post> postList;
    private PostListAdapter postListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
        postList = new ArrayList<>();
        recyclerView = findViewById(R.id.main_recycler_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.main_fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postListAdapter = new PostListAdapter(postList);
        recyclerView.setAdapter(postListAdapter);
        floatingActionButton.setOnClickListener(fabOcl);
        getData();
    }

    @Override
    public void onAddPost(Post post) {
        postList.add(post);
        postListAdapter.notifyItemInserted(postList.indexOf(post));
    }

    private void getData() {
        GsonGetRequest<List<Post>> gsonGetRequest = new GsonGetRequest<>(gson, Constant.URI,
                new TypeToken<ArrayList<Post>>() { }.getType(), new Response.Listener<List<Post>>() {
            @Override
            public void onResponse(List<Post> posts) {
                postList.addAll(posts);
                postListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        SingletonNetwork.getInstance(getApplicationContext()).addToRequestQueue(gsonGetRequest);
    }

    private View.OnClickListener fabOcl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CreatePostFragment.newInstance().show(getSupportFragmentManager(), "CreatePostFragment");
        }
    };
}
