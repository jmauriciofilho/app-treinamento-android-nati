package br.com.unifor.noticiasunifor.view.activity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.unifor.noticiasunifor.R;
import br.com.unifor.noticiasunifor.adapter.NoticiasAdapter;
import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.event.ListaCriadaEvent;
import br.com.unifor.noticiasunifor.event.NoticiaClicadaEvent;
import br.com.unifor.noticiasunifor.service.NoticiaService;
import br.com.unifor.noticiasunifor.view.activity.DetalhesNoticiaActivity_;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_esporte)
public class EsporteFragment extends Fragment {

    @ViewById(R.id.recycle_view_esporte)
    protected RecyclerView recyclerViewEsporte;

    @Bean
    protected NoticiasAdapter noticiasAdapter;

    @Bean
    protected NoticiaService noticiaService;

    public EsporteFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void afterViews(){
        recyclerViewEsporte.setAdapter(noticiasAdapter);
        noticiaService.requestNoticias();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        noticiasAdapter.refreshEsporte();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(NoticiaClicadaEvent event) {
        Noticia noticia = noticiasAdapter.getNoticiaAt(event.getPosition());

        DetalhesNoticiaActivity_.intent(this).idNoticia(noticia.getId()).start();
    }

    @Subscribe
    public void onEvent (ListaCriadaEvent event){
        noticiasAdapter.refreshEsporte();
    }
}
