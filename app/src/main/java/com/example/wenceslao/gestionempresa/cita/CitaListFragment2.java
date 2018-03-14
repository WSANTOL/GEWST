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
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.wenceslao.gestionempresa.R;
import com.example.wenceslao.gestionempresa.constantes.Utilidades;
import com.example.wenceslao.gestionempresa.proveedor.CitaProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCita;

import java.io.FileNotFoundException;

public class CitaListFragment2 extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	CitaCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	//menu contextual
	ActionMode mActionMode;
	View viewSeleccionado;


	public static CitaListFragment2 newInstance() {
		CitaListFragment2 f = new CitaListFragment2();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//para boton de añadir registro
		//setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		/*
		MenuItem menuItem=menu.add(Menu.NONE, G.INSERTAR,Menu.NONE,"Insertar");
		menuItem.setIcon(R.drawable.ic_nuevo_registro);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		*/
		super.onCreateOptionsMenu(menu, inflater);
	}

	//para que haga algo la opcion anterior


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		switch (item.getItemId()){
			case G.INSERTAR:
				Intent intent=new Intent(getActivity(),CitaInsertarActivity.class);
				startActivity(intent);
				break;

		}
		*/
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
		View v = inflater.inflate( R.layout.fragment_cita_list, container, false);

		mAdapter = new CitaCursorAdapter(getActivity());
		setListAdapter(mAdapter);

		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Log.i(LOGTAG, "onActivityCreated");

		mCallbacks = this;

		getLoaderManager().initLoader(0, null, mCallbacks);
		//para decir cuando se lanza el menu cntezxtual
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				if (mActionMode!=null){
					return false;
				}
				mActionMode=getActivity().startActionMode(mActionModeCallback);
				view.setSelected(true);
				viewSeleccionado=view;
				return true;
			}
		});

	}

	//menu conxtual
	ActionMode.Callback mActionModeCallback=new ActionMode.Callback() {
		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			//debe aparecer menu contextual
			MenuInflater inflater= actionMode.getMenuInflater();
			inflater.inflate( R.menu.menu_contextual,menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
			int citaId=(Integer) viewSeleccionado.getTag();
			switch (menuItem.getItemId()){
				case R.id.menu_borrar:
					CitaProveedor.delete(getActivity().getContentResolver(),citaId);
					break;
				case R.id.menu_editar:
					Intent intent=new Intent(getActivity(),CitaModificarActivity.class);
					intent.putExtra(ContratoCita.Cita._ID,citaId);
					startActivity(intent);
					break;
			}
			mActionMode.finish();
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			//salimos del menu
			mActionMode=null;

		}
	};

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

	public class CitaCursorAdapter extends CursorAdapter {
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

	
			TextView textviewDia = (TextView) view.findViewById( R.id.textview_cita_list_item_dia);
			textviewDia.setText(dia);

			TextView textviewMes = (TextView) view.findViewById( R.id.textview_cita_list_item_mes);
			textviewMes.setText(mes);

			TextView textviewAnho = (TextView) view.findViewById( R.id.textview_cita_list_item_anho);
			textviewAnho.setText(anho);

			TextView textviewHora = (TextView) view.findViewById( R.id.textview_cita_list_item_hora);
			textviewHora.setText(hora);

			TextView textviewMinuto = (TextView) view.findViewById( R.id.textview_cita_list_item_minuto);
			textviewMinuto.setText(minuto);

			TextView textviewServicio = (TextView) view.findViewById( R.id.textview_cita_list_item_servicio);
			textviewServicio.setText(servicio);

			TextView textviewCliente = (TextView) view.findViewById( R.id.textview_cita_list_item_codcliente);
			textviewCliente.setText(cod_cliente);

			TextView textviewEmpleado = (TextView) view.findViewById( R.id.textview_cita_list_item_codempleado);
			textviewEmpleado.setText(cod_empleado);

			//poner la imagen
			ImageView imagen_cita = (ImageView) view.findViewById( R.id.image_cita);
			try {
				Utilidades.loadImageFromStorage(getActivity(),"img_cita"+ID+".jpg",imagen_cita);
			} catch (FileNotFoundException e) {
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				int color = generator.getColor(servicio); //Genera un color según el nombre
				TextDrawable drawable = TextDrawable.builder()
						.buildRound(servicio.substring(0,1), color);
				imagen_cita.setImageDrawable(drawable);
			}
			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate( R.layout.cita_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
