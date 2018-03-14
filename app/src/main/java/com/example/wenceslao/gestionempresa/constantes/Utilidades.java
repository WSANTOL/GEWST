package com.example.wenceslao.gestionempresa.constantes;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wenceslao on 11/11/2017.
 */

public class Utilidades {

    static public void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img) throws FileNotFoundException {
        // The new size we want to scale to
        final int REQUIRED_SIZE=70;

        File f=contexto.getFileStreamPath(imagenFichero);
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(f), null, o);

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap b= BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        // Bitmap b= BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);

    }

    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {
        //guardar imagen con memoria interna
        FileOutputStream fos=contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress( Bitmap.CompressFormat.PNG,75,fos);
        fos.close();

    }

    public static boolean guardarMemoriaExterna(Bitmap image, Context contexto, String fileName, ContentResolver content){
        boolean exito=false;
        String sd= Environment.getExternalStorageDirectory().getAbsolutePath();
        File folder=new File(sd+"/Imagenes/Memoria Externa");
        if(!folder.exists()){
            folder.mkdirs();
        }
        File image_f=new File(folder,fileName);


        FileOutputStream outStream;
        try{
            outStream=new FileOutputStream(image_f);
            image.compress( Bitmap.CompressFormat.PNG,75,outStream);
            outStream.flush();
            outStream.close();
            MediaStore.Images.Media.insertImage(content,image_f.getAbsolutePath(),image_f.getName(),image_f.getName());
            exito=true;

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();

        }

        return exito;


    }
}
