package br.com.unifor.noticiasunifor.service;

import java.util.List;

import br.com.unifor.noticiasunifor.bean.Noticia;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mauricio on 26/07/17.
 */

public interface API {

    @GET("noticias")
    Call<List<Noticia>> getNoticias();

}
