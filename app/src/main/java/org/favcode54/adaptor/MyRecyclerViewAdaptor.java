package org.favcode54.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.favcode54.R;
import org.favcode54.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adaptor
 */
public class MyRecyclerViewAdaptor extends RecyclerView.Adapter<MyRecyclerViewAdaptor.ViewHolder> {
    // A list of posts
    private List<Post> posts;
    private Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

    public MyRecyclerViewAdaptor(ArrayList<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(posts.get(i).getThumbnailUrl())
                .centerCrop()
                .into(viewHolder.thumbnailImageView);

        viewHolder.title.loadData(posts.get(i).getTitle(), "text/html; charset=UTF-8", null);

        int count = posts.get(i).getCommentCount();
        String countText = (count == 1 || count == 0) ? count + " Comment" : count + " Comments";
        viewHolder.commentCount.setText(countText);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(posts.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        WebView title;
        TextView commentCount;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (WebView) itemView.findViewById(R.id.title);
            title.getSettings().setJavaScriptEnabled(true);
            title.setWebChromeClient(new WebChromeClient());

            // Load and display HTML content
            // Use "charset=UTF-8" to support non-English language

            commentCount = (TextView) itemView.findViewById(R.id.comment_count);

        }

    }
}
