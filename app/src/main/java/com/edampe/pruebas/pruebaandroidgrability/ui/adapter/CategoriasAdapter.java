package com.edampe.pruebas.pruebaandroidgrability.ui.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edampe.pruebas.pruebaandroidgrability.R;
import com.edampe.pruebas.pruebaandroidgrability.ui.item.CategoriasItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by edamp on 8/14/2016.
 */
public class CategoriasAdapter extends ArrayAdapter<CategoriasItem> {

    Activity _Activity;

    int _LayoutResourceId;

    ArrayList<CategoriasItem> data = new ArrayList<CategoriasItem>();

    /**
     * Constructor.
     *
     * @param _Activity
     * @param _LayoutResourceId
     * @param _Data
     */
    public CategoriasAdapter(Activity _Activity, int _LayoutResourceId,
                             ArrayList<CategoriasItem> _Data) {
        super(_Activity, _LayoutResourceId, _Data);
        this._LayoutResourceId = _LayoutResourceId;
        this._Activity = _Activity;
        this.data = _Data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _ViewAdapter = convertView;
        ViewHolder holder = null;

        // Se crea la vista si aun no existe.
        if (_ViewAdapter == null) {
            LayoutInflater inflater = (_Activity).getLayoutInflater();
            _ViewAdapter = inflater.inflate(_LayoutResourceId, parent, false);

            holder = new ViewHolder();

            holder._ImgCategorias = (ImageView) _ViewAdapter.findViewById(R.id.img_categorias);
            holder._NombreCategorias = (TextView) _ViewAdapter.findViewById(R.id.txt_nombre_categorias);

            _ViewAdapter.setTag(holder);
        } else {
            holder = (ViewHolder) _ViewAdapter.getTag();
        }

        // Consulta de las categorias
        CategoriasItem _CategoriasItem = data.get(position);


        holder._NombreCategorias.setText(_CategoriasItem.get_NombreCategoria());
        Picasso.with(_Activity)
                .load(_CategoriasItem.get_ImgCategoria())
                .into(holder._ImgCategorias);
        if (!_Activity.getResources().getBoolean(R.bool.portrait_only)) {

            final float density = _Activity.getResources().getDisplayMetrics().density;

            DisplayMetrics metrics = new DisplayMetrics();
            _Activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int widthPixels = metrics.widthPixels;

            final float scale = ((widthPixels - (100 * density)) / 5);

            holder._ImgCategorias.requestLayout();
            holder._ImgCategorias.getLayoutParams().height = (int) scale;
            holder._ImgCategorias.getLayoutParams().width = (int) scale;

            holder. _NombreCategorias.requestLayout();
            holder._NombreCategorias.getLayoutParams().width = (int) scale;
        }


        return _ViewAdapter;
    }

    static class ViewHolder {
        ImageView _ImgCategorias;
        TextView _NombreCategorias;
    }

}