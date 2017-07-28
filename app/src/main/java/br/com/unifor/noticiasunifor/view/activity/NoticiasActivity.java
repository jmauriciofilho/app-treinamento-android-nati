package br.com.unifor.noticiasunifor.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.unifor.noticiasunifor.R;
import br.com.unifor.noticiasunifor.adapter.NoticiasAdapter;
import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.event.ListaCriadaEvent;
import br.com.unifor.noticiasunifor.event.NoticiaClicadaEvent;
import br.com.unifor.noticiasunifor.event.RequestBegunEvent;
import br.com.unifor.noticiasunifor.event.RequestFinishedEvent;
import br.com.unifor.noticiasunifor.service.NoticiaService;
import br.com.unifor.noticiasunifor.view.activity.fragments.EsporteFragment;
import br.com.unifor.noticiasunifor.view.activity.fragments.EsporteFragment_;
import br.com.unifor.noticiasunifor.view.activity.fragments.EventoFragment;
import br.com.unifor.noticiasunifor.view.activity.fragments.EventoFragment_;
import br.com.unifor.noticiasunifor.view.activity.fragments.FavoritosFragment_;
import br.com.unifor.noticiasunifor.view.activity.fragments.NoticiaFragment;
import br.com.unifor.noticiasunifor.view.activity.fragments.NoticiaFragment_;

@EActivity(R.layout.activity_noticias)
public class NoticiasActivity extends AppCompatActivity {

    @ViewById(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_geral:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NoticiaFragment_()).commit();
                    return true;
                case R.id.navigation_eventos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EventoFragment_()).commit();
                    return true;
                case R.id.navigation_esportes:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EsporteFragment_()).commit();
                    return true;
                case R.id.navigation_favoritos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FavoritosFragment_()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @AfterViews
    protected void afterViews() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NoticiaFragment_()).commit();
    }

    @Subscribe
    public void onEvent(RequestFinishedEvent event) {
        if (event.getMensagem() != null) {
            Snackbar.make(navigation, event.getMensagem(), Snackbar.LENGTH_SHORT).show();
        }
    }

}
