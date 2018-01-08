package myapps.jherrera.nezter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import myapps.jherrera.nezter.R;
import myapps.jherrera.nezter.adapters.ProductAdapter;
import myapps.jherrera.nezter.controllers.ProductController;
import myapps.jherrera.nezter.objects.RefreshCallback;

public class ConfiguracionActivity extends AppCompatActivity implements View.OnClickListener, RefreshCallback {

    ProductController controller;
    TextView textView;

    EditText etName, etStock;
    Button btnAdd, btnShow;

    //Elementos del adapter
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Configuración");
        setContentView(R.layout.activity_configuracion);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        controller = new ProductController(this);

        etName = findViewById(R.id.et_name);
        etStock = findViewById(R.id.et_stock);
        btnAdd = findViewById(R.id.button_add);
        btnShow = findViewById(R.id.button_goToActivityMostrar);

        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);

        showAllProducts();
    }

    public void showAllProducts() {
        // Obtener el Recycler
        recycler = findViewById(R.id.reciclador_configuracion);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ProductAdapter(controller.getAllProducts(),0,this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.button_add:

                String name = (etName.getText().toString());
                String stock = etStock.getText().toString();

                controller.addProduct(name, stock);
                showAllProducts();
                break;

            case R.id.button_goToActivityMostrar:

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refresh() {
            showAllProducts();
    }
}