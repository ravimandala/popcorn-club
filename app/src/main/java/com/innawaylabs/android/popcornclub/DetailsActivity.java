package com.innawaylabs.android.popcornclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.innawaylabs.android.popcornclub.utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {
    private final String TAG = "POPCORN";

    private final String TITLE = "title";
    private final String OVERVIEW = "overview";

    private int movieId;

    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        this.movieId = getIntent().getIntExtra(Constants.MOVIE_ID, -1);
        if (movieId != -1)
            fetchMoviesDetails();
    }

    // Send out the network request
    private void fetchMoviesDetails() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.add(getString(R.string.tmdb_key_api_key), getString(R.string.tmdb_api_v3_key));
        client.get(getString(R.string.tmdb_api_v3_url_prefix) + movieId,
                params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        tvMovieTitle.setText(response.optString(TITLE));
                        tvOverview.setText(response.optString(OVERVIEW));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d(TAG, "Http request failed with statusCode = " + statusCode + " and error: " + throwable.getMessage());
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });
    }
}