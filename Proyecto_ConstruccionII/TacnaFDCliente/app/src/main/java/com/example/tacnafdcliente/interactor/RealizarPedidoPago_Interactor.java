package com.example.tacnafdcliente.interactor;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.culqi.Culqi;
import com.example.tacnafdcliente.Culqi.Card;
import com.example.tacnafdcliente.Culqi.Token;
import com.example.tacnafdcliente.Culqi.TokenCallback;
import com.example.tacnafdcliente.interfaces.RealizarPedidoPago;
import com.example.tacnafdcliente.modelo.Establecimiento_Modelo;
import com.example.tacnafdcliente.modelo.Pedido_Modelo;
import com.example.tacnafdcliente.modelo.Usuario_Modelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalPayment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealizarPedidoPago_Interactor implements RealizarPedidoPago.Interactor {

    private RealizarPedidoPago.onOperationListener mListener;
    private ValueEventListener valueEventListener_GetClientName;
    private String Json_Result = "";
    private String Precio_Dolar = "";
    private Double Soles_A_Dolares = 0.0;
    private Double Comision = 0.0;
    private Double Total_Con_Comision = 0.0;

    public RealizarPedidoPago_Interactor(RealizarPedidoPago.onOperationListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public void performSaveOrder(DatabaseReference Database_Reference, Pedido_Modelo Pedido) {
        Database_Reference.child(Pedido.getID_Pedido()).setValue(Pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    mListener.onSuccessSaveOrder();
                }
                else
                {
                    mListener.onFailureSaveOrder();
                }

            }
        });
    }

    @Override
    public void performGetOrderDataSharedPreference(Context Contexto) {
        SharedPreferences sharedPref = Contexto.getApplicationContext().getSharedPreferences("info_pedido", Context.MODE_PRIVATE);
        String ID_Usuario = sharedPref.getString("id_usuario","");
        String ID_Establecimiento = sharedPref.getString("id_establecimiento","");
        String Direccion_Destino = sharedPref.getString("direccion_destino","");
        String Punto_Geografico_Destino = sharedPref.getString("punto_geografico_destino","");

        if(ID_Usuario.length() != 0){
            mListener.onSuccessGetOrderDataSharedPreference(ID_Usuario, ID_Establecimiento, Direccion_Destino, Punto_Geografico_Destino);
        }

    }

    @Override
    public void performGetClientName(DatabaseReference Database_Reference, String ID_Usuario) {
        final Query query = Database_Reference.orderByChild("id_Usuario_Cliente").equalTo(ID_Usuario);

        valueEventListener_GetClientName = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    Usuario_Modelo Usuario = postSnapShot.getValue(Usuario_Modelo.class);
                    mListener.onSuccessGetClientName(Usuario);
                }
                query.removeEventListener(valueEventListener_GetClientName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetClientName();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener_GetClientName);
    }

    @Override
    public void performGetEstablishmentData(DatabaseReference Database_Reference, String ID_Establecimiento) {

        final Query query = Database_Reference.orderByChild("id_Establecimiento").equalTo(ID_Establecimiento);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapShot : snapshot.getChildren())
                {
                    Establecimiento_Modelo Establecimiento = postSnapShot.getValue(Establecimiento_Modelo.class);
                    mListener.onSuccessGetEstablishmentData(Establecimiento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListener.onFailureGetEstablishmentData();
            }
        });

    }

    @Override
    public void performGetJsonResultFixer(String Url_Fixer) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;

        try {
            URL url = new URL(Url_Fixer);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null)
            {
                mListener.onFailureGetJsonResultFixer();
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0)
            {
                mListener.onFailureGetJsonResultFixer();
            }

            forecastJsonStr = buffer.toString();
            Json_Result = buffer.toString();
            mListener.onSuccessGetJsonResultFixer(Json_Result);

        }
        catch (IOException e)
        {
            mListener.onFailureGetJsonResultFixer();
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(reader != null)
            {
                try {
                    reader.close();
                }
                catch (final IOException e)
                {

                }
            }
        }
    }

    @Override
    public void performGetDollarPrice(String Json_Result) {

        try{

            JSONObject reader = new JSONObject(Json_Result);
            JSONObject rates = reader.getJSONObject("rates");
            Precio_Dolar = rates.getString("USD");
            mListener.onSuccessGetDollarPrice(Precio_Dolar);
        }
        catch (Exception e)
        {
            mListener.onFailureGetDollarPrice();
        }
    }

    @Override
    public void performGetPaymentWithCommission(String Precio_Dolar, Double Total) {
        Soles_A_Dolares = Double.parseDouble(Precio_Dolar) * Total;
        Comision = Soles_A_Dolares * (5.4/100) + 0.3;
        Total_Con_Comision = Soles_A_Dolares + Comision;
        Comision = Total_Con_Comision * (5.4/100) + 0.3;
        Total_Con_Comision = Soles_A_Dolares + Comision;
        mListener.onSuccessGetPaymentWithCommission(Total_Con_Comision);
    }

    @Override
    public void performMakeCardPayment(Context Contexto, String Codigo_Culqi, final String ID_Pedido, final Double Total_Pagar, final String Correo_Electronico, String Numero_Tarjeta,
                                       String CVV_Tarjeta, String Fecha_Vencimiento_Tarjeta) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Numero_Tarjeta = Numero_Tarjeta.replace(" ","");

        String[] Fecha_Anio = Fecha_Vencimiento_Tarjeta.split("/");
        final String[] Keys = Codigo_Culqi.split("/");

        final int Total_Pagar_Centimos = (int) (Total_Pagar*100);

        Card Tarjeta = new Card(Numero_Tarjeta, CVV_Tarjeta, Integer.parseInt(Fecha_Anio[0]), Integer.parseInt("20"+Fecha_Anio[1]), Correo_Electronico);

        Token token = new Token(Keys[1]);

        token.createToken(Contexto, Tarjeta, new TokenCallback() {
            @Override
            public void onSuccess(JSONObject token) {

                try
                {

                    Culqi culqi = new Culqi();
                    culqi.public_key = Keys[0];
                    culqi.secret_key = Keys[1];



                    Map<String, Object> charge = new HashMap<String, Object>();
                    Map<String, Object> metadata = new HashMap<String, Object>();

                    metadata.put("oder_id", ID_Pedido);

                    charge.put("amount",Total_Pagar_Centimos);
                    charge.put("currency_code","PEN");
                    charge.put("email",Correo_Electronico);
                    charge.put("source_id", token.get("id").toString());
                    charge.put("metadata", metadata);

                    Map<String, Object> Informacion_Pago = culqi.charge.create(charge);

                    if(Informacion_Pago.get("statement_descriptor").toString() != null)
                    {
                        mListener.onSuccessMakeCardPayment();
                    }


                }
                catch (Exception ex)
                {
                    mListener.onFailureMakeCardPayment();
                }

            }

            @Override
            public void onError(Exception error) {
                mListener.onFailureMakeCardPayment();
            }
        });
    }
}
