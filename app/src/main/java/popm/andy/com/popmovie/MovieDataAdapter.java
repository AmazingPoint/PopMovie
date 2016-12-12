package popm.andy.com.popmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;


/**
 * Created by andy on 12/7/16.
 */

public class MovieDataAdapter extends BaseAdapter {
    private Context context;
    public static List<MovieItem> list;

    public  MovieDataAdapter(Context context, List<MovieItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view == null){
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            bindingHolder(holder, view);
        }else{
            holder = (Holder)view.getTag();
        }
        setHolder(holder, i);
        return view;
    }


    public class Holder{
        public TextView titleTextView;
        public ImageView pictureView;
        public TextView voteAvTextView;
        public RatingBar ratingBar;
    }

    private void bindingHolder(Holder holder, View view){
        holder.titleTextView = (TextView)view.findViewById(R.id.name);
        holder.pictureView = (ImageView)view.findViewById(R.id.picture);
        holder.ratingBar = (RatingBar)view.findViewById(R.id.rating);
        holder.voteAvTextView = (TextView)view.findViewById(R.id.vote_text);
        view.setTag(holder);
    }

    private void setHolder(Holder holder, int i){
        String url = list.get(i).pic_url;
        String title = list.get(i).title;
        holder.titleTextView.setText(title);
        Picasso.with(context).load(url).placeholder(R.drawable.placeholder).into(holder.pictureView);
        holder.ratingBar.setRating(list.get(i).vote_average / 2);
        holder.voteAvTextView.setText(Float.toString(list.get(i).vote_average));
    }
}
