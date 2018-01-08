package myapps.jherrera.nezter.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import myapps.jherrera.nezter.database.DbProductHelper;
import myapps.jherrera.nezter.objects.Product;
import myapps.jherrera.nezter.objects.RefreshCallback;

public class ProductController {

    private final int STOCK_DEFAULT = 1;

    private Context context;
    private DbProductHelper dbHelper;
    private Product product;

    public ProductController(Context context){
        this.context = context;
        dbHelper = new DbProductHelper(context);
    }

    public void addProduct(String name, String stock){

        if (noBlankName(name)){
            int intStock = convertStockToInt(stock);
            product = new Product(name,intStock);
            saveProductOnBd();
        }
    }

    private boolean noBlankName(String name){

        if (name.length() == 0){
            showDialog("El nombre esta en blanco");
            return false;
        }else {
            return true;
        }
    }

    private int convertStockToInt(String stock){

        if (Objects.equals(stock, "")){
            return STOCK_DEFAULT;
        }else {
           return Integer.parseInt(stock);
        }
    }

    private void showDialog(String dialog){
        Toast.makeText(context, dialog, Toast.LENGTH_LONG).show();
    }

    private   void saveProductOnBd(){

        try {
            long result = dbHelper.addProduct(product.getName(), product.getStock());

            if (result > 0) {
                showDialog("Registro exitoso");
            }
        } catch (Exception e) {
            showDialog("Se produjo un error al agregar producto");
            e.printStackTrace();
        }
    }

    private void deleteProduct(String id){
     dbHelper.deleteProduct(id);

    }

    public void switchActiveInactive(String id){
         dbHelper.updateActive(id);
    }

    public List getAllProducts(){
        return dbHelper.selectAll();
    }

    public void alertDialog(String name, final int ID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Eliminar ");
        builder1.setMessage("¿Desea eliminar " + name + "?");
        builder1.setCancelable(true);
        builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder1.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteProduct(String.valueOf(ID));
                        showAllProducts();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showAllProducts(){
        RefreshCallback refresh = (RefreshCallback) context;
        refresh.refresh();
    }

    public void incrementStock(int id){
        dbHelper.increment(String.valueOf(id));
        showAllProducts();
    }

    public void decrementStock(int id){
        dbHelper.drecement(String.valueOf(id));
        showAllProducts();
    }
}
