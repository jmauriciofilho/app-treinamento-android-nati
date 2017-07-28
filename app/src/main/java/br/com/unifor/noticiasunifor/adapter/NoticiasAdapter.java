package br.com.unifor.noticiasunifor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import br.com.unifor.noticiasunifor.R;
import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.service.NoticiaService;
import br.com.unifor.noticiasunifor.view.activity.ItemNoticia;

/**
 * Created by mauricio on 25/07/17.
 */

@EBean
public class NoticiasAdapter extends RecyclerView.Adapter<ItemNoticia>{

    @Bean
    NoticiaService noticiaService;

    public List<Noticia> noticias;

    public NoticiasAdapter(){
        this.noticias = new ArrayList<>();
    }

    @Override
    public ItemNoticia onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);
        ItemNoticia itemNoticia = new ItemNoticia(view);
        return itemNoticia;
    }

    @Override
    public void onBindViewHolder(ItemNoticia holder, int position) {
        holder.bind(noticias.get(position), position);
    }


    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public Noticia getNoticiaAt(int position){
        return noticias.get(position);
    }

    @AfterInject
    public void refreshNoticias() {
        noticias = noticiaService.getNoticias();
        notifyDataSetChanged();
    }

    @AfterInject
    public void refreshEvento(){
        noticias = noticiaService.getNoticiasEventos();
        notifyDataSetChanged();
    }

    @AfterInject
    public void refreshEsporte(){
        noticias = noticiaService.getNoticiasEsporte();
        notifyDataSetChanged();
    }

    @AfterInject
    public void refreshFavoritos(){
        noticias = noticiaService.getNoticiaFavorita();
        notifyDataSetChanged();
    }
}
