package com.example.wenceslao.gestionempresa.cliente;

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
import com.example.wenceslao.gestionempresa.proveedor.ClienteProveedor;
import com.example.wenceslao.gestionempresa.proveedor.ContratoCliente;

import java.io.FileNotFoundException;

public class ClienteListFragment2 extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	ClienteCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	//menu contextual
	ActionMode mActionMode;
	View viewSeleccionado;


	public static ClienteListFragment2 newInstance() {
		ClienteListFragment2 f = new ClienteListFragment2();

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
				Intent intent=new Intent(getActivity(),ClienteInsertarActivity.class);
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
		View v = inflater.inflate( R.layout.fragment_cliente_list, container, false);

		mAdapter = new ClienteCursorAdapter(getActivity());
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
			int clienteId=(Integer) viewSeleccionado.getTag();
			switch (menuItem.getItemId()){
				case R.id.menu_borrar:

					ClienteProveedor.delete(getActivity().getContentResolver(),clienteId);
					break;
				case R.id.menu_editar:
					Intent intent=new Intent(getActivity(),ClienteModificarActivity.class);
					intent.putExtra(ContratoCliente.Cliente._ID,clienteId);
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
		String columns[] = new String[] { ContratoCliente.Cliente._ID,
										  ContratoCliente.Cliente.NOMBRE,
				                          ContratoCliente.Cliente.APELLIDOS,
				                          ContratoCliente.Cliente.EMAIL,
				                          ContratoCliente.Cliente.TELEFONO
										};

		Uri baseUri = ContratoCliente.Cliente.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+ContratoCliente.AUTHORITY+"/Cliente");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public class ClienteCursorAdapter extends CursorAdapter {
		public ClienteCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(ContratoCliente.Cliente._ID));
			String nombre = cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.NOMBRE));
			String apellido = cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.APELLIDOS));
			String email = cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.EMAIL));
			String telefono = cursor.getString(cursor.getColumnIndex(ContratoCliente.Cliente.TELEFONO));

	
			TextView textviewNombre = (TextView) view.findViewById( R.id.textview_cliente_list_item_nombre );
			textviewNombre.setText(nombre);

			TextView textviewApellidos = (TextView) view.findViewById( R.id.textview_cliente_list_item_apellidos );
			textviewApellidos.setText(apellido);

			TextView textViewEmail = (TextView) view.findViewById( R.id.textview_cliente_list_item_email );
			textViewEmail.setText(email);

			TextView textViewTelefono = (TextView) view.findViewById( R.id.textview_cliente_list_item_telefono );
			textViewTelefono.setText(telefono);

			//poner la imagen
			ImageView imagen_cliente = (ImageView) view.findViewById( R.id.image_cliente);
			try {
				Utilidades.loadImageFromStorage(getActivity(),"img_cliente"+ID+".jpg",imagen_cliente);
			} catch (FileNotFoundException e) {
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				int color = generator.getColor(apellido); //Genera un color según el nombre
				TextDrawable drawable = TextDrawable.builder()
						.buildRound(apellido.substring(0,1), color);
				imagen_cliente.setImageDrawable(drawable);
			}
			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate( R.layout.cliente_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
