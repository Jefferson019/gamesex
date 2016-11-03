package br.com.gamesex.gamesex;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jeffe on 17/10/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public ProgressDialog mProgressoDialogo;

    public void showProgressoDialogo(){

            if(mProgressoDialogo == null){

                mProgressoDialogo = new ProgressDialog(this);
                mProgressoDialogo.setMessage(getString(R.string.loading));
                mProgressoDialogo.setIndeterminate(true);
            }

        mProgressoDialogo.show();
    }


    public void hideProgressoDialogo(){

        if(mProgressoDialogo != null && mProgressoDialogo.isShowing());
        mProgressoDialogo.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressoDialogo();
    }
}
