package com.example.proyectosqlite.Interfaces;

import com.example.proyectosqlite.Entities.Asignatura;

import org.intellij.lang.annotations.JdkConstants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AsignaturaDao {

    @Query("SELECT COUNT (*) FROM" + Asignatura.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + Asignatura.TABLE_NAME)
    List<Asignatura> getAllAsignatura();

    @Insert
    long insert(Asignatura asignatura);

    @Query("DELETE FROM "+ Asignatura.TABLE_NAME + " WHERE " + Asignatura.COLUMN_ID +"= :id")
    int deleteById (long id);

    @Query("UPDATE " + Asignatura.TABLE_NAME + " SET titulo=:titulo, descripcion=:descripcion WHERE _ID=:id")
    int  updateEntidad (long id, String titulo, String descripcion);

}
