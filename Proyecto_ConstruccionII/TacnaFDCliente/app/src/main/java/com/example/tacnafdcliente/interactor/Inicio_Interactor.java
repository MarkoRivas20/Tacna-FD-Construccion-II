package com.example.tacnafdcliente.interactor;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.tacnafdcliente.interfaces.Inicio;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Inicio_Interactor implements Inicio.Interactor {

    private Inicio.onOperationListener mListener;

    private ArrayList<Establecimiento_Modelo> Establecimientos = new ArrayList<>();
    private ArrayList<Establecimiento_Modelo> Establecimientos_Populares = new ArrayList<>();
    private ArrayList<ItemMenu_Modelo> Items_Menu = new ArrayList<>();
    private ArrayList<ItemMenu_Modelo> Items_Menu_Categoria = new ArrayList<>();

    private ValueEventListener valueEventListener_Search_Establishment;
    private ValueEventListener valueEventListener_List_Item_Menu;

    Boolean Listar_Items_Menu = false;
    Boolean Existe_Establecimiento = false;

    Double Numero_Mayor = 0.0;
    int Posicion_Numero_Mayor = 0;

    Boolean aBoolean = false;
    Boolean aBoolean2 = false;

    public Inicio_Interactor(Inicio.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performSearchEstablishmentByCategory(DatabaseReference Database_Reference, String Categoria) {

        final Query query = Database_Reference.orderByChild("categoria").equalTo(Categoria);

        valueEventListener_Search_Establishment = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Establecimientos.clear();
                Existe_Establecimiento = false;

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Existe_Establecimiento = true;
                    Establecimiento_Modelo Establecimiento = postSnapshot.getValue(Establecimiento_Modelo.class);
                    Establecimientos.add(Establecimiento);
                }
                mListener.onSuccessSearchEstablishmentByCategory(Establecimientos, Existe_Establecimiento);
                query.removeEventListener(valueEventListener_Search_Establishment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureSearchEstablishmentByCategory();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_Search_Establishment);


    }

    @Override
    public void performGetFourBestEstablishment(ArrayList<Establecimiento_Modelo> Establecimientos) {
        Establecimientos_Populares.clear();

        for(int i = 0 ; i < 4 ; i++)
        {
            for(int j = 0 ; j < Establecimientos.size() ; j++)
            {
                if(Establecimientos.get(j).getPuntuacion() >= Numero_Mayor)
                {
                    if(Establecimientos_Populares.size() != 0)
                    {
                        for(int z = 0 ; z < Establecimientos_Populares.size() ; z++){

                            if(Establecimientos.get(j).getID_Establecimiento().equals(Establecimientos_Populares.get(z).getID_Establecimiento())){

                                aBoolean = true;
                            }
                        }

                        if(!aBoolean)
                        {
                            aBoolean2 = true;
                            Posicion_Numero_Mayor = j;
                            Numero_Mayor = Establecimientos.get(j).getPuntuacion();
                        }
                        aBoolean = false;
                    }
                    else
                    {
                        aBoolean2 = true;
                        Posicion_Numero_Mayor = j;
                        Numero_Mayor = Establecimientos.get(j).getPuntuacion();
                    }
                }
            }
            if(aBoolean2){
                Establecimientos_Populares.add(Establecimientos.get(Posicion_Numero_Mayor));
                Numero_Mayor = 0.0;
                aBoolean2 = false;
            }
        }

        mListener.onSuccessGetFourBestEstablishment(Establecimientos_Populares);
    }

    @Override
    public void performListItemMenu(final DatabaseReference Database_Reference) {

        valueEventListener_List_Item_Menu = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Listar_Items_Menu = false;
                Items_Menu.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    ItemMenu_Modelo Item_Menu = postSnapshot.getValue(ItemMenu_Modelo.class);
                    if(Item_Menu.getEstado().equals("Activo"))
                    {
                        Listar_Items_Menu = true;
                        Items_Menu.add(Item_Menu);
                    }
                }
                mListener.onSuccessListItemMenu(Items_Menu, Listar_Items_Menu);
                Database_Reference.removeEventListener(valueEventListener_List_Item_Menu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureListItemMenu();
            }
        };

        Database_Reference.addListenerForSingleValueEvent(valueEventListener_List_Item_Menu);

    }

    @Override
    public void performSaveEstablishmentInfo(Context Contexto, String ID_Establecimiento, String Nombre_Establecimiento) {

        SharedPreferences sharedPref = Contexto.getSharedPreferences("info_establecimiento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id_establecimiento", ID_Establecimiento);
        editor.putString("nombre_establecimiento", Nombre_Establecimiento);
        editor.apply();
    }

    @Override
    public void performGetItemsMenuByCategory(ArrayList<Establecimiento_Modelo> Establecimientos, ArrayList<ItemMenu_Modelo> Items_Menu) {
        Items_Menu_Categoria.clear();

        for(int i = 0; i < Items_Menu.size(); i++)
        {
            for( int j = 0; j < Establecimientos.size(); j++)
            {
                if(Items_Menu.get(i).getID_Establecimiento().equals(Establecimientos.get(j).getID_Establecimiento()))
                {
                    Items_Menu_Categoria.add((Items_Menu.get(i)));
                    break;
                }
            }
        }
        
        mListener.onSuccessGetItemsMenuByCategory(Items_Menu_Categoria);
    }

    @Override
    public void performGetEstablishmentName(ArrayList<Establecimiento_Modelo> Establecimientos, String ID_Establecimiento) {

        for(int i = 0; i < Establecimientos.size(); i++){

            if(ID_Establecimiento.equals(Establecimientos.get(i).getID_Establecimiento()))
            {
                mListener.onSuccessGetEstablishmentName(Establecimientos.get(i).getID_Establecimiento(), Establecimientos.get(i).getNombre());
                break;
            }
        }
    }
}
