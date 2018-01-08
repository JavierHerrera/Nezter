package myapps.jherrera.nezter.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import myapps.jherrera.nezter.R;
import myapps.jherrera.nezter.controllers.ProductController;
import myapps.jherrera.nezter.objects.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    ProductController controller;
    private List<Product> items;
    private int flagActivity; //0 ConfiguracionActivity, 1 MainActivity
    private  Context context;


    public ProductAdapter(List items, int flagActivity, Context context) {
        this.items = items;
        this.flagActivity = flagActivity;
        this.context = context;
        controller = new ProductController(context);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView stock;
        private TextView lowStock;
        private ImageView btnDelete;
        private CheckBox active;
        private RelativeLayout lyConfigurationActivity;
        private LinearLayout lyMainActivity;
        private Button more;
        private Button less;

        public ProductViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.tv_nombre_producto);
            stock =  v.findViewById(R.id.tv_stock_producto);
            lowStock = v.findViewById(R.id.tv_lowStock);
            btnDelete =  v.findViewById(R.id.img_delete);
            active = v.findViewById(R.id.checkbox_active);
            lyConfigurationActivity = v.findViewById(R.id.ly_configuracion);
            lyMainActivity = v.findViewById(R.id.ly_mian);
            more = v.findViewById(R.id.btn_one_more);
            less =v.findViewById(R.id.btn_one_less);

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_list, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ProductViewHolder holder, final int position) {
        final Product product = items.get(position);

        holder.nombre.setText(product.getName());
        holder.stock.setText(product.getStock()+"");
        holder.lowStock.setVisibility(showLowStock(product.getStock()));
        holder.active.setChecked(product.isActive());

        if ( flagActivity == 1){
            holder.lyMainActivity.setVisibility(View.VISIBLE);
        }else {
            holder.lyConfigurationActivity.setVisibility(View.VISIBLE);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.alertDialog(product.getName() ,product.getId());
            }
        });

        holder.active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.switchActiveInactive(String.valueOf(product.getId()));
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.incrementStock(product.getId());
            }
        });

        holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.decrementStock(product.getId());
            }
        });

    }

    private int showLowStock(int stock){
        if (stock<6) {
            return View.VISIBLE;
        }
        return View.INVISIBLE;
    }
}


