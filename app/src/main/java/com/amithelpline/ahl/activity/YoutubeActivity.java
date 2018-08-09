package com.amithelpline.ahl.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Neeraj on 18-08-2017.
 */

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String VideoId, Title, UserId, Comment;
    TextView tvTitle;
    EditText etComment;
    Button btnVote;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        etComment = (EditText) findViewById(R.id.etComment);
        btnVote = (Button) findViewById(R.id.btnVote);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyCjDrz6lE-E_UHm2dKHYBtuRmrB-djT__E", this);
        Bundle bundle = getIntent().getExtras();

        VideoId = bundle.getString("videoid");
        Title = bundle.getString("title");
        tvTitle.setText(Title);
        sharedpreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = sharedpreferences.getString(Const.UserId, "0");
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment = etComment.getText().toString();
                saveVote();
            }
        });

    }

    public void saveVote() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_save_vote.php?user_id=" + UserId + "&video_id=" + VideoId + "&title=" + Title + "&comment=" + Comment;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {

                                    Toast.makeText(YoutubeActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(YoutubeActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(YoutubeActivity.this, "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            // player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            player.cueVideo(VideoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
           /* String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize("AIzaSyCjDrz6lE-E_UHm2dKHYBtuRmrB-djT__E", this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
