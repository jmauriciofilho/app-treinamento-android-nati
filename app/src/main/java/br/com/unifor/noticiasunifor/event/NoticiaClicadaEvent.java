package br.com.unifor.noticiasunifor.event;

/**
 * Created by mauricio on 26/07/17.
 */

public class NoticiaClicadaEvent {

    private int position;

    public NoticiaClicadaEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
