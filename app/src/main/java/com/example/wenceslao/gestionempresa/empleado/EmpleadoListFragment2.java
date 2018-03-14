package com.example.wenceslao.gestionempresa.empleado;

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
import com.example.wenceslao.gestionempresa.proveedor.ContratoEmpleado;
import com.example.wenceslao.gestionempresa.proveedor.EmpleadoProveedor;

import java.io.FileNotFoundException;

public class EmpleadoListFragment2 extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	EmpleadoCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	//menu contextual
	ActionMode mActionMode;
	View viewSeleccionado;


	public static EmpleadoListFragment2 newInstance() {
		EmpleadoListFragment2 f = new EmpleadoListFragment2();

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
		/*MenuItem menuItem=menu.add(Menu.NONE, G.INSERTAR,Menu.NONE,"Insertar");
		menuItem.setIcon(R.drawable.ic_nuevo_registro);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		*/
		super.onCreateOptionsMenu(menu, inflater);
	}

	//para que haga algo la opcion anterior


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()){
			case G.INSERTAR:
				Intent intent=new Intent(getActivity(),EmpleadoInsertarActivity.class);
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
		View v = inflater.inflate( R.layout.fragment_empleado_list, container, false);

		mAdapter = new EmpleadoCursorAdapter(getActivity());
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
			int empleadoId=(Integer) viewSeleccionado.getTag();
			switch (menuItem.getItemId()){
				case R.id.menu_borrar:

					EmpleadoProveedor.delete(getActivity().getContentResolver(),empleadoId);
					break;
				case R.id.menu_editar:
					Intent intent=new Intent(getActivity(),EmpleadoModificarActivity.class);
					intent.putExtra(ContratoEmpleado.Empleado._ID,empleadoId);
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
		String columns[] = new String[] { ContratoEmpleado.Empleado._ID,
										  ContratoEmpleado.Empleado.NOMBRE_COMPLETO,
								          ContratoEmpleado.Empleado.FORMACION,
				                          ContratoEmpleado.Empleado.EMAIL,
				                          ContratoEmpleado.Empleado.TELEFONO
										};

		Uri baseUri = ContratoEmpleado.Empleado.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+ContratoEmpleado.AUTHORITY+"/Empleado");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public class EmpleadoCursorAdapter extends CursorAdapter {
		public EmpleadoCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(ContratoEmpleado.Empleado._ID));
			String nombre = cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.NOMBRE_COMPLETO));
			String formacion = cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.FORMACION));
			String email = cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.EMAIL));
			String telefono = cursor.getString(cursor.getColumnIndex(ContratoEmpleado.Empleado.TELEFONO));

	
			TextView textviewNombre = (TextView) view.findViewById( R.id.textview_empleado_list_item_nombre );
			textviewNombre.setText(nombre);

			TextView textViewFormacion = (TextView) view.findViewById( R.id.textview_empleado_list_item_formacion );
			textViewFormacion.setText(formacion);

			TextView textViewEmail = (TextView) view.findViewById( R.id.textview_empleado_list_item_email );
			textViewEmail.setText(email);

			TextView textViewTelefono = (TextView) view.findViewById( R.id.textview_empleado_list_item_telefono );
			textViewTelefono.setText(telefono);

			//poner la imagen
			ImageView image = (ImageView) view.findViewById( R.id.image_empleado);
			try {
				Utilidades.loadImageFromStorage(getActivity(),"img_empleado"+ID+".jpg",image);
			} catch (FileNotFoundException e) {
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				int color = generator.getColor(formacion); //Genera un color según el nombre
				TextDrawable drawable = TextDrawable.builder()
						.buildRound(formacion.substring(0,1), color);
				image.setImageDrawable(drawable);
			}
			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate( R.layout.empleado_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
