package com.example.proyectosqlite.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectosqlite.Config.Constants;
import com.example.proyectosqlite.Database.AppDatabase;
import com.example.proyectosqlite.Datos.Datos;
import com.example.proyectosqlite.Entities.Asignatura;
import com.example.proyectosqlite.Holder.MyHolder;
import com.example.proyectosqlite.R;
import com.example.proyectosqlite.UserInterfaces.MainActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context c;
    Datos list = null;


    public MyAdapter(Context c, Datos list) {
        this.c=c;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);
        return new MyHolder(view);
    }

    public void notifyChanged() {
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.mTitle.setText(Datos.asignaturas.get(position).getTitle());
        holder.mDescription.setText(Datos.asignaturas.get(position).getDescription());

        holder.setCreateContextMenu(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Opciones");

                //Eliminando elemento
                menu.add("Eliminar Elemento").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                    try {
                        Asignatura current = Datos.asignaturas.get(position);
                        list.eliminar(current.getId());
                        Toast.makeText(c, "Se ha eliminado con exito", Toast.LENGTH_SHORT).show();
                        notifyChanged();
                    }
                    catch(Exception ex) {
                        Toast.makeText(c, "Error al eliminar " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
                    }
                });

                //Actualizando elemento
                menu.add("Actualizar elemento").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Aquí va el código actualizar
                        final Dialog dlg = new Dialog(c);

                        dlg.setContentView(R.layout.add_new);
                        dlg.setTitle("Actualizando asignatura");
                        dlg.setCancelable(false);
                        Button btAddNew = (Button) dlg.findViewById(R.id.btnew);
                        Button btCancel = (Button) dlg.findViewById(R.id.btcancel);

                        final EditText editText_Nombreasig = (EditText) dlg.findViewById(R.id.editText_Name);
                        final EditText editText_Descrip = (EditText) dlg.findViewById(R.id.editText_Desc);

                        final Asignatura current = Datos.asignaturas.get(position);
                        editText_Nombreasig.setText(current.getTitle());
                        editText_Descrip.setText(current.getDescription());

                        btAddNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageView imageView = (ImageView) dlg.findViewById(R.id.imageAsig);

                                if ((editText_Nombreasig.getText().toString().contentEquals("")) ||
                                        (editText_Descrip.getText().toString().contentEquals(""))) {
                                    Toast.makeText(c, "Los campos no pueden quedar en blanco",
                                            Toast.LENGTH_LONG).show();

                                } else {
                                    String nAsignatura, nDes;

                                    nAsignatura = editText_Nombreasig.getText().toString();
                                    nDes = editText_Descrip.getText().toString();

                                    current.setTitle(nAsignatura);
                                    current.setDescription(nDes);
                                    try {
                                        long resultadoInsert = list.actualizar(current);
                                        notifyChanged();
                                        Toast.makeText(c, "Datos Actualizados", Toast.LENGTH_LONG).show();
                                        editText_Nombreasig.setText("");
                                        editText_Descrip.setText("");
                                    }
                                    catch(Exception e) {
                                        Toast.makeText(c, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    dlg.cancel();
                                }
                            }
                        });

                        btCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.cancel();
                            }
                        });

                        dlg.show();

                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return Datos.asignaturas.size();
    }

}
