package com.example.tacnafdcliente.vista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.adaptador.DetallePedido_Adaptador;
import com.example.tacnafdcliente.interfaces.RealizarPedidoDetalle;
import com.example.tacnafdcliente.modelo.DetallePedido_Modelo;
import com.example.tacnafdcliente.modelo.ItemMenu_Modelo;
import com.example.tacnafdcliente.presentador.RealizarPedidoDetalle_Presentador;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.numberpicker.NumberPicker;


public class RealizarPedidoDetalle_Vista extends Fragment implements RealizarPedidoDetalle.View {


    public RealizarPedidoDetalle_Vista() {
        // Required empty public constructor
    }
    private RecyclerView Recycler_View;
    private DetallePedido_Adaptador Adaptador;
    private RecyclerView.LayoutManager Layout_Manager;

    public RealizarPedidoDetalle_Presentador mPresenter;
    public DatabaseReference mReference;

    RealizarPedidoPago_Vista realizarPedidoPago_vista;

    String[] descriptionData = {"Datos", "Pedido", "Pago"};
    String ID_Establecimiento = "";
    String ID_Item_Menu = "";

    ArrayList<ItemMenu_Modelo> Items_Menu = new ArrayList<>();
    List<DetallePedido_Modelo> Items_Detalle_Pedido = new ArrayList<>();

    StateProgressBar State_ProgressBar;

    Spinner Spinner_Items_Menu;

    Button BtnAgregar;
    Button BtnSiguiente;

    NumberPicker Number_Picker;

    TextView TxtSub_Total;

    int Posicion_Item = 0;
    int Posicion_Adapter = 0;

    Double Subtotal = 0.0;
    Double Precio_Total_Item = 0.0;

    Boolean Item_Agregado = false;

    DecimalFormat Decimal_Format = new DecimalFormat("#.00");

    Bundle Detalle_Pedido = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realizar_pedido_detalle__vista, container, false);

        mPresenter = new RealizarPedidoDetalle_Presentador(this);
        mReference = FirebaseDatabase.getInstance().getReference().child("ItemMenu");

        Spinner_Items_Menu = (Spinner) view.findViewById(R.id.Spinner_Items_Menu);
        BtnAgregar = (Button) view.findViewById(R.id.BtnAgregar);
        Number_Picker = (NumberPicker) view.findViewById(R.id.Number_Picker);
        State_ProgressBar = (StateProgressBar) view.findViewById(R.id.State_ProgressBar);
        Recycler_View = (RecyclerView) view.findViewById(R.id.Recycler_DetallePedido) ;
        TxtSub_Total = (TextView) view.findViewById(R.id.TxtSubTotal);
        BtnSiguiente = (Button) view.findViewById(R.id.BtnSiguiente);

        realizarPedidoPago_vista = new RealizarPedidoPago_Vista();

        mPresenter.GetEstablishmentInfo(getActivity());
        mPresenter.GetItemsMenuForEstablishment(mReference, ID_Establecimiento);

        State_ProgressBar.setStateDescriptionData(descriptionData);

        Recycler_View.setHasFixedSize(true);
        Layout_Manager = new LinearLayoutManager(getActivity());
        Recycler_View.setLayoutManager(Layout_Manager);

        Adaptador = new DetallePedido_Adaptador(Items_Detalle_Pedido,getActivity());
        Recycler_View.setAdapter(Adaptador);

        TxtSub_Total.setText("Subtotal: S/." + Subtotal);

        Spinner_Items_Menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_Item_Menu = Items_Menu.get(position).getID_Item_Menu();
                Posicion_Item = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item_Agregado = CheckList(Items_Detalle_Pedido);

                if(Item_Agregado)
                {
                    CheckNumberItems(Number_Picker.getProgress(), Items_Detalle_Pedido.get(Posicion_Adapter).getCantidad());
                }
                else
                {

                    Precio_Total_Item = Double.parseDouble(Items_Menu.get(Posicion_Item).getPrecio()) * Number_Picker.getProgress();
                    DetallePedido_Modelo Detalle_Pedido = new DetallePedido_Modelo(ID_Item_Menu, Items_Menu.get(Posicion_Item).getNombre(),
                            Number_Picker.getProgress(), Double.parseDouble(Items_Menu.get(Posicion_Item).getPrecio()),
                            Double.parseDouble(Decimal_Format.format(Precio_Total_Item)),Items_Menu.get(Posicion_Item).getUrl_Imagen());

                    AddItemToDetail(Detalle_Pedido);
                }
                ShowOrderDetail();
            }
        });

        BtnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.GetOrderDescription(Items_Detalle_Pedido);

            }
        });

        return view;
    }

    public Boolean CheckList(List<DetallePedido_Modelo> Items_Detalle_Pedido){

        if(Items_Detalle_Pedido.size() != 0)
        {
            for(int i = 0; i< Items_Detalle_Pedido.size();i++)
            {
                if(String.valueOf(ID_Item_Menu).equals(String.valueOf(Items_Detalle_Pedido.get(i).getID_Item_Menu())))
                {
                    Item_Agregado = true;
                    Posicion_Adapter = i;
                    break;
                }
                else
                {
                    Item_Agregado = false;

                }
            }
        }
        else
        {
            Item_Agregado = false;
        }

        return Item_Agregado;
    }

    public void CheckNumberItems(int Cantidad_Seleccionada, int Cantidad_Existente){
        int Nueva_Cantidad = Cantidad_Seleccionada + Cantidad_Existente;

        if(Nueva_Cantidad <= 20)
        {
            int Cantidad_Anterior = Items_Detalle_Pedido.get(Posicion_Adapter).getCantidad();
            int Cantidad_Nueva = Number_Picker.getProgress() + Cantidad_Anterior;
            Precio_Total_Item = Double.parseDouble(Items_Menu.get(Posicion_Item).getPrecio()) * (Cantidad_Nueva);

            Items_Detalle_Pedido.get(Posicion_Adapter).setCantidad(Cantidad_Nueva);
            Items_Detalle_Pedido.get(Posicion_Adapter).setPrecio_Total(Double.parseDouble(Decimal_Format.format(Precio_Total_Item)));

            Subtotal = Subtotal + (Double.parseDouble(Items_Menu.get(Posicion_Item).getPrecio()) * Number_Picker.getProgress());
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"Solo se permite una cantidad maxima de 20",Toast.LENGTH_SHORT).show();
        }
    }

    public void AddItemToDetail(DetallePedido_Modelo Detalle_Pedido){

        Items_Detalle_Pedido.add(Detalle_Pedido);

        Subtotal = Subtotal + Double.parseDouble(Decimal_Format.format(Precio_Total_Item));
    }

    public void ShowOrderDetail(){

        TxtSub_Total.setText("Subtotal: S/."+Decimal_Format.format(Subtotal));

        Adaptador = new DetallePedido_Adaptador(Items_Detalle_Pedido,getActivity());

        Adaptador.setOnItemClickListener(new DetallePedido_Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Subtotal=Subtotal-Items_Detalle_Pedido.get(position).getPrecio_Total();

                TxtSub_Total.setText("Subtotal: S/." + Subtotal);

                Items_Detalle_Pedido.remove(position);
                Adaptador.notifyItemRemoved(position);
            }
        });
        Recycler_View.setAdapter(Adaptador);
    }

    @Override
    public void onGetItemsMenuForEstablishmentSuccessful(ArrayList<ItemMenu_Modelo> Items_Menu, ArrayList<String> Nombre_Items_Menu) {

        this.Items_Menu = Items_Menu;
        Spinner_Items_Menu.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Nombre_Items_Menu));
    }

    @Override
    public void onGetItemsMenuForEstablishmentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetOrderDescriptionSuccessful(String Descripcion_Pedido) {
        Detalle_Pedido.putString("descripcion_pedido",Descripcion_Pedido);
        Detalle_Pedido.putDouble("sub_total",Subtotal);

        realizarPedidoPago_vista.setArguments(Detalle_Pedido);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, realizarPedidoPago_vista).addToBackStack(null).commit();
    }

    @Override
    public void onGetOrderDescriptionFailure() {
        Toast.makeText(getActivity(),"Seleccione al menos un item",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentInfoSuccessful(String ID_Establecimiento) {
        this.ID_Establecimiento = ID_Establecimiento;
    }
}