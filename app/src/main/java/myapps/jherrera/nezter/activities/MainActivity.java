package myapps.jherrera.nezter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import myapps.jherrera.nezter.R;
import myapps.jherrera.nezter.adapters.ProductAdapter;
import myapps.jherrera.nezter.controllers.ProductController;
import myapps.jherrera.nezter.objects.RefreshCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RefreshCallback{

    Button btnAumentar, btnDisminuir;

    //Elementos del adapter
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    ProductController controller = new ProductController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAumentar = findViewById(R.id.btn_configuracion);
        btnAumentar.setOnClickListener(this);

        showActivesProducts();
    }


    private void showActivesProducts(){
        // Obtener el Recycler
        recycler = findViewById(R.id.reciclador_main);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ProductAdapter(controller.getAllProducts(),1,this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btn_configuracion:

                Intent intent = new Intent(this, ConfiguracionActivity.class);
                startActivity(intent);
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

    @Override
    public void refresh() {
        showActivesProducts();
    }
}
