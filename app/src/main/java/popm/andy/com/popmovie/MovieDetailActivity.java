package popm.andy.com.popmovie;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends Activity{

    private TextView textView_name;
    private TextView textView_pub_date;
    private TextView textView_vote;
    private TextView textView_desciption;
    private RatingBar ratingBar;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setActionBar(R.layout.actionbar_detail);
        int position = getIntent().getExtras().getInt("position");
        setContent(position);
    }

    private void setActionBar(int layout_id){
        View view = LayoutInflater.from(this).inflate(layout_id, null);
        ActionBar bar = getActionBar();
        bar.setDisplayShowCustomEnabled(true);
        bar.setCustomView(view);
    }

    private void setContent(int position){
        textView_name = (TextView)findViewById(R.id.text_name);
        textView_pub_date = (TextView)findViewById(R.id.text_pub_date);
        textView_vote = (TextView)findViewById(R.id.text_vote);
        textView_desciption = (TextView)findViewById(R.id.text_description);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        picture = (ImageView)findViewById(R.id.picture);
        MovieItem item = MovieDataAdapter.list.get(position);
        System.out.println(item.pic_url);
        Picasso.with(this).load(item.pic_url)
                .placeholder(R.drawable.placeholder).into(picture);
        textView_name.setText(item.title);
        textView_vote.setText(item.vote_average + "");
        textView_desciption.setText(item.description);
        textView_pub_date.setText(item.pub_date);
        ratingBar.setRating(item.vote_average);

    }
}
