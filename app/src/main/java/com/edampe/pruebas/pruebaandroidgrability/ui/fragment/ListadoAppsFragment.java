package com.edampe.pruebas.pruebaandroidgrability.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edampe.pruebas.pruebaandroidgrability.R;
import com.edampe.pruebas.pruebaandroidgrability.cache.CacheDatosApps;
import com.edampe.pruebas.pruebaandroidgrability.constant.Constants;
import com.edampe.pruebas.pruebaandroidgrability.io.adapter.AppsApiAdapter;
import com.edampe.pruebas.pruebaandroidgrability.io.interfaces.AppsMetods;
import com.edampe.pruebas.pruebaandroidgrability.io.item.GetFreeApplicationBin;
import com.edampe.pruebas.pruebaandroidgrability.ui.activity.DetalleAppActivity;
import com.edampe.pruebas.pruebaandroidgrability.ui.activity.ListadoAppsActivity;
import com.edampe.pruebas.pruebaandroidgrability.ui.item.DatosAppsItem;
import com.edampe.pruebas.pruebaandroidgrability.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListadoAppsFragment extends Fragment {

    // Variable para debug
    public static final String LOG_TAG = ListadoAppsFragment.class.getName();

    private String _Codigo;

    private AppsMetods _Servicio;

    public static ArrayList<DatosAppsItem> _ListaAppsItems;

    private Util _Util;

    private ViewPager _ViewPager;

    private CacheDatosApps _CacheDatosApps;

    private HashMap<String, String> _Cache;

    public interface FragmentIterationListener {
        public void onFragmentIteration(Bundle parameters);
    }

    public static ListadoAppsFragment newInstance(Bundle bundle) {
        ListadoAppsFragment f = new ListadoAppsFragment();
        if (bundle != null) {
            f.setArguments(bundle);
        }
        return f;
    }

    /**
     * Unicamente verificamos si se esta instanciando correctamente
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListadoAppsActivity) {
            Log.i(LOG_TAG, "Yes");
        }
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listado_apps, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        _Servicio = AppsApiAdapter.getApiService();
        _ListaAppsItems = new ArrayList<>();
        _Util = new Util(getActivity());

        _CacheDatosApps = new CacheDatosApps(getActivity(), _Codigo);

        // Configurar el ViewPager con el adaptador secciones.
        _ViewPager = (ViewPager) root.findViewById(R.id.view_pager);


        CoordinatorLayout parentLayout = (CoordinatorLayout) root.findViewById(R.id.fragment_listado_apps);
        if (_Util.isDeviceOnlineActivity(parentLayout)) {
            searchFreeApplication();
        } else {
            searchCache();
            TextView _TxtCache = (TextView) root.findViewById(R.id.txt_cache);
            _TxtCache.setVisibility(View.VISIBLE);
        }

        return root;
    }

    private PagerAdapter buildAdapter() {
        return (new SectionsPagerAdapter(getChildFragmentManager()));
    }

    /*
     * (non-Javadoc)
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        _Codigo = bundle.getString(Constants.CODIGO);
    }

    private void searchFreeApplication() {

        _Util.mostrarProgress(getActivity().getResources().getString(R.string.organizando));
        Call<GetFreeApplicationBin> call = _Servicio.GetTopFreeApplication(_Codigo);

        call.enqueue(new Callback<GetFreeApplicationBin>() {
            @Override
            public void onResponse(Call<GetFreeApplicationBin> call,
                                   Response<GetFreeApplicationBin> response) {
                if (response.isSuccessful()) {
                    try {

                        GetFreeApplicationBin _Parametros = response.body(); //Lista de reportes que llegan

                        Map<String, List> _Feed = _Parametros.feed;

                        List<Map> _ListEntry = _Feed.get(Constants.ENTRY);

                        for (Map _Data : _ListEntry) {
                            DatosAppsItem _DatosAppsItem = new DatosAppsItem();

                            _DatosAppsItem.set_Image((String) ((Map) ((List) _Data.get(Constants.IMAGE)).
                                    get(2)).get(Constants.LABEL));
                            _DatosAppsItem.set_Name((String) ((Map) _Data.get(Constants.NAME)).
                                    get(Constants.LABEL));
                            _DatosAppsItem.set_Price((String) ((Map) ((Map) _Data.get(Constants.PRICE)).
                                    get(Constants.ATTRIBUTES)).get(Constants.AMOUNT));
                            _DatosAppsItem.set_Summary((String) ((Map) _Data.get(Constants.SUMMARY)).
                                    get(Constants.LABEL));
                            _DatosAppsItem.set_Tittle((String) ((Map) _Data.get(Constants.TITTLE)).
                                    get(Constants.LABEL));
                            _DatosAppsItem.set_Artist((String) ((Map) ((Map) _Data.get(Constants.ARTIST)).
                                    get(Constants.ATTRIBUTES)).get(Constants.HREF));
                            _ListaAppsItems.add(_DatosAppsItem);
                        }
                        _ViewPager.setOffscreenPageLimit(_ListaAppsItems.size());
                        _ViewPager.setAdapter(buildAdapter());
                        _Util.ocultarProgress();
                        guardarCache();

                    } catch (Exception ex) {
                        errorConexionServidor();
                        _Util.ocultarProgress();
                    }
                } else {
                    _Util.ocultarProgress();
                    errorConexionServidor();
                }
            }

            @Override
            public void onFailure(Call<GetFreeApplicationBin> call, Throwable t) {
                _Util.ocultarProgress();
                errorConexionServidor();
            }
        });
    }

    private void searchCache() {
        _Cache = _CacheDatosApps.getCacheDatos();
        System.out.println(_Cache.get(Constants.IMAGE));
        if (_Cache.get(Constants.IMAGE) != null) {
            String[] _Image = _Cache.get(Constants.IMAGE).split("¬");
            String[] _Name = _Cache.get(Constants.NAME).split("¬");
            String[] _Price = _Cache.get(Constants.PRICE).split("¬");
            String[] _Summary = _Cache.get(Constants.SUMMARY).split("¬");
            String[] _Artist = _Cache.get(Constants.ARTIST).split("¬");
            String[] _Tittle = _Cache.get(Constants.TITTLE).split("¬");

            for (int i = 0; i < _Price.length; i++) {

                DatosAppsItem _DatosAppsItem = new DatosAppsItem();

                _DatosAppsItem.set_Image(_Image[i]);
                _DatosAppsItem.set_Name(_Name[i]);
                _DatosAppsItem.set_Price(_Price[i]);
                _DatosAppsItem.set_Summary(_Summary[i]);
                _DatosAppsItem.set_Tittle(_Artist[i]);
                _DatosAppsItem.set_Artist(_Tittle[i]);
                _ListaAppsItems.add(_DatosAppsItem);

            }

            _ViewPager.setAdapter(buildAdapter());
        }

    }

    private void guardarCache() {
        String _Image = "", _Name = "", _Price = "", _Summary = "", _Artist = "",
                _Tittle = "";

        for (DatosAppsItem _DatosAppsItem :
                _ListaAppsItems) {
            _Image = _Image + _DatosAppsItem.get_Image() + "¬";
            _Name = _Name + _DatosAppsItem.get_Name() + "¬";
            _Price = _Price + _DatosAppsItem.get_Price() + "¬";
            _Summary = _Summary + _DatosAppsItem.get_Summary() + "¬";
            _Artist = _Artist + _DatosAppsItem.get_Artist() + "¬";
            _Tittle = _Tittle + _DatosAppsItem.get_Tittle() + "¬";
        }

        _CacheDatosApps.createCache(_Image, _Name, _Price, _Summary, _Artist, _Tittle);

    }


    /**
     * El metodo muestra un mensaje de error al conectarse con el WS
     */
    private void errorConexionServidor() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getActivity().getResources().getString(R.string.alert_titulo_error_conexion));
        alert.setMessage(getActivity().getResources().getString(R.string.alert_mensaje_error_servidor));
        alert.setPositiveButton(getActivity().getResources().getString(R.string.alert_boton_aceptar),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        getActivity().finish();

                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }


    /**
     * Un fragmento de marcador de posición que contiene una vista simple.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnTouchListener, View.OnLongClickListener {
        /**
         * El argumento fragmento que representa el número de sección para este fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        LinearLayout _Linear11, _Linear12, _Linear13, _Linear14, _Linear15, _Linear21, _Linear22,
                _Linear23, _Linear24, _Linear25, _Linear31, _Linear32, _Linear33, _Linear34,
                _Linear35;

        ImageView _Img11, _Img12, _Img13, _Img14, _Img15, _Img21, _Img22, _Img23, _Img24, _Img25,
                _Img31, _Img32, _Img33, _Img34, _Img35;

        TextView _Txt11, _Txt12, _Txt13, _Txt14, _Txt15, _Txt21, _Txt22, _Txt23, _Txt24, _Txt25,
                _Txt31, _Txt32, _Txt33, _Txt34, _Txt35;


        ArrayList<LinearLayout> _ListLinearLayouts = new ArrayList<>();
        ArrayList<ImageView> _ListImageViews = new ArrayList<>();
        ArrayList<TextView> _ListTextViews = new ArrayList<>();
        Animation zoomin;

        Canvas canvas;
        Paint paint;

        float downx = 0,downy = 0,upx = 0,upy = 0;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Crea cada vista del ViewPager.
         *
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_stand_apps, container, false);

            initInterface(rootView);


            return rootView;
        }

        private View initInterface(View rootView) {
            zoomin = AnimationUtils.loadAnimation(getActivity(), R.anim.ampliar_img);


            _Linear11 = (LinearLayout) rootView.findViewById(R.id.linear_1_1);
            _Linear12 = (LinearLayout) rootView.findViewById(R.id.linear_1_2);
            _Linear13 = (LinearLayout) rootView.findViewById(R.id.linear_1_3);
            _Linear14 = (LinearLayout) rootView.findViewById(R.id.linear_1_4);
            _Linear15 = (LinearLayout) rootView.findViewById(R.id.linear_1_5);
            _Linear21 = (LinearLayout) rootView.findViewById(R.id.linear_2_1);
            _Linear22 = (LinearLayout) rootView.findViewById(R.id.linear_2_2);
            _Linear23 = (LinearLayout) rootView.findViewById(R.id.linear_2_3);
            _Linear24 = (LinearLayout) rootView.findViewById(R.id.linear_2_4);
            _Linear25 = (LinearLayout) rootView.findViewById(R.id.linear_2_5);
            _Linear31 = (LinearLayout) rootView.findViewById(R.id.linear_3_1);
            _Linear32 = (LinearLayout) rootView.findViewById(R.id.linear_3_2);
            _Linear33 = (LinearLayout) rootView.findViewById(R.id.linear_3_3);
            _Linear34 = (LinearLayout) rootView.findViewById(R.id.linear_3_4);
            _Linear35 = (LinearLayout) rootView.findViewById(R.id.linear_3_5);
            _Img11 = (ImageView) rootView.findViewById(R.id.img_apps_1_1);
            _Img12 = (ImageView) rootView.findViewById(R.id.img_apps_1_2);
            _Img13 = (ImageView) rootView.findViewById(R.id.img_apps_1_3);
            _Img14 = (ImageView) rootView.findViewById(R.id.img_apps_1_4);
            _Img15 = (ImageView) rootView.findViewById(R.id.img_apps_1_5);
            _Img21 = (ImageView) rootView.findViewById(R.id.img_apps_2_1);
            _Img22 = (ImageView) rootView.findViewById(R.id.img_apps_2_2);
            _Img23 = (ImageView) rootView.findViewById(R.id.img_apps_2_3);
            _Img24 = (ImageView) rootView.findViewById(R.id.img_apps_2_4);
            _Img25 = (ImageView) rootView.findViewById(R.id.img_apps_2_5);
            _Img31 = (ImageView) rootView.findViewById(R.id.img_apps_3_1);
            _Img32 = (ImageView) rootView.findViewById(R.id.img_apps_3_2);
            _Img33 = (ImageView) rootView.findViewById(R.id.img_apps_3_3);
            _Img34 = (ImageView) rootView.findViewById(R.id.img_apps_3_4);
            _Img35 = (ImageView) rootView.findViewById(R.id.img_apps_3_5);

            _Txt11 = (TextView) rootView.findViewById(R.id.txt_apps_1_1);
            _Txt12 = (TextView) rootView.findViewById(R.id.txt_apps_1_2);
            _Txt13 = (TextView) rootView.findViewById(R.id.txt_apps_1_3);
            _Txt14 = (TextView) rootView.findViewById(R.id.txt_apps_1_4);
            _Txt15 = (TextView) rootView.findViewById(R.id.txt_apps_1_5);
            _Txt21 = (TextView) rootView.findViewById(R.id.txt_apps_2_1);
            _Txt22 = (TextView) rootView.findViewById(R.id.txt_apps_2_2);
            _Txt23 = (TextView) rootView.findViewById(R.id.txt_apps_2_3);
            _Txt24 = (TextView) rootView.findViewById(R.id.txt_apps_2_4);
            _Txt25 = (TextView) rootView.findViewById(R.id.txt_apps_2_5);
            _Txt31 = (TextView) rootView.findViewById(R.id.txt_apps_3_1);
            _Txt32 = (TextView) rootView.findViewById(R.id.txt_apps_3_2);
            _Txt33 = (TextView) rootView.findViewById(R.id.txt_apps_3_3);
            _Txt34 = (TextView) rootView.findViewById(R.id.txt_apps_3_4);
            _Txt35 = (TextView) rootView.findViewById(R.id.txt_apps_3_5);

            _ListLinearLayouts.add(_Linear11);
            _ListLinearLayouts.add(_Linear12);
            _ListLinearLayouts.add(_Linear13);
            _ListLinearLayouts.add(_Linear21);
            _ListLinearLayouts.add(_Linear22);
            _ListLinearLayouts.add(_Linear23);
            _ListLinearLayouts.add(_Linear31);
            _ListLinearLayouts.add(_Linear32);
            _ListLinearLayouts.add(_Linear33);

            _ListImageViews.add(_Img11);
            _ListImageViews.add(_Img12);
            _ListImageViews.add(_Img13);
            _ListImageViews.add(_Img21);
            _ListImageViews.add(_Img22);
            _ListImageViews.add(_Img23);
            _ListImageViews.add(_Img31);
            _ListImageViews.add(_Img32);
            _ListImageViews.add(_Img33);

            _ListTextViews.add(_Txt11);
            _ListTextViews.add(_Txt12);
            _ListTextViews.add(_Txt13);
            _ListTextViews.add(_Txt21);
            _ListTextViews.add(_Txt22);
            _ListTextViews.add(_Txt23);
            _ListTextViews.add(_Txt31);
            _ListTextViews.add(_Txt32);
            _ListTextViews.add(_Txt33);

            if (!getResources().getBoolean(R.bool.portrait_only)) {

                _ListLinearLayouts.add(_Linear14);
                _ListLinearLayouts.add(_Linear15);
                _ListLinearLayouts.add(_Linear24);
                _ListLinearLayouts.add(_Linear25);
                _ListLinearLayouts.add(_Linear34);
                _ListLinearLayouts.add(_Linear35);
                _ListImageViews.add(_Img14);
                _ListImageViews.add(_Img15);
                _ListImageViews.add(_Img24);
                _ListImageViews.add(_Img25);
                _ListImageViews.add(_Img34);
                _ListImageViews.add(_Img35);
                _ListTextViews.add(_Txt14);
                _ListTextViews.add(_Txt15);
                _ListTextViews.add(_Txt24);
                _ListTextViews.add(_Txt25);
                _ListTextViews.add(_Txt34);
                _ListTextViews.add(_Txt35);
            }

            for (ImageView _ImageView :
                    _ListImageViews) {
                _ImageView.setOnTouchListener(this);
                _ImageView.setOnLongClickListener(this);

            }

            for (int i = 0; i < _ListLinearLayouts.size(); i++) {
                int arg = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
                int _CanDatos = i + (arg * _ListLinearLayouts.size());
                if (_CanDatos < _ListaAppsItems.size()) {

                    _ListLinearLayouts.get(i).setVisibility(View.VISIBLE);
                    Picasso.with(getActivity()).load(_ListaAppsItems.get(_CanDatos).get_Image()).
                            placeholder(R.drawable.img_cargando).
                            error(R.drawable.img_no_disponible).
                            into(_ListImageViews.get(i));
                    _ListTextViews.get(i).setText(_ListaAppsItems.get(_CanDatos).get_Name());
                    _ListImageViews.get(i).setTag(_CanDatos);

                }
            }

            return null;
        }


        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.img_apps_1_1:
                    lanzarActivityDetalleApp((Integer) _Img11.getTag());
                    break;
                case R.id.img_apps_1_2:
                    lanzarActivityDetalleApp((Integer) _Img12.getTag());
                    break;
                case R.id.img_apps_1_3:
                    lanzarActivityDetalleApp((Integer) _Img13.getTag());
                    break;
                case R.id.img_apps_1_4:
                    lanzarActivityDetalleApp((Integer) _Img14.getTag());
                    break;
                case R.id.img_apps_1_5:
                    lanzarActivityDetalleApp((Integer) _Img15.getTag());
                    break;
                case R.id.img_apps_2_1:
                    lanzarActivityDetalleApp((Integer) _Img21.getTag());
                    break;
                case R.id.img_apps_2_2:
                    lanzarActivityDetalleApp((Integer) _Img22.getTag());
                    break;
                case R.id.img_apps_2_3:
                    lanzarActivityDetalleApp((Integer) _Img23.getTag());
                    break;
                case R.id.img_apps_2_4:
                    lanzarActivityDetalleApp((Integer) _Img24.getTag());
                    break;
                case R.id.img_apps_2_5:
                    lanzarActivityDetalleApp((Integer) _Img25.getTag());
                    break;
                case R.id.img_apps_3_1:
                    lanzarActivityDetalleApp((Integer) _Img31.getTag());
                    break;
                case R.id.img_apps_3_2:
                    lanzarActivityDetalleApp((Integer) _Img32.getTag());
                    break;
                case R.id.img_apps_3_3:
                    lanzarActivityDetalleApp((Integer) _Img33.getTag());
                    break;
                case R.id.img_apps_3_4:
                    lanzarActivityDetalleApp((Integer) _Img34.getTag());
                    break;
                case R.id.img_apps_3_5:
                    lanzarActivityDetalleApp((Integer) _Img35.getTag());
                    break;
            }
            return true;
        }

        private void lanzarActivityDetalleApp(int _Codigo) {
            Intent intent = new Intent(getActivity(), DetalleAppActivity.class);
            intent.putExtra(Constants.IMAGE, _ListaAppsItems.get(_Codigo).get_Image());
            intent.putExtra(Constants.NAME, _ListaAppsItems.get(_Codigo).get_Name());
            intent.putExtra(Constants.PRICE, _ListaAppsItems.get(_Codigo).get_Price());
            intent.putExtra(Constants.SUMMARY, _ListaAppsItems.get(_Codigo).get_Summary());
            intent.putExtra(Constants.ARTIST, _ListaAppsItems.get(_Codigo).get_Artist());
            intent.putExtra(Constants.TITTLE, _ListaAppsItems.get(_Codigo).get_Tittle());

            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.transition_in, R.anim.transition_out);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView _ImgApp = _Img11;
            switch (v.getId()) {
                case R.id.img_apps_1_1:
                    _ImgApp = _Img11;
                    break;
                case R.id.img_apps_1_2:
                    _ImgApp = _Img12;
                    break;
                case R.id.img_apps_1_3:
                    _ImgApp = _Img13;
                    break;
                case R.id.img_apps_1_4:
                    _ImgApp = _Img14;
                    break;
                case R.id.img_apps_1_5:
                    _ImgApp = _Img15;
                    break;
                case R.id.img_apps_2_1:
                    _ImgApp = _Img21;
                    break;
                case R.id.img_apps_2_2:
                    _ImgApp = _Img22;
                    break;
                case R.id.img_apps_2_3:
                    _ImgApp = _Img23;
                    break;
                case R.id.img_apps_2_4:
                    _ImgApp = _Img24;
                    break;
                case R.id.img_apps_2_5:
                    _ImgApp = _Img25;
                    break;
                case R.id.img_apps_3_1:
                    _ImgApp = _Img31;
                    break;
                case R.id.img_apps_3_2:
                    _ImgApp = _Img32;
                    break;
                case R.id.img_apps_3_3:
                    _ImgApp = _Img33;
                    break;
                case R.id.img_apps_3_4:
                    _ImgApp = _Img34;
                    break;
                case R.id.img_apps_3_5:
                    _ImgApp = _Img35;
                    break;
            }
            int action = event.getActionMasked();
            _ImgApp.setAnimation(zoomin);


            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    _ImgApp.startAnimation(zoomin);
                    break;
                case MotionEvent.ACTION_UP:
                    _ImgApp.clearAnimation();
                    break;
            }
            return false;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} que devuelve un fragmento correspondiente a una de las secciones / tabs / páginas.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // GetItem está llamado a crear una instancia del fragmento de la página en cuestión.
            // Devolución de un PlaceholderFragment (definida como una clase interna estática a continuación).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Muestra el total de las imagenes.
            double _TamañoArreglo = Float.valueOf(_ListaAppsItems.size());
            double _Divisor = 9.0;
            if (getResources().getBoolean(R.bool.portrait_only)) {

                return (int) Math.ceil(_TamañoArreglo / _Divisor);
            } else return (int) Math.ceil(_TamañoArreglo / (_Divisor + 6.0));

        }
    }
}
