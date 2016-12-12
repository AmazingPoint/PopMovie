package popm.andy.com.popmovie;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class APIWebServices extends AsyncTask <String, Integer, List<MovieItem>>{

    private int page;
    private Context context;
    private Uri.Builder mBuilder;
    private HttpURLConnection mConnection;
    private InputStream mInputStream;
    public static List<MovieItem> movieList;
    private MovieDataAdapter adapter;
    private GridView containerView;

    public APIWebServices(Context context, GridView containerView){
        movieList = new ArrayList<>();
        this.context = context;
        this.containerView = containerView;
    }

    @Override
    protected List<MovieItem> doInBackground(String... params) {
        try {
            URL url = buildUrl(params);
            setStreamData(url);
            String jsonStr = decodeStreamData(mInputStream);
            movieList = parseJson(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mConnection.disconnect();
        }
        return movieList;
    }

    @Override
    protected void onPostExecute(List<MovieItem> movieList) {

        if(movieList != null){
            if(page == 1){
                adapter = new MovieDataAdapter(context, movieList);
                containerView.setAdapter(adapter);
            }else{
                adapter = (MovieDataAdapter) containerView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private URL buildUrl(String... params) throws Exception{
        mBuilder= new Uri.Builder();
        mBuilder.scheme(BuiltSource.MOVIE_PORTAL).authority(BuiltSource.MOVIE_BASE_URL).
                appendPath(BuiltSource.MOVIE_API_VERSION).appendPath("movie").
                appendPath(params[0]).appendQueryParameter("language", params[1]).
                appendQueryParameter("page", params[2]).
                appendQueryParameter("api_key", BuiltSource.MOVIE_API_KEY).build();
        URL url = new URL(mBuilder.toString());
        return url;
    }

    private void setStreamData(URL url) throws Exception{
        mConnection = (HttpURLConnection)url.openConnection();
        mConnection.setRequestMethod("GET");
        mConnection.connect();
        mInputStream = mConnection.getInputStream();
    }

    private String decodeStreamData(InputStream streamData) throws Exception{
        if(streamData == null){return null;}
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(streamData));
        if(reader == null){return null;}
        String line;
        while((line = reader.readLine()) != null){
            buffer.append(line + "\n");
        }
        reader.close();
        return buffer.toString();
    }

    private List<MovieItem> parseJson(String jsonStr) throws Exception{
         JSONObject movieJson = new JSONObject(jsonStr);
         page = movieJson.getInt("page");
         JSONArray jsonArray = movieJson.getJSONArray("results");
         JSONObject row;
         String title;
         String pic_url;
         String description;
         float vote_averrage;
         String pub_date;
         if(page != 1){
             movieList = MovieDataAdapter.list;
         }
         for(int i = 0; i < jsonArray.length(); i++){
             row = jsonArray.getJSONObject(i);
             title = row.getString("title");
             pic_url = row.getString("poster_path");
             vote_averrage = (float)row.getDouble("vote_average");
             description = row.getString("overview");
             pub_date = row.getString("release_date");
             movieList.add(new MovieItem(title, pic_url, vote_averrage, description, pub_date));
         }
         return movieList;
     }
}
