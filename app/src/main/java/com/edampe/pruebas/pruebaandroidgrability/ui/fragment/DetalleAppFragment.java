package com.edampe.pruebas.pruebaandroidgrability.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.edampe.pruebas.pruebaandroidgrability.R;
import com.edampe.pruebas.pruebaandroidgrability.constant.Constants;
import com.edampe.pruebas.pruebaandroidgrability.ui.activity.DetalleAppActivity;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetalleAppFragment extends Fragment {

    // Variable para debug
    public static final String LOG_TAG = DetalleAppFragment.class.getName();

    private String _Image, _Name, _Price, _Summary, _Artist, _Tittle;

    public interface FragmentIterationListener {
        public void onFragmentIteration(Bundle parameters);
    }

    public static DetalleAppFragment newInstance(Bundle bundle) {
        DetalleAppFragment f = new DetalleAppFragment();
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
        if (context instanceof DetalleAppActivity) {
            Log.i(LOG_TAG, "Yes");
        }
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_app, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ImageView _ImgCerrar = (ImageView) root.findViewById(R.id.img_cerrar);
        _ImgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ImageView _ImgApp = (ImageView) root.findViewById(R.id.img_app);
        Picasso.with(getActivity()).load(_Image).
                placeholder(R.drawable.img_cargando).
                error(R.drawable.img_no_disponible).
                into(_ImgApp);

        TextView _TxtName = (TextView) root.findViewById(R.id.txt_nombre_app);
        _TxtName.setText(_Name);
        TextView _TxtPrecio = (TextView) root.findViewById(R.id.txt_precion_img);
        _TxtPrecio.setText(_Price);
        TextView _TxtDetalles = (TextView) root.findViewById(R.id.txt_detalles_apps);

        String _StrDetallesApp = String.format("%s \n\n%s \n\n%s ",
                _Name,
                _Summary,
                _Tittle);

        _TxtDetalles.setText(_StrDetallesApp);
        _TxtDetalles.setMovementMethod(new ScrollingMovementMethod());


        return root;
    }

    /*
     * (non-Javadoc)
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        _Image = bundle.getString(Constants.IMAGE);
        _Name = bundle.getString(Constants.NAME);
        _Price = bundle.getString(Constants.PRICE);
        _Summary = bundle.getString(Constants.SUMMARY);
        _Artist = bundle.getString(Constants.ARTIST);
        _Tittle = bundle.getString(Constants.TITTLE);

    }
}

