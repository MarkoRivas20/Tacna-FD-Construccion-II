package com.example.tacnafdcliente.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.culqi.Culqi;
import com.example.tacnafdcliente.Culqi.Card;
import com.example.tacnafdcliente.Culqi.Token;
import com.example.tacnafdcliente.Culqi.TokenCallback;
import com.example.tacnafdcliente.R;
import com.example.tacnafdcliente.interfaces.RealizarPedidoPago;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.example.tacnafdcliente.presentador.RealizarPedidoPago_Presentador;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.uphyca.creditcardedittext.CreditCardDateEditText;
import com.uphyca.creditcardedittext.CreditCardNumberEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RealizarPedidoPago_Vista extends Fragment implements RealizarPedidoPago.View {

    public RealizarPedidoPago_Vista() {
        // Required empty public constructor
    }

    public RealizarPedidoPago_Presentador mPresenter;
    public DatabaseReference mReference_Cliente;
    public DatabaseReference mReference_Establecimiento;
    public DatabaseReference mReference_Pedido;

    private static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration CONFIG;

    TextView LblNombre_Cliente;
    TextView LblNombre_Establecimiento;
    TextView LblDireccion_Destino;
    TextView LblOrden_Pedido;
    TextView LblSubTotal;
    TextView LblEnvio;
    TextView LblTotal;
    TextView LblRepartidores;
    TextView LblMetodo_Pago;
    TextView tv_card_number;
    TextView tv_member_name;
    TextView tv_card_date;
    TextView tv_card_cvv;

    CreditCardNumberEditText credit_CardNumber_EditText;
    CreditCardDateEditText Credit_CardDate_EditText;
    EditText Credit_CardCVV_EditText;
    EditText Credit_CardName_EditText;
    EditText TxtCorreo_Electronico;

    ImageView ImgTarjeta;

    Button BtnPedir;
    Button BtnPagar;

    StateProgressBar State_ProgressBar;

    String[] descriptionData = {"Datos", "Pedido", "Pago"};
    String ID_Usuario = "";
    String ID_Establecimiento = "";
    String ID_Pedido = "";
    String Direccion_Destino = "";
    String Punto_Geografico_Destino = "";
    String Descripcion_Pedido = "";
    String Nombre_Cliente = "";
    String Patron_Fecha = "dd/MM/yyyy hh:mm:ss";
    String Fecha_Actual = "";
    String Codigo_Paypal = "";
    String Url_Fixer = "https://data.fixer.io/api/latest?access_key=Your_Api_Key&base=PEN&symbols=USD&format=1";
    String Codigo_Culqi = "";
    String Statement_Descriptor = null;

    Double SubTotal = 0.0;
    Double Total = 0.0;

    RadioButton RD_Nuestros_Repartidores;
    RadioButton RD_Repartidor_Tercero;
    RadioButton RD_Paypal;
    RadioButton RD_Tarjetas;
    RadioButton RD_Vendemas;

    LinearLayout LinearLayout_Opciones;
    LinearLayout LinearLayout_Contraentrega;
    LinearLayout LinearLayout_Tarjeta;
    LinearLayout LinearLayout_Paypal;

    ConstraintLayout Constraint_Layout_Medios_Pago;
    ConstraintLayout ConstraintLayout_Tarjeta_Delante;
    ConstraintLayout ConstraintLayout_Tarjeta_Atras;

    SimpleDateFormat simpleDateFormat;

    ListarPedido_Vista listarPedido_vista;

    AlertDialog.Builder mBuilder;

    View Tarjeta_Layout;

    AlertDialog dialog;

    NavigationView Navigation_View;

    Toolbar Tool_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_realizar_pedido_pago__vista, container, false);

        simpleDateFormat = new SimpleDateFormat(Patron_Fecha);
        Fecha_Actual = simpleDateFormat.format(new Date());

        final Bundle Detalle_Pedido = getArguments();

        mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setCancelable(true);
        Tarjeta_Layout = getLayoutInflater().inflate(R.layout.dialog_tarjeta,null);
        mBuilder.setView(Tarjeta_Layout);
        dialog = mBuilder.create();

        mPresenter = new RealizarPedidoPago_Presentador(this);
        mReference_Cliente = FirebaseDatabase.getInstance().getReference().child("Usuario_Cliente");
        mReference_Establecimiento = FirebaseDatabase.getInstance().getReference().child("Establecimiento");
        mReference_Pedido = FirebaseDatabase.getInstance().getReference().child("Pedido");

        tv_card_number = (TextView) Tarjeta_Layout.findViewById(R.id.tv_card_number);
        tv_member_name = (TextView) Tarjeta_Layout.findViewById(R.id.tv_member_name);
        tv_card_date = (TextView) Tarjeta_Layout.findViewById(R.id.tv_card_date);
        tv_card_cvv = (TextView) Tarjeta_Layout.findViewById(R.id.tv_card_cvv);
        credit_CardNumber_EditText = (CreditCardNumberEditText) Tarjeta_Layout.findViewById(R.id.credit_CardNumber_EditText);
        Credit_CardDate_EditText = (CreditCardDateEditText) Tarjeta_Layout.findViewById(R.id.Credit_CardDate_EditText);
        Credit_CardCVV_EditText = (EditText) Tarjeta_Layout.findViewById(R.id.Credit_CardCVV_EditText);
        Credit_CardName_EditText = (EditText) Tarjeta_Layout.findViewById(R.id.Credit_CardName_EditText);
        TxtCorreo_Electronico = (EditText) Tarjeta_Layout.findViewById(R.id.TxtCorreo_Electronico);
        BtnPagar = (Button) Tarjeta_Layout.findViewById(R.id.BtnPagar);
        ImgTarjeta = (ImageView) Tarjeta_Layout.findViewById(R.id.ImgTarjeta);
        ConstraintLayout_Tarjeta_Delante = (ConstraintLayout) Tarjeta_Layout.findViewById(R.id.ConstraintLayout_Tarjeta_Delante);
        ConstraintLayout_Tarjeta_Atras = (ConstraintLayout) Tarjeta_Layout.findViewById(R.id.ConstraintLayout_Tarjeta_Atras);
        LblNombre_Cliente = (TextView) view.findViewById(R.id.LblNombre_Cliente);
        LblNombre_Establecimiento = (TextView) view.findViewById(R.id.LblNombre_Establecimiento);
        LblDireccion_Destino = (TextView) view.findViewById(R.id.LblDireccion_Destino);
        LblOrden_Pedido = (TextView) view.findViewById(R.id.LblOrden_Pedido);
        RD_Nuestros_Repartidores = (RadioButton) view.findViewById(R.id.RD_Nuestros_Repartidores);
        RD_Repartidor_Tercero = (RadioButton) view.findViewById(R.id.RD_Repartidor_Tercero);
        Constraint_Layout_Medios_Pago = (ConstraintLayout) view.findViewById(R.id.Constraint_Layout_Medios_Pago);
        RD_Paypal = (RadioButton) view.findViewById(R.id.RD_Paypal);
        RD_Tarjetas = (RadioButton) view.findViewById(R.id.RD_Tarjetas);
        RD_Vendemas = (RadioButton) view.findViewById(R.id.RD_Vendemas);
        LblSubTotal = (TextView) view.findViewById(R.id.TxtSubTotal);
        LblEnvio = (TextView) view.findViewById(R.id.TxtEnvio);
        LblTotal = (TextView) view.findViewById(R.id.TxtTotal);
        LinearLayout_Opciones = (LinearLayout) view.findViewById(R.id.LinearLayout_Opciones);
        LinearLayout_Contraentrega = (LinearLayout) view.findViewById(R.id.LinearLayout_Contraentrega);
        LinearLayout_Tarjeta = (LinearLayout) view.findViewById(R.id.LinearLayout_Tarjeta);
        LinearLayout_Paypal = (LinearLayout) view.findViewById(R.id.LinearLayout_Paypal);
        LblRepartidores = (TextView) view.findViewById(R.id.LblRepartidores);
        LblMetodo_Pago = (TextView) view.findViewById(R.id.LblMetodo_Pago);
        BtnPedir = (Button) view.findViewById(R.id.BtnPedir);
        Navigation_View = (NavigationView) getActivity().findViewById(R.id.nav_view);
        Tool_bar = (Toolbar) getActivity().findViewById(R.id.toolbar);


        listarPedido_vista = new ListarPedido_Vista();

        Descripcion_Pedido = Detalle_Pedido.getString("descripcion_pedido");
        LblOrden_Pedido.setText(Descripcion_Pedido.replace(", ","\n"));

        SubTotal = Detalle_Pedido.getDouble("sub_total");
        LblSubTotal.setText("SubTotal: " + SubTotal);

        Total = SubTotal;
        LblTotal.setText("Total: " + Total);

        mPresenter.GetOrderDataSharedPreference(getActivity());
        mPresenter.GetClientName(mReference_Cliente, ID_Usuario);
        mPresenter.GetEstablishmentData(mReference_Establecimiento, ID_Establecimiento);

        State_ProgressBar = (StateProgressBar) view.findViewById(R.id.State_ProgressBar);

        State_ProgressBar.setStateDescriptionData(descriptionData);

        BtnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ID_Pedido = mReference_Pedido.push().getKey();

                if(RD_Repartidor_Tercero.isChecked())
                {
                    Pedido_Modelo Pedido = new Pedido_Modelo(ID_Pedido, ID_Establecimiento, ID_Usuario, Descripcion_Pedido, Fecha_Actual,
                            Total, LblDireccion_Destino.getText().toString(), "Separado", Punto_Geografico_Destino);

                    mPresenter.SaveOrder(mReference_Pedido, Pedido);
                }
                else
                {
                    if(RD_Paypal.isChecked())
                    {
                        mPresenter.GetJsonResultFixer(Url_Fixer);
                    }
                    if(RD_Vendemas.isChecked())
                    {
                        Pedido_Modelo Pedido = new Pedido_Modelo(ID_Pedido,ID_Establecimiento,ID_Usuario, Descripcion_Pedido, Fecha_Actual,
                                Total, LblDireccion_Destino.getText().toString(), "Pendiente", "Contraentrega", Punto_Geografico_Destino);

                        mPresenter.SaveOrder(mReference_Pedido, Pedido);
                    }
                    if(RD_Tarjetas.isChecked())
                    {
                        dialog.show();
                    }
                }

            }
        });

        RD_Nuestros_Repartidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RD_Nuestros_Repartidores.isChecked())
                {
                    Total = SubTotal + 5;
                    Constraint_Layout_Medios_Pago.setVisibility(View.VISIBLE);
                    LblEnvio.setText("Envio: S/. 5.0");
                    LblTotal.setText("Total: " + Total);
                }
            }
        });

        RD_Repartidor_Tercero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RD_Repartidor_Tercero.isChecked())
                {
                    Total = SubTotal;
                    Constraint_Layout_Medios_Pago.setVisibility(View.GONE);
                    LblEnvio.setText("Envio: S/. 0.0");
                    LblTotal.setText("Total: " + SubTotal);
                }
            }
        });

        RD_Paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RD_Tarjetas.setChecked(false);
                RD_Vendemas.setChecked(false);
            }
        });

        RD_Tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RD_Paypal.setChecked(false);
                RD_Vendemas.setChecked(false);
            }
        });

        RD_Vendemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RD_Paypal.setChecked(false);
                RD_Tarjetas.setChecked(false);
            }
        });

        credit_CardNumber_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_card_number.setText(s.toString());
                Validation(s.toString());
            }
        });

        Credit_CardDate_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_card_date.setText(s.toString());
            }
        });

        Credit_CardName_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_member_name.setText(s.toString());
            }
        });

        Credit_CardCVV_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_card_cvv.setText(s.toString());
            }
        });

        Credit_CardCVV_EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    ConstraintLayout_Tarjeta_Delante.setVisibility(View.INVISIBLE);
                    ConstraintLayout_Tarjeta_Atras.setVisibility(View.VISIBLE);
                }
                else
                {
                    ConstraintLayout_Tarjeta_Delante.setVisibility(View.VISIBLE);
                    ConstraintLayout_Tarjeta_Atras.setVisibility(View.INVISIBLE);
                }
            }
        });

        BtnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.MakeCardPayment(getActivity(), Codigo_Culqi, ID_Pedido, Total, TxtCorreo_Electronico.getText().toString(), credit_CardNumber_EditText.getText().toString(),
                                Credit_CardCVV_EditText.getText().toString(), Credit_CardDate_EditText.getText().toString());
                    }
                }).start();

            }
        });



        return view;
    }

    @Override
    public void onSaveOrderSuccessful() {
        Toast.makeText(getActivity(),"Pedido Realizado Satisfactoriamente", Toast.LENGTH_SHORT).show();
        Navigation_View.setCheckedItem(R.id.nav_pedido);
        Tool_bar.setNavigationIcon(R.drawable.icon_toolbar);
        Tool_bar.setBackground(new ColorDrawable(Color.parseColor("#0031A8")));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, listarPedido_vista).addToBackStack("ListarItemMenu_Vista").commit();
    }

    @Override
    public void onSaveOrderFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetOrderDataSharedPreferenceSuccessful(String ID_Usuario, String ID_Establecimiento, String Direccion_Destino, String Punto_Geografico_Destino) {
        this.ID_Usuario = ID_Usuario;
        this.ID_Establecimiento = ID_Establecimiento;
        this.Direccion_Destino = Direccion_Destino;
        this.Punto_Geografico_Destino = Punto_Geografico_Destino;

        LblDireccion_Destino.setText(Direccion_Destino);
    }

    @Override
    public void onGetClientNameSuccessful(Usuario_Modelo Usuario) {
        Nombre_Cliente = Usuario.getNombre() + " " + Usuario.getApellido();
        LblNombre_Cliente.setText(Nombre_Cliente);
    }

    @Override
    public void onGetClientNameFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetEstablishmentDataSuccessful(Establecimiento_Modelo Establecimiento) {
        LblNombre_Establecimiento.setText(Establecimiento.getNombre());
        Codigo_Paypal = Establecimiento.getCodigo_Paypal();
        Codigo_Culqi = Establecimiento.getCodigo_Culqi();

        CONFIG = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Codigo_Paypal);

        Intent intent=new Intent(getActivity(),PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,CONFIG);
        getActivity().startService(intent);

        if(Establecimiento.getCodigo_Paypal() == null){
            LinearLayout_Paypal.setVisibility(View.GONE);
        }
        else
        {
            LinearLayout_Paypal.setVisibility(View.VISIBLE);
        }

        if(Establecimiento.getCodigo_Culqi() == null){
            LinearLayout_Tarjeta.setVisibility(View.GONE);
        }
        else
        {
            LinearLayout_Tarjeta.setVisibility(View.VISIBLE);
        }

        if(Establecimiento.getUrl_Qr() == null){
            LinearLayout_Contraentrega.setVisibility(View.GONE);
            LinearLayout_Opciones.setVisibility(View.GONE);
        }
        else
        {
            LinearLayout_Contraentrega.setVisibility(View.VISIBLE);
            LinearLayout_Opciones.setVisibility(View.VISIBLE);
        }

        if(Establecimiento.getCodigo_Paypal() == null && Establecimiento.getCodigo_Culqi() == null && Establecimiento.getUrl_Qr() == null)
        {
            LblMetodo_Pago.setVisibility(View.GONE);
            RD_Nuestros_Repartidores.setVisibility(View.GONE);
            LblRepartidores.setVisibility(View.GONE);
        }
        else
        {
            LblMetodo_Pago.setVisibility(View.VISIBLE);
            RD_Nuestros_Repartidores.setVisibility(View.VISIBLE);
            LblRepartidores.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetEstablishmentDataFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetJsonResultFixerSuccessful(String Json_Result) {
        mPresenter.GetDollarPrice(Json_Result);
    }

    @Override
    public void onGetJsonResultFixerFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetDollarPriceSuccessful(String Precio_Dolar) {
        mPresenter.GetPaymentWithCommission(Precio_Dolar, Total);
    }

    @Override
    public void onGetDollarPriceFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPaymentWithCommissionSuccessful(Double Total_Con_Comision) {

        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(String.valueOf(Total_Con_Comision)),"USD","Pago del Pedido", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent=new Intent(getActivity().getApplicationContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,CONFIG);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onMakeCardPaymentSuccessful() {

        Pedido_Modelo Pedido = new Pedido_Modelo(ID_Pedido,ID_Establecimiento,ID_Usuario, Descripcion_Pedido, Fecha_Actual,
                Total, LblDireccion_Destino.getText().toString(), "Pendiente", "Tarjeta de Credito o Debito", Punto_Geografico_Destino);

        mPresenter.SaveOrder(mReference_Pedido, Pedido);

        dialog.dismiss();


    }

    @Override
    public void onMakeCardPaymentFailure() {
        Toast.makeText(getActivity(),"Algo salio mal", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==PAYPAL_REQUEST_CODE){
            if(resultCode==getActivity().RESULT_OK){
                PaymentConfirmation confirmation= data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation!=null){
                    try{

                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        Pedido_Modelo Pedido = new Pedido_Modelo(ID_Pedido,ID_Establecimiento,ID_Usuario, Descripcion_Pedido, Fecha_Actual,
                                Total, LblDireccion_Destino.getText().toString(), "Pendiente", "PayPal", Punto_Geografico_Destino);

                        mPresenter.SaveOrder(mReference_Pedido, Pedido);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }

            }
            else if(requestCode== Activity.RESULT_CANCELED){
                Toast.makeText(getActivity().getApplicationContext(),"Cancelada",Toast.LENGTH_SHORT).show();

            }else if(requestCode==PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(getActivity().getApplicationContext(),"Invalida",Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode,resultCode,data);
    }

    public void Validation(String Numero_Tarjeta){
        if(Numero_Tarjeta.length() > 2)
        {
            if(Integer.valueOf(Numero_Tarjeta.substring(0,2)) == 41)
            {
                ImgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.ic_visa));
            }
            else if (Integer.valueOf(Numero_Tarjeta.substring(0,2)) == 51)
            {
                ImgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.ic_mastercard));
            }
            else if (Integer.valueOf(Numero_Tarjeta.substring(0,2)) == 36 || Integer.valueOf(Numero_Tarjeta.substring(0,2)) == 38 ||
                    Integer.valueOf(Numero_Tarjeta.substring(0,3)) == 300 || Integer.valueOf(Numero_Tarjeta.substring(0,3)) == 305)
            {
                ImgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.ic_dinners));
            }
            else if (Integer.valueOf(Numero_Tarjeta.substring(0,2)) == 37)
            {
                ImgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.ic_amex));
            }
        }


    }
}