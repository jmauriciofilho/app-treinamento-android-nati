package br.com.unifor.noticiasunifor.view.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import br.com.unifor.noticiasunifor.R;
import br.com.unifor.noticiasunifor.adapter.NoticiasAdapter;
import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.event.CompartilharEvent;
import br.com.unifor.noticiasunifor.event.RequestFinishedEvent;
import br.com.unifor.noticiasunifor.service.NoticiaService;

@EActivity(R.layout.activity_detalhes_noticia)
public class DetalhesNoticiaActivity extends AppCompatActivity {

    @Bean
    NoticiaService noticiaService;

    @Bean
    NoticiasAdapter noticiasAdapter;

    @ViewById(R.id.image_noticia)
    protected ImageView imageView;

    @ViewById(R.id.titulo_noticia)
    protected TextView titulo;

    @ViewById(R.id.corpo_noticia)
    protected TextView corpo;

    @Extra
    protected Integer idNoticia;

    private Noticia noticia;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @AfterViews
    protected void afterViews() {
        noticia = noticiaService.getNoticia(idNoticia);
        Picasso.with(imageView.getContext())
                .load(noticia.getUrlImg())
                .resize(500, 400)
                .into(imageView);
        titulo.setText(noticia.getTitulo());
        Spanned spanned = stripHtml(noticia.getCorpo());
        corpo.setText(spanned);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (!noticiaService.mesmoTitulo(noticia.getTitulo())
                || Objects.equals(noticia.getTipo(), Noticia.TIPO_FAVORITO)) {
            //inflater.inflate(R.menu.menu_detalhes_noticias, menu);

            if (Objects.equals(noticia.getTipo(), Noticia.TIPO_FAVORITO)) {
                inflater.inflate(R.menu.menu_detalhes_noticias, menu);
                //menu.getItem(0).setIcon(R.drawable.ic_star_black_24dp);
            }else {
                inflater.inflate(R.menu.menu_detalhes_nao_favorito, menu);
                //menu.getItem(0).setIcon(R.drawable.ic_star_border_black_24dp);
            }
        }else{
            inflater.inflate(R.menu.menu_detalhe_favorito, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.favoritar:
                favoritar();
                invalidateOptionsMenu();
                break;
            case R.id.desfavoritar:
                desfavoritar();
                finish();
                break;
            case R.id.eh_favorito:
                Snackbar.make(imageView, "Para desfavoritar acesse os favoritos!", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.compartilhar:
                compartilhar();
                break;
            default:
                Log.d("Return:", "Eu não conheço esse Menu");
                return false;
        }
        return true;
    }


    private void favoritar() {
        noticiaService.favoritar(idNoticia);
    }

    private void desfavoritar(){
        noticiaService.desfavoritar(idNoticia);
    }

    @Subscribe
    public void onEvent(RequestFinishedEvent event) {
        if (event.getMensagem() != null) {
            Snackbar.make(titulo, event.getMensagem(), Snackbar.LENGTH_SHORT).show();
        }
    }


    public void compartilhar(){

        Noticia noticia = noticiaService.getNoticia(idNoticia);

        String resumeNoticia = noticia.getResumo();

        Log.d("Teste", resumeNoticia);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, resumeNoticia);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    private Spanned stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
}
