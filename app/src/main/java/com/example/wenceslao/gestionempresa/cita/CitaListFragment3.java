package com.example.wenceslao.gestionempresa.cita;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.wenceslao.gestionempresa.R;
import com.example.wenceslao.gestionempresa.constantes.G;
import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.proveedor.CitaProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCita;

import java.io.FileNotFoundException;

public class CitaListFragment3 extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	CitaCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;


	public static CitaListFragment3 newInstance() {
		CitaListFragment3 f = new CitaListFragment3();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//para boton de añadir registro
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		MenuItem menuItem2=menu.add(Menu.NONE, G.CONSULTA1,Menu.NONE,"Consulta 1");
		menuItem2.setIcon(R.drawable.ic_consulta1);
		menuItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		super.onCreateOptionsMenu(menu, inflater);
	}

	//para que haga algo la opcion anterior


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case G.CONSULTA1:

				break;

		}
		return super.onOptionsItemSelected(item);
	}



	/**
	 * The Fragment's UI is just a simple text view showing its
	 * instance number.
	 */



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		//Log.i(LOGTAG, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_cita_list, container, false);

		mAdapter = new CitaCursorAdapter(getActivity());
		setListAdapter(mAdapter);

		return v;
	}


	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		String columns[] = new String[] { ContratoCita.Cita._ID,
										  ContratoCita.Cita.DIA,
										  ContratoCita.Cita.MES,
										  ContratoCita.Cita.ANHO,
										  ContratoCita.Cita.HORA,
										  ContratoCita.Cita.MINUTO,
										  ContratoCita.Cita.SERVICIO,
										  ContratoCita.Cita.COD_CLIENTE,
										  ContratoCita.Cita.COD_EMPLEADO
										};

		Uri baseUri = ContratoCita.Cita.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+ContratoCita.AUTHORITY+"/Cita");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public static class CitaCursorAdapter extends CursorAdapter {
		public CitaCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(ContratoCita.Cita._ID));
			String dia = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.DIA));
			String mes = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.MES));
			String anho = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.ANHO));
			String hora = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.HORA));
			String minuto = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.MINUTO));
			String servicio = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.SERVICIO));
			String cod_cliente = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.COD_CLIENTE));
			String cod_empleado = cursor.getString(cursor.getColumnIndex(ContratoCita.Cita.COD_EMPLEADO));

	
			TextView textviewDia = (TextView) view.findViewById(R.id.textview_cita_list_item_dia);
			textviewDia.setText(dia);

			TextView textviewMes = (TextView) view.findViewById(R.id.textview_cita_list_item_mes);
			textviewMes.setText(mes);

			TextView textviewAnho = (TextView) view.findViewById(R.id.textview_cita_list_item_anho);
			textviewAnho.setText(anho);

			TextView textviewHora = (TextView) view.findViewById(R.id.textview_cita_list_item_hora);
			textviewHora.setText(hora);

			TextView textviewMinuto = (TextView) view.findViewById(R.id.textview_cita_list_item_minuto);
			textviewMinuto.setText(minuto);

			TextView textviewServicio = (TextView) view.findViewById(R.id.textview_cita_list_item_servicio);
			textviewServicio.setText(servicio);

			TextView textviewCliente = (TextView) view.findViewById(R.id.textview_cita_list_item_codcliente);
			textviewCliente.setText(cod_cliente);

			TextView textviewEmpleado = (TextView) view.findViewById(R.id.textview_cita_list_item_codempleado);
			textviewEmpleado.setText(cod_empleado);


		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.cita_list_item_1, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
