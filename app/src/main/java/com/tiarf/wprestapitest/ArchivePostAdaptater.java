package com.tiarf.wprestapitest;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ArchivePostAdaptater extends RecyclerView.Adapter<ArchivePostAdaptater.ArchivePostsViewHolder> {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ArchivePostsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView postTitle;
        TextView postDate;
        ImageView postPhoto;
        Button postReadMoreBtn;

        ArchivePostsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.post_card);
            postTitle = (TextView)itemView.findViewById(R.id.post_title);
            postDate = (TextView)itemView.findViewById(R.id.post_date);
            postPhoto = (ImageView)itemView.findViewById(R.id.post_photo);
            postReadMoreBtn = (Button)itemView.findViewById(R.id.readMoreBtn);
        }
    }

    private List<Post> posts;

    private FtiarService ftiarService = new RestAdapter.Builder()
            .setEndpoint(FtiarService.ENDPOINT)
            .build()
            .create(FtiarService.class);

    private Context context;

    public ArchivePostAdaptater(List<Post> posts, Context context){
        this.posts = posts;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArchivePostsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_posts, viewGroup, false);
        return new ArchivePostsViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( final ArchivePostsViewHolder holder, int i ) {
        final Post currentPost = this.posts.get(i);
        holder.postTitle.setText( currentPost.getTitle().getRendered() );

        // Format the post date
        String postDate = currentPost.getDate();
        holder.postDate.setText( currentPost.getI18nFormatedDate(postDate, "dd MMMM yyyy", Locale.FRANCE) );

        final Context ctxt = this.context; // Save the context locally

        // Get Media Object with a call to the REST API
        this.ftiarService.getMediaAsync(currentPost.getFeatured_media(), new Callback<Media>() {

            @Override
            public void success(Media media, Response response) {
                // Use Picasso lib to display an Image based on an URL
                Picasso.with(ctxt).load(media.getMedia_details().getSizes().getThumbnail().getSource_url())
                        .into(holder.postPhoto);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println( error );
            }
        });

        // Handle read more click
        holder.postReadMoreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText( ctxt, "Bonne lecture", Toast.LENGTH_SHORT).show();

                // Launch a single post activity
                Intent intentSinglePost = new Intent( ctxt, SinglePostActivity.class );
                intentSinglePost.putExtra("post_id", currentPost.getId() );
                intentSinglePost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctxt.startActivity(intentSinglePost);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}