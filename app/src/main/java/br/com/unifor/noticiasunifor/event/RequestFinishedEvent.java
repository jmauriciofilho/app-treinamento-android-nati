package br.com.unifor.noticiasunifor.event;

/**
 * Created by mauricio on 26/07/17.
 */

public class RequestFinishedEvent {

    private String mensagem;

    public RequestFinishedEvent() {
    }

    public RequestFinishedEvent(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
