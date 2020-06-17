package com.example.proyectosqlite.Datos;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import com.example.proyectosqlite.Config.Constants;
import com.example.proyectosqlite.Database.AppDatabase;
import com.example.proyectosqlite.Entities.Asignatura;

import java.util.ArrayList;
import java.util.List;

public class Datos {
    public static List<Asignatura> asignaturas = new ArrayList<Asignatura>();
    public static AppDatabase db = null;
    
    public Datos(Context c) {
        if(db == null) {
            db = Room.databaseBuilder(c, AppDatabase.class, Constants.BD_NAME)
                .allowMainThreadQueries()
                .build();
        }
        try {
            asignaturas = db.asignaturaDao().getAllAsignatura();
        }
        catch(Exception e) {
            Toast.makeText(c, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    public long agregar(Asignatura asignatura) {
        asignaturas.add(asignatura);
        long res = db.asignaturaDao().insert(asignatura);
        return res;
    }
    
    public int eliminar(long id) {
        int res = 0;
        Asignatura a = null;
        for (Asignatura asig : asignaturas) {
            if(asig.getId() == id) {
                a = asig;
            }
        }
        if(a != null) {
            asignaturas.remove(a);
            res = db.asignaturaDao().deleteById(id);
        }
        return res;
    }

    public int actualizar(Asignatura asignatura) {
        int res = 0;
        Asignatura asigActualizar = null;
        int indice = -1;
        for (Asignatura asig : asignaturas) {
            if(asig.getId() == asignatura.getId()) {
                asigActualizar = asig;
                indice ++;
            }
        }
        if(asigActualizar != null) {
            asignaturas.set(indice, asignatura);
            db.asignaturaDao().updateEntidad(asignatura.getId(), asignatura.getTitle(), asignatura.getDescription());
        }
        return res;
    }
    
}
