package popm.andy.com.popmovie;

/**
 * Created by andy on 12/7/16.
 */

public class MovieItem {
    public String title;
    public String pic_url;
    public float vote_average;
    public String description;
    public String pub_date;

    MovieItem(String name, String pic_url, float vote_average, String description,String pub_date){
        this.title = name;
        this.pic_url = BuiltSource.MOVIE_PICTURE_BASE_URL + pic_url;
        this.vote_average = vote_average;
        this.description = description;
        this.pub_date = pub_date;
    }
}
