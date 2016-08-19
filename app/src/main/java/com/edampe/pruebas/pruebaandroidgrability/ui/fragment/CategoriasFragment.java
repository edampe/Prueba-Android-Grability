package com.edampe.pruebas.pruebaandroidgrability.ui.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.edampe.pruebas.pruebaandroidgrability.R;
import com.edampe.pruebas.pruebaandroidgrability.constant.Constants;
import com.edampe.pruebas.pruebaandroidgrability.ui.activity.ListadoAppsActivity;
import com.edampe.pruebas.pruebaandroidgrability.ui.adapter.CategoriasAdapter;
import com.edampe.pruebas.pruebaandroidgrability.ui.item.CategoriasItem;

import java.util.ArrayList;

/**
 * Created by edamp on 8/14/2016.
 */
public class CategoriasFragment extends Fragment {

    ArrayList<CategoriasItem> _CategoriasItemArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.content_main, container, false);

        CategoriasAdapter _Adapter = new CategoriasAdapter(getActivity(), R.layout.item_categorias,
                setDatosLista());

        if(getResources().getBoolean(R.bool.portrait_only)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            ListView _ListView = (ListView) root.findViewById(R.id.list_categorias);

            _ListView.setAdapter(_Adapter);

            _ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {

                    Intent intent = new Intent(getActivity(), ListadoAppsActivity.class);
                    intent.putExtra(Constants.CODIGO, _CategoriasItemArray.get(position).get_CodigoCategoria());
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
                }

            });

        }else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            GridView _GridView = (GridView) root.findViewById(R.id.grid_categorias);
            _GridView.setAdapter(_Adapter);
            _GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    Intent intent = new Intent(getActivity(), ListadoAppsActivity.class);
                    intent.putExtra(Constants.CODIGO, _CategoriasItemArray.get(position).get_CodigoCategoria());
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.transition_in, R.anim.transition_out);
                }

            });


        }
        return root;
    }

    private  ArrayList<CategoriasItem> setDatosLista(){
       _CategoriasItemArray = new ArrayList<>();

        CategoriasItem _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_1),
                R.drawable.ic_1,
                getActivity().getResources().getString(R.string.str_1));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_2),
                R.drawable.ic_2,
                getActivity().getResources().getString(R.string.str_2));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_3),
                R.drawable.ic_3,
                getActivity().getResources().getString(R.string.str_3));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_4),
                R.drawable.ic_4,
                getActivity().getResources().getString(R.string.str_4));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_5),
                R.drawable.ic_5,
                getActivity().getResources().getString(R.string.str_5));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_6),
                R.drawable.ic_6,
                getActivity().getResources().getString(R.string.str_6));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_7),
                R.drawable.ic_7,
                getActivity().getResources().getString(R.string.str_7));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_8),
                R.drawable.ic_8,
                getActivity().getResources().getString(R.string.str_8));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_9),
                R.drawable.ic_9,
                getActivity().getResources().getString(R.string.str_9));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_10),
                R.drawable.ic_10,
                getActivity().getResources().getString(R.string.str_10));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_11),
                R.drawable.ic_11,
                getActivity().getResources().getString(R.string.str_11));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_12),
                R.drawable.ic_12,
                getActivity().getResources().getString(R.string.str_12));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_13),
                R.drawable.ic_13,
                getActivity().getResources().getString(R.string.str_13));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_14),
                R.drawable.ic_14,
                getActivity().getResources().getString(R.string.str_14));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_15),
                R.drawable.ic_15,
                getActivity().getResources().getString(R.string.str_15));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_16),
                R.drawable.ic_16,
                getActivity().getResources().getString(R.string.str_16));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_17),
                R.drawable.ic_17,
                getActivity().getResources().getString(R.string.str_17));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_18),
                R.drawable.ic_18,
                getActivity().getResources().getString(R.string.str_18));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_19),
                R.drawable.ic_19,
                getActivity().getResources().getString(R.string.str_19));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_20),
                R.drawable.ic_20,
                getActivity().getResources().getString(R.string.str_20));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_21),
                R.drawable.ic_21,
                getActivity().getResources().getString(R.string.str_21));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_22),
                R.drawable.ic_22,
                getActivity().getResources().getString(R.string.str_22));
        _CategoriasItemArray.add(_CategoriasItem);

        _CategoriasItem = new CategoriasItem(getActivity().getResources().getString(R.string.cod_23),
                R.drawable.ic_23,
                getActivity().getResources().getString(R.string.str_23));
        _CategoriasItemArray.add(_CategoriasItem);



        return _CategoriasItemArray;
    }

}
