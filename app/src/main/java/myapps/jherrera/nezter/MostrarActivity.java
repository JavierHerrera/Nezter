package myapps.jherrera.nezter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import myapps.jherrera.nezter.database.DbProductosHelper;

public class MostrarActivity extends AppCompatActivity implements View.OnClickListener {


    TextView textView;
    DbProductosHelper dbHelper;

    EditText etId;
    Button btnAumentar, btnDisminuir;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        textView = findViewById(R.id.tv_result);
        dbHelper = new DbProductosHelper(this);

        etId = findViewById(R.id.ed_id);
        btnAumentar = findViewById(R.id.btn_aumentar);
        btnDisminuir = findViewById(R.id.btn_diminuir);

        btnAumentar.setOnClickListener(this);
        btnDisminuir.setOnClickListener(this);

        showActivesProducts();
        alertDialog();
    }

    private void alertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Productos por agotarse");

        String lowStock = "";
        dbHelper.openDB();
        lowStock = dbHelper.getNameLowStock();
        dbHelper.closeDB();

        builder1.setMessage(lowStock);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();

        if (!Objects.equals(lowStock, "")) {
        alert11.show();
        }
    }

    private void showActivesProducts(){
        dbHelper.openDB();
        textView.setText(dbHelper.selectActivos());
        dbHelper.closeDB();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btn_aumentar:

                id = String.valueOf(etId.getText());
                if (id.length() >0 ){
                    dbHelper.increment(id);
                    showActivesProducts();
                }else {
                    Toast.makeText(this,"ID en blanco",Toast.LENGTH_LONG ).show();
                }
                break;

            case R.id.btn_diminuir:

                id = String.valueOf(etId.getText());
                if (id.length() >0 ){
                    dbHelper.drecement(id);
                    showActivesProducts();
                }else {
                    Toast.makeText(this,"ID en blanco",Toast.LENGTH_LONG ).show();
                }
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ConfiguracionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
