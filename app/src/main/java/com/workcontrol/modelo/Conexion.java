package com.workcontrol.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.Nullable;

public class Conexion extends SQLiteOpenHelper {
    private Context contexto;
    private static final String NOMBRE_BD = "database";
    private static final int VERSION_BD = 1;

    public Conexion(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUsuarios = "CREATE TABLE Usuarios (dni TEXT PRIMARY KEY, nombre TEXT, apellido TEXT, correo TEXT, contrasegna TEXT)";
        sqLiteDatabase.execSQL(createUsuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AgnadirUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("dni", usuario.getDni());
        values.put("nombre", usuario.getNombre());
        values.put("apellido", usuario.getApellido());
        values.put("correo", usuario.getCorreo());
        values.put("contrasegna", usuario.getContrasegna());

        db.insert("Usuarios", null, values);

    }

    public Cursor consultar (Usuario usuario) {
        String consultaTodosUsuarios = "SELECT * FROM Usuarios WHERE dni = "+"'"+usuario.getDni()+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (cursor == null) {
            cursor = db.rawQuery(consultaTodosUsuarios, null);

        }
        return cursor;
    }

}
