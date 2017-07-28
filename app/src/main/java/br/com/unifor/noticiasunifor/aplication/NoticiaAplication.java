package br.com.unifor.noticiasunifor.aplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by mauricio on 26/07/17.
 */

public class NoticiaAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }

}
