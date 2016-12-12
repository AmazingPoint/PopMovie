package popm.andy.com.popmovie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;


/**
 * Created by andy on 12/11/16.
 */

public class MenuDialog extends DialogFragment{

    private RadioButton rb_en;
    private RadioButton rb_voting;
    private RadioButton rb_zh;
    private RadioButton rb_pop;
    private SharedPreferences settings;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.menu_dialog, null);
        rb_en = (RadioButton) view.findViewById(R.id.lang_en);
        rb_pop = (RadioButton) view.findViewById(R.id.sort_pop);
        rb_zh = (RadioButton) view.findViewById(R.id.lang_zh);
        rb_voting = (RadioButton) view.findViewById(R.id.sort_voting);
        settings = getActivity().getSharedPreferences(BuiltSource.PREFS_NAME, 0);
        loadSteeings(settings);
        builder.setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setLanguage(rb_en.isChecked());
                setSorting(rb_pop.isChecked());
            }
        });
        builder.setView(view);
        return builder.create();
    }


    private void setLanguage(boolean flag){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("lang", flag?"en":"zh");
        editor.apply();
    }

    private void setSorting(boolean flag){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("sort_type", flag?"popular":"top_rated");
        editor.apply();
    }


    private void loadSteeings(SharedPreferences sharedPreferences) {
        String lang_value = sharedPreferences.getString("lang", "en");
        String sort_value = sharedPreferences.getString("sort_type", "popular");
        if(lang_value.equals("en")){
            rb_en.setChecked(true);
            rb_zh.setChecked(false);
        }else{
            rb_en.setChecked(false);
            rb_zh.setChecked(true);
        }
        if(sort_value.equals("popular")){
            rb_pop.setChecked(true);
            rb_voting.setChecked(false);
        }else{
            rb_pop.setChecked(false);
            rb_voting.setChecked(true);
        }
    }
}
