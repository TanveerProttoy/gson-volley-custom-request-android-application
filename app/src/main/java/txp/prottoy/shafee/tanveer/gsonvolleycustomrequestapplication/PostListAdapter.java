package txp.prottoy.shafee.tanveer.gsonvolleycustomrequestapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListRecyclerViewHolder> {
    private List<Post> posts;

    public PostListAdapter(List<Post> posts) {
        this.posts = posts;
    }

    protected class PostListRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView userIdTextView, idTextView, titleTextView, bodyTextView;

        PostListRecyclerViewHolder(View view) {
            super(view);
            userIdTextView = view.findViewById(R.id.row_list_text_view0);
            idTextView = view.findViewById(R.id.row_list_text_view1);
            titleTextView = view.findViewById(R.id.row_list_text_view2);
            bodyTextView = view.findViewById(R.id.row_list_text_view3);
        }
    }

    @NonNull
    @Override
    public PostListAdapter.PostListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostListAdapter.PostListRecyclerViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.row_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.PostListRecyclerViewHolder holder, int position) {
        try {
            Post post = posts.get(position);
            holder.userIdTextView.setText(String.valueOf(post.getUserId()));
            holder.idTextView.setText(String.valueOf(post.getId()));
            holder.titleTextView.setText(post.getTitle());
            holder.bodyTextView.setText(post.getBody());
        }
        catch(Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        try {
            return posts.size();
        }
        catch(Exception e) {
            return 0;
        }
    }
}
