package br.com.unifor.noticiasunifor.view.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.unifor.noticiasunifor.R;
import br.com.unifor.noticiasunifor.bean.Noticia;
import br.com.unifor.noticiasunifor.event.NoticiaClicadaEvent;

/**
 * Created by mauricio on 25/07/17.
 */

public class ItemNoticia extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;

    public ItemNoticia(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        textView = itemView.findViewById(R.id.info_text);
    }

    public void bind(final Noticia noticia, final int position){
        Picasso.with(imageView.getContext()).load(noticia.getUrlImg()).into(imageView);
        textView.setText(noticia.getTitulo());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TITULO DA NOTICIA", noticia.getTitulo());
                EventBus.getDefault().post(new NoticiaClicadaEvent(position));
            }
        });
    }
}
