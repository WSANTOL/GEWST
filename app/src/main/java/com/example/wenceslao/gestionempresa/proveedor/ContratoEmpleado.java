package com.example.wenceslao.gestionempresa.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wenceslao on 30/10/2017.
 */

public class ContratoEmpleado {

    public static final String AUTHORITY = "com.example.wenceslao.gestionempresa.proveedor.ProveedorDeContenido";

    public static final class Empleado implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://" + AUTHORITY + "/Empleado");

        // Table column
        public static final String NOMBRE_COMPLETO = "Nombre_completo";
        public static final String FORMACION = "Formacion";
        public static final String EMAIL = "Email";
        public static final String TELEFONO = "Telefono";
    }

}
