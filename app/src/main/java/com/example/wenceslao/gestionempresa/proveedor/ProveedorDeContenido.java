package com.example.wenceslao.gestionempresa.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

/**
 * Created by wenceslao on 29/10/2017.
 */

public class ProveedorDeContenido extends ContentProvider{

    private static final int CLIENTE_ONE_REG = 1;
    private static final int CLIENTE_ALL_REGS = 2;

    private static final int EMPLEADO_ONE_REG = 3;
    private static final int EMPLEADO_ALL_REGS = 4;

    private static final int CITA_ONE_REG = 5;
    private static final int CITA_ALL_REGS = 6;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Empresa.db";
    private static final int DATABASE_VERSION = 7;

    private static final String CLIENTE_TABLE_NAME = "Cliente";
    private static final String EMPLEADO_TABLE_NAME = "Empleado";
    private static final String CITA_TABLE_NAME = "Cita";

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                ContratoCliente.AUTHORITY,
                CLIENTE_TABLE_NAME,
                CLIENTE_ALL_REGS);
        sUriMatcher.addURI(
                ContratoCliente.AUTHORITY,
                CLIENTE_TABLE_NAME + "/#",
                CLIENTE_ALL_REGS);

        sUriMatcher.addURI(
                ContratoEmpleado.AUTHORITY,
                EMPLEADO_TABLE_NAME,
                EMPLEADO_ALL_REGS);
        sUriMatcher.addURI(
                ContratoEmpleado.AUTHORITY,
                EMPLEADO_TABLE_NAME + "/#",
                EMPLEADO_ALL_REGS);

        sUriMatcher.addURI(
                ContratoCita.AUTHORITY,
                CITA_TABLE_NAME,
                CITA_ALL_REGS);
        sUriMatcher.addURI(
                ContratoCita.AUTHORITY,
                CITA_TABLE_NAME + "/#",
                CITA_ALL_REGS);



        // Specifies a custom MIME type for the picture URL table text/html

        sMimeTypes.put(
                CLIENTE_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        ContratoCliente.AUTHORITY + "." + CLIENTE_TABLE_NAME);
        sMimeTypes.put(
                CLIENTE_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        ContratoCliente.AUTHORITY + "." + CLIENTE_TABLE_NAME);

        // Specifies a custom MIME type for the picture URL table text/html

        sMimeTypes.put(
                EMPLEADO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        ContratoEmpleado.AUTHORITY + "." + EMPLEADO_TABLE_NAME);
        sMimeTypes.put(
                EMPLEADO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        ContratoEmpleado.AUTHORITY + "." + EMPLEADO_TABLE_NAME);

        // Specifies a custom MIME type for the picture URL table text/html

        sMimeTypes.put(
                CITA_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        ContratoCita.AUTHORITY + "." + CITA_TABLE_NAME);
        sMimeTypes.put(
                CITA_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        ContratoCita.AUTHORITY + "." + CITA_TABLE_NAME);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
            //}
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                    + CLIENTE_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + ContratoCliente.Cliente.NOMBRE + " TEXT , "
                    + ContratoCliente.Cliente.APELLIDOS + " TEXT , "
                    + ContratoCliente.Cliente.EMAIL + " TEXT , "
                    + ContratoCliente.Cliente.TELEFONO + " TEXT ); "
            );

            db.execSQL("Create table "
                    + EMPLEADO_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + " TEXT , "
                    + ContratoEmpleado.Empleado.FORMACION + " TEXT , "
                    + ContratoEmpleado.Empleado.EMAIL + " TEXT , "
                    + ContratoEmpleado.Empleado.TELEFONO + " TEXT ); "
            );

            db.execSQL("Create table "
                    + CITA_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + ContratoCita.Cita.DIA + " INT , "
                    + ContratoCita.Cita.MES + " INT , "
                    + ContratoCita.Cita.ANHO + " INT , "
                    + ContratoCita.Cita.HORA + " INT , "
                    + ContratoCita.Cita.MINUTO + " INT , "
                    + ContratoCita.Cita.SERVICIO + " TEXT , "
                    + ContratoCita.Cita.COD_CLIENTE + " INT , "
                    + ContratoCita.Cita.COD_EMPLEADO + " INT , "
                    + "FOREIGN KEY ("+ContratoCita.Cita.COD_CLIENTE +") REFERENCES " + CLIENTE_TABLE_NAME +"("+ ContratoCliente.Cliente._ID + "),"
                    + "FOREIGN KEY ("+ContratoCita.Cita.COD_EMPLEADO +") REFERENCES " + EMPLEADO_TABLE_NAME +"("+ ContratoEmpleado.Empleado._ID + "));"
            );

            inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

            db.execSQL("INSERT INTO " + CLIENTE_TABLE_NAME + " (" +  ContratoCliente.Cliente._ID + "," + ContratoCliente.Cliente.NOMBRE + "," + ContratoCliente.Cliente.APELLIDOS + "," + ContratoCliente.Cliente.EMAIL + "," + ContratoCliente.Cliente.TELEFONO + ") " +
                    "VALUES (1,'Alicia','Suárez Benítez','alicisb@hotmail.com','928674523')");
            db.execSQL("INSERT INTO " + CLIENTE_TABLE_NAME + " (" +  ContratoCliente.Cliente._ID + "," + ContratoCliente.Cliente.NOMBRE + "," + ContratoCliente.Cliente.APELLIDOS + "," + ContratoCliente.Cliente.EMAIL + "," + ContratoCliente.Cliente.TELEFONO + ") " +
                    "VALUES (2,'Juan','López Santana','juanito2002@hotmail.com','928623456')");
            db.execSQL("INSERT INTO " + CLIENTE_TABLE_NAME + " (" +  ContratoCliente.Cliente._ID + "," + ContratoCliente.Cliente.NOMBRE + "," + ContratoCliente.Cliente.APELLIDOS + "," + ContratoCliente.Cliente.EMAIL + "," + ContratoCliente.Cliente.TELEFONO + ") " +
                    "VALUES (3,'Lourdes','Ortega Déniz','lourdes1461212@hotmail.com','660786523')");
            db.execSQL("INSERT INTO " + CLIENTE_TABLE_NAME + " (" +  ContratoCliente.Cliente._ID + "," + ContratoCliente.Cliente.NOMBRE + "," + ContratoCliente.Cliente.APELLIDOS + "," + ContratoCliente.Cliente.EMAIL + "," + ContratoCliente.Cliente.TELEFONO + ") " +
                    "VALUES (4,'Ana','Jiménez Benítez','anitac@hotmail.com','687132467')");

            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (1,'María Eugenia','Auxiliar Peluquería','meugenia.nr@hotmail.com','667895634')");
            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (2,'Lola','Auxiliar Peluquería','lolita56.nr@hotmail.com','687563412')");
            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (3,'Paco','Aprendiz','pepe.nr@hotmail.com','123456789')");
            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (4,'Juani','Peluquera','juana.nr@hotmail.com','567456345')");
            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (5,'María Aurelia','Esteticista','aurelita.nr@hotmail.com','928343434')");
            db.execSQL("INSERT INTO " + EMPLEADO_TABLE_NAME + " (" +  ContratoEmpleado.Empleado._ID + "," + ContratoEmpleado.Empleado.NOMBRE_COMPLETO + "," + ContratoEmpleado.Empleado.FORMACION + "," + ContratoEmpleado.Empleado.EMAIL + "," + ContratoEmpleado.Empleado.TELEFONO + ") " +
                    "VALUES (6,'María Pilar','Peluquera','pilipp.nr@hotmail.com','124567890')");

            db.execSQL("INSERT INTO " + CITA_TABLE_NAME + " (" +  ContratoCita.Cita._ID + "," + ContratoCita.Cita.DIA + "," + ContratoCita.Cita.MES + "," + ContratoCita.Cita.ANHO + "," + ContratoCita.Cita.HORA + "," + ContratoCita.Cita.MINUTO + "," + ContratoCita.Cita.SERVICIO + "," + ContratoCita.Cita.COD_CLIENTE + "," + ContratoCita.Cita.COD_EMPLEADO + ") " +
                    "VALUES (1,20,11,2017,9,00,'Corte',1,1)");
            db.execSQL("INSERT INTO " + CITA_TABLE_NAME + " (" +  ContratoCita.Cita._ID + "," + ContratoCita.Cita.DIA + "," + ContratoCita.Cita.MES + "," + ContratoCita.Cita.ANHO + "," + ContratoCita.Cita.HORA + "," + ContratoCita.Cita.MINUTO + "," + ContratoCita.Cita.SERVICIO + "," + ContratoCita.Cita.COD_CLIENTE + "," + ContratoCita.Cita.COD_EMPLEADO + ") " +
                    "VALUES (2,20,11,2017,9,30,'Peinado',2,2)");
            db.execSQL("INSERT INTO " + CITA_TABLE_NAME + " (" +  ContratoCita.Cita._ID + "," + ContratoCita.Cita.DIA + "," + ContratoCita.Cita.MES + "," + ContratoCita.Cita.ANHO + "," + ContratoCita.Cita.HORA + "," + ContratoCita.Cita.MINUTO + "," + ContratoCita.Cita.SERVICIO + "," + ContratoCita.Cita.COD_CLIENTE + "," + ContratoCita.Cita.COD_EMPLEADO + ") " +
                    "VALUES (3,21,11,2017,10,00,'Tinte',3,3)");
            db.execSQL("INSERT INTO " + CITA_TABLE_NAME + " (" +  ContratoCita.Cita._ID + "," + ContratoCita.Cita.DIA + "," + ContratoCita.Cita.MES + "," + ContratoCita.Cita.ANHO + "," + ContratoCita.Cita.HORA + "," + ContratoCita.Cita.MINUTO + "," + ContratoCita.Cita.SERVICIO + "," + ContratoCita.Cita.COD_CLIENTE + "," + ContratoCita.Cita.COD_EMPLEADO + ") " +
                    "VALUES (4,21,11,2017,9,30,'Estetica',4,4)");
            db.execSQL("INSERT INTO " + CITA_TABLE_NAME + " (" +  ContratoCita.Cita._ID + "," + ContratoCita.Cita.DIA + "," + ContratoCita.Cita.MES + "," + ContratoCita.Cita.ANHO + "," + ContratoCita.Cita.HORA + "," + ContratoCita.Cita.MINUTO + "," + ContratoCita.Cita.SERVICIO + "," + ContratoCita.Cita.COD_CLIENTE + "," + ContratoCita.Cita.COD_EMPLEADO + ") " +
                    "VALUES (5,20,12,2017,10,00,'Corte',1,1)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CLIENTE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + EMPLEADO_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + CITA_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenido() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CLIENTE_ALL_REGS:
                table = CLIENTE_TABLE_NAME;
                break;
            case EMPLEADO_ALL_REGS:
                table = EMPLEADO_TABLE_NAME;
                break;
            case CITA_ALL_REGS:
                table = CITA_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CLIENTE_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCliente.Cliente._ID + " = "
                        + uri.getLastPathSegment();
                table = CLIENTE_TABLE_NAME;
                break;
            case CLIENTE_ALL_REGS:
                table = CLIENTE_TABLE_NAME;
                break;
            case EMPLEADO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoEmpleado.Empleado._ID + " = "
                        + uri.getLastPathSegment();
                table = EMPLEADO_TABLE_NAME;
                break;
            case EMPLEADO_ALL_REGS:
                table = EMPLEADO_TABLE_NAME;
                break;
            case CITA_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCita.Cita._ID + " = "
                        + uri.getLastPathSegment();
                table = CITA_TABLE_NAME;
                break;
            case CITA_ALL_REGS:
                table = CITA_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case CLIENTE_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCliente.Cliente._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(CLIENTE_TABLE_NAME);
                break;
            case CLIENTE_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        ContratoCliente.Cliente._ID + " ASC";
                qb.setTables(CLIENTE_TABLE_NAME);
                break;
            case EMPLEADO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoEmpleado.Empleado._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(EMPLEADO_TABLE_NAME);
                break;
            case EMPLEADO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        ContratoEmpleado.Empleado._ID + " ASC";
                qb.setTables(EMPLEADO_TABLE_NAME);
                break;
            case CITA_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCita.Cita._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(CITA_TABLE_NAME);
                break;
            case CITA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        ContratoCita.Cita._ID + " ASC";
                qb.setTables(CITA_TABLE_NAME);
                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CLIENTE_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCliente.Cliente._ID + " = "
                        + uri.getLastPathSegment();
                table = CLIENTE_TABLE_NAME;
                break;
            case CLIENTE_ALL_REGS:
                table = CLIENTE_TABLE_NAME;
                break;
            case EMPLEADO_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoEmpleado.Empleado._ID + " = "
                        + uri.getLastPathSegment();
                table = EMPLEADO_TABLE_NAME;
                break;
            case EMPLEADO_ALL_REGS:
                table = EMPLEADO_TABLE_NAME;
                break;
            case CITA_ONE_REG:
                if (null == selection) selection = "";
                selection += ContratoCita.Cita._ID + " = "
                        + uri.getLastPathSegment();
                table = CITA_TABLE_NAME;
                break;
            case CITA_ALL_REGS:
                table = CITA_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);
    }


    public Cursor consultaMultiple(String selection){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(EMPLEADO_CITAS);

        return qb.query( db,columnas,selection,null,null,null,null );

    }

    private static final String EMPLEADO_CITAS= EMPLEADO_TABLE_NAME +
            "INNER JOIN  " + CITA_TABLE_NAME +
            "ON"+ ContratoEmpleado.Empleado._ID+ " = "+ContratoCita.Cita.COD_EMPLEADO;

    private final String[] columnas = new String[]{
            EMPLEADO_TABLE_NAME + "." + ContratoEmpleado.Empleado._ID,
            ContratoCita.Cita.DIA,
            ContratoCita.Cita.MES,
            ContratoCita.Cita.ANHO,
            ContratoCita.Cita.HORA,
            ContratoCita.Cita.MINUTO,
            ContratoCita.Cita.SERVICIO,
            ContratoEmpleado.Empleado.NOMBRE_COMPLETO};


}
