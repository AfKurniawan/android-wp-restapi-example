package com.tiarf.wprestapitest.Services;

import com.tiarf.wprestapitest.Models.Post;
import com.tiarf.wprestapitest.Services.FtiarService;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public interface NotificationService extends Service{

	Override
	public int onStartCommand(Intent intent, int flags, int startId) {

        FtiarService ftiarService = new RestAdapter.Builder()
                .setEndpoint(FtiarService.ENDPOINT)
                .build()
                .create(FtiarService.class);

        ftiarService.getPostsAsync(new Callback<List<Post>>() {
            @Override
            public void success(List<Post> posts, Response response) {
                System.out.println("Success");
                posts.get(0);

	            // Restore preferences
	            SharedPreferences settings = ctxt.getSharedPreferences("myShare", ctxt.MODE_PRIVATE);
	            int last_post_id = settings.getInt("last_post_id", 0);

	            if ( last_post_id != posts.get(0).getId() ) {
	                // We need an Editor object to make preference changes.
	                SharedPreferences.Editor editor = settings.edit();
	                editor.putInt("last_post_id", posts.get(0).getId());

	                // Commit the edits!
	                editor.apply();

	                if ( last_post_id != 0 ) {
	                    // Push notification goes here
	                    NotificationCompat.Builder mBuilder =
	                        new NotificationCompat.Builder(context)
	                            .setContentTitle("Un nouvel article est disponible")
	                            .setContentText(posts.get(0).getTitle().getRendered());
	                    // Sets an ID for the notification
	                    int mNotificationId = 001;
	                    // Gets an instance of the NotificationManager service
	                    NotificationManager mNotifyMgr =
	                            (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
	                    // Builds the notification and issues it.
	                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
	                }

	            }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Fail");
                System.out.println(error);
            }
        });
		
		//TODO do something useful
		return Service.START_NOT_STICKY;
	}


}