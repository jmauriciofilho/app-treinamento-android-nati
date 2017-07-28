package br.com.unifor.noticiasunifor.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mauricio on 26/07/17.
 */

public class Noticia extends RealmObject{

    @PrimaryKey
    private Integer id;

    private String dataPublicacao;
    private String urlImg;
    private String titulo;
    private String corpo;
    private String resumo;
    private String tipo;
    private Integer registro;
    private Integer totalRegistro;

    public static final String TIPO_NOTICIA = "NOTICIA";
    public static final String TIPO_EVENTO = "EVENTO";
    public static final String TIPO_ESPORTIVO = "ESPORTIVO";
    public static final String TIPO_FAVORITO = "FAVORITO";

    public Noticia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getRegistro() {
        return registro;
    }

    public void setRegistro(Integer registro) {
        this.registro = registro;
    }

    public Integer getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(Integer totalRegistro) {
        this.totalRegistro = totalRegistro;
    }
}
