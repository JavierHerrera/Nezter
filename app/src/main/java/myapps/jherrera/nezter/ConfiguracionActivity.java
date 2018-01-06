package myapps.jherrera.nezter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import myapps.jherrera.nezter.database.DbProductosHelper;

public class ConfiguracionActivity extends AppCompatActivity implements View.OnClickListener {

    DbProductosHelper dbHelper;
    TextView textView;

    EditText etName, etStock;
    Button btnAdd, btnShow, btnUpdate, btnDelete;
    TextView tvId;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        dbHelper = new DbProductosHelper(this);

        textView = findViewById(R.id.tv_result);

        etName = findViewById(R.id.et_name);
        etStock = findViewById(R.id.et_stock);
        btnAdd = findViewById(R.id.button_add);
        btnShow = findViewById(R.id.button_show);
        btnUpdate = findViewById(R.id.button_activar_desactivar);
        btnDelete = findViewById(R.id.button_delete);
        tvId = findViewById(R.id.tv_id);

        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        showAllProducts();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.button_add:

                Toast.makeText(this,"Registrando...",Toast.LENGTH_SHORT ).show();
                String nombre = String.valueOf(etName.getText());
                String stock = String.valueOf((etStock.getText()));

                if (nombre.length() == 0){
                    Toast.makeText(this,"Nombre incorrecto",Toast.LENGTH_LONG ).show();
                }
                else {

                    try {
                        long result = dbHelper.addProduct(nombre, Integer.parseInt(stock));

                        if (result > 0) {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    showAllProducts();
                }
                break;

            case R.id.button_show:
                Intent intent = new Intent(this, MostrarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.button_activar_desactivar:
                id = String.valueOf(tvId.getText());
                if (id.length() >0 ){
                    boolean update = dbHelper.updateActive(id);
                    if (update){
                        Toast.makeText(this,"Cambio realizado",Toast.LENGTH_LONG ).show();
                        showAllProducts();
                    }
                }else {
                    Toast.makeText(this,"ID en blanco",Toast.LENGTH_LONG ).show();

                }
                break;

            case R.id.button_delete:
                id = String.valueOf(tvId.getText());
                if (id.length() >0 ) {
                    boolean delete = dbHelper.deleteProduct(id);
                    if (delete) {
                        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_LONG).show();
                    }
                    showAllProducts();
                }else {
                    Toast.makeText(this,"ID en blanco",Toast.LENGTH_LONG ).show();
                }
                break;
        }
    }

    private void showAllProducts(){
        dbHelper.openDB();
        textView.setText(dbHelper.selectAll());
        dbHelper.closeDB();
    }
}