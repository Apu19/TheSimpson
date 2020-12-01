package cl.inacap.thesimpson.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.thesimpson.R;
import cl.inacap.thesimpson.dto.Personaje;

public class Adapter extends ArrayAdapter<Personaje> {
    private List<Personaje> personajes;
    private Activity contexto;
    public Adapter(@NonNull Activity context, int resource, @NonNull List<Personaje> objects) {
        super(context, resource, objects);
        this.personajes = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_personajes,null,true);
        TextView namePer = rowView.findViewById(R.id.nombreList);
        TextView fraPer = rowView.findViewById(R.id.fraseList);
        ImageView imgPer = rowView.findViewById(R.id.imgList);
        namePer.setText(this.personajes.get(position).getCharacter());
        fraPer.setText(this.personajes.get(position).getQuote());
        Picasso.get().load(this.personajes.get(position).getImage())
                .resize(300,300).into(imgPer);
        return rowView;
    }
}
