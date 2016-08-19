package com.edampe.pruebas.pruebaandroidgrability.ui.item;

/**
 * Created by edamp on 8/14/2016.
 */
public class CategoriasItem {

    public String _NombreCategoria;
    public int _ImgCategoria;
    public String _CodigoCategoria;

    public CategoriasItem(String _CodigoCategoria, int _ImgCategoria, String _NombreCategoria) {
        this._NombreCategoria = _NombreCategoria;
        this._ImgCategoria = _ImgCategoria;
        this._CodigoCategoria = _CodigoCategoria;
    }

    public String get_NombreCategoria() {
        return _NombreCategoria;
    }

    public void set_NombreCategoria(String _NombreCategoria) {
        this._NombreCategoria = _NombreCategoria;
    }

    public int get_ImgCategoria() {
        return _ImgCategoria;
    }

    public void set_ImgCategoria(int _ImgCategoria) {
        this._ImgCategoria = _ImgCategoria;
    }

    public String get_CodigoCategoria() {
        return _CodigoCategoria;
    }

    public void set_CodigoCategoria(String _CodigoCategoria) {
        this._CodigoCategoria = _CodigoCategoria;
    }
}
