package com.example.wenceslao.gestionempresa.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wenceslao on 30/10/2017.
 */

public class ContratoCliente {

    public static final String AUTHORITY = "com.example.wenceslao.gestionempresa.proveedor.ProveedorDeContenido";

    public static final class Cliente implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://" + AUTHORITY + "/Cliente");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String APELLIDOS = "Apellidos";
        public static final String EMAIL = "Email";
        public static final String TELEFONO = "Telefono";
    }

}
