package br.com.unifor.noticiasunifor.service;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.event.ListaCriadaEvent;
import br.com.unifor.noticiasunifor.event.RequestBegunEvent;
import br.com.unifor.noticiasunifor.event.RequestFinishedEvent;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mauricio on 26/07/17.
 */

@EBean
public class NoticiaService {

    private static API api;

    public NoticiaService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo6508156.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public void salvarNoticias(List<Noticia> noticias){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(noticias);
        realm.commitTransaction();
    }

    public void favoritar(int idNoticia) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Noticia noticia = realm
                .where(Noticia.class)
                .equalTo("id", idNoticia)
                .findFirst();

        Noticia noticiaCopia = realm.copyFromRealm(noticia);
        Number max = realm.where(Noticia.class).max("id");
        Integer nextId = 0;
        nextId = max.intValue() + 1;
        noticiaCopia.setId(nextId);
        noticiaCopia.setTipo("FAVORITO");
        realm.copyToRealmOrUpdate(noticiaCopia);
        realm.commitTransaction();

    }

    public void desfavoritar(int idNoticia){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Noticia noticia = realm
                .where(Noticia.class)
                .equalTo("id", idNoticia)
                .findFirst();
        noticia.deleteFromRealm();
        realm.commitTransaction();
    }

    public boolean mesmoTitulo(String titulo){
        Realm realm = Realm.getDefaultInstance();
        long numero = realm
                .where(Noticia.class)
                .equalTo("titulo", titulo).count();

        return numero > 1;
    }

    public void requestNoticias(){
        EventBus.getDefault().post(new RequestBegunEvent());
        api.getNoticias().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if (response.code() == 200) {
                    List<Noticia> noticias = response.body();
                    salvarNoticias(noticias);
                    EventBus.getDefault().post(new ListaCriadaEvent());
                }else{
                    EventBus.getDefault().post(new RequestFinishedEvent("Não foi 200"));
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                EventBus.getDefault().post(new RequestFinishedEvent("Falha na requisição!"));
            }
        });
    }

    public Noticia getNoticia(Integer idNoticia){
        Realm realm = Realm.getDefaultInstance();
        Noticia noticia = realm
                .where(Noticia.class)
                .equalTo("id", idNoticia)
                .findFirst();
        if (noticia != null) {
            return realm.copyFromRealm(noticia);
        } else {
            return null;
        }
    }

    public List<Noticia> getNoticias(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Noticia> noticias = realm
                .where(Noticia.class)
                .equalTo("tipo", Noticia.TIPO_NOTICIA).findAll();

        if (noticias != null) {
            return realm.copyFromRealm(noticias);
        } else {
            return null;
        }
    }

    public List<Noticia> getNoticiasEventos(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Noticia> noticiasEventos = realm
                .where(Noticia.class)
                .equalTo("tipo", Noticia.TIPO_EVENTO).findAll();

        if (noticiasEventos != null) {
            return realm.copyFromRealm(noticiasEventos);
        } else {
            return null;
        }
    }

    public List<Noticia> getNoticiasEsporte(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Noticia> noticiasEsporte = realm
                .where(Noticia.class)
                .equalTo("tipo", Noticia.TIPO_ESPORTIVO).findAll();

        if (noticiasEsporte != null) {
            return realm.copyFromRealm(noticiasEsporte);
        } else {
            return null;
        }
    }

    public List<Noticia> getNoticiaFavorita(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Noticia> noticiasFavoritas = realm
                .where(Noticia.class)
                .equalTo("tipo", Noticia.TIPO_FAVORITO).findAll();

        if (noticiasFavoritas != null) {
            return realm.copyFromRealm(noticiasFavoritas);
        } else {
            return null;
        }
    }
}
