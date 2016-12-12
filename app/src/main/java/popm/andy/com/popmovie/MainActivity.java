package popm.andy.com.popmovie;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private GridView gridView;
    private APIWebServices aws;
    private TextView setting_menu;
    private String lang;
    private String sort_type;
    private int page=1;
    private int getLastVisiblePosition = 0,lastVisiblePositionY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar(R.layout.actionbar_home);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid_view);
        checkSettings();
        workByNetwork();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        View v=(View) view.getChildAt(view.getChildCount()-1);
                        int[] location = new  int[2] ;
                        v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                        int y=location [1];

                        if (view.getLastVisiblePosition()!=getLastVisiblePosition
                                && lastVisiblePositionY!=y)//第一次拖至底部
                        {
                            page++;
                            aws = new APIWebServices(getApplicationContext(), gridView);
                            aws.execute(sort_type, lang, page+"");
                            getLastVisiblePosition=view.getLastVisiblePosition();
                            lastVisiblePositionY=y;
                            return;
                        }
//                        else if (view.getLastVisiblePosition()==getLastVisiblePosition
//                                && lastVisiblePositionY==y)//第二次拖至底部
//                        {
//
//                        }
                    }

                    //未滚动到底部，第二次拖至底部都初始化
                    getLastVisiblePosition=0;
                    lastVisiblePositionY=0;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });
    }


    private void checkSettings(){
        SharedPreferences settings =  getSharedPreferences(BuiltSource.PREFS_NAME, 0);
        lang = settings.getString("lang", "en");
        sort_type = settings.getString("sort_type", "top_rated");
        settings.registerOnSharedPreferenceChangeListener(this);
    }

    public void updateData(){
        aws = new APIWebServices(this, gridView);
        aws.movieList.clear();
        aws.execute(sort_type,lang, "1");
    }

    public void workByNetwork(){
        if(BuiltSource.isConnect2Network(this)){
            updateData();
        }else{
            BuiltSource.showNetworkError(this);
        }
    }


    private void setActionBar(int layout_id){
        View view = LayoutInflater.from(this).inflate(layout_id, null);
        setting_menu = (TextView) view.findViewById(R.id.menu_setting);
        ActionBar bar = getActionBar();
        bar.setDisplayShowCustomEnabled(true);
        bar.setCustomView(view);
        setting_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuDialog menuDiglog =  new MenuDialog();
                menuDiglog.show(getFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        lang = sharedPreferences.getString("lang", "en");
        sort_type = sharedPreferences.getString("sort_type", "top_rated");
        updateData();
    }

}
