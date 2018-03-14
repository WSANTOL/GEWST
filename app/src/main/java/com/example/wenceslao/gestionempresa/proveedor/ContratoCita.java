package com.example.wenceslao.gestionempresa.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wenceslao on 30/10/2017.
 */

public class ContratoCita {

    public static final String AUTHORITY = "com.example.wenceslao.gestionempresa.proveedor.ProveedorDeContenido";

    public static final class Cita implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://" + AUTHORITY + "/Cita");

        // Table column
        public static final String DIA = "Dia";
        public static final String MES = "Mes";
        public static final String ANHO = "Año";
        public static final String HORA = "Hora";
        public static final String MINUTO = "Minuto";
        public static final String SERVICIO = "Servicio";
        public static final String COD_CLIENTE = "Cod_cliente";
        public static final String COD_EMPLEADO = "Cod_empleado";
    }

}


