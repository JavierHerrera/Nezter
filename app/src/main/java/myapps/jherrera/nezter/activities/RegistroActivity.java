package myapps.jherrera.nezter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import myapps.jherrera.nezter.R;
import myapps.jherrera.nezter.objects.User;
import myapps.jherrera.nezter.database.DbUserHelper;


public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etConfimrPassword;
    Button btnRegister;
    DbUserHelper usuariosHelper = new DbUserHelper(this);
    String name,password, confirmPassword;
    User usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registro");
        setContentView(R.layout.activity_registro);

        name = "";
        password = "";
        confirmPassword = "";

        etName = findViewById(R.id.et_nombre_usuario_registro);
        etPassword = findViewById(R.id.et_password_usuario_registro);
        etConfimrPassword = findViewById(R.id.et_confirm_password_registro);
        btnRegister = findViewById(R.id.btn_registrar);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

             case R.id.btn_registrar:
                 name = String.valueOf(etName.getText());
                 password = String.valueOf(etPassword.getText());
                 confirmPassword = String.valueOf(etConfimrPassword.getText());

                 try {
                     if (validName() && validPassword()) {
                         setUser();
                         Long result = usuariosHelper.addUser(usuario);

                         if (result >0 ) {
                             Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT ).show();
                             Intent intent = new Intent(this, MainActivity.class);
                             startActivity(intent);
                         }
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                     Toast.makeText(this,"Exception error "+e.toString(),Toast.LENGTH_LONG ).show();
                 }


                 break;
        }

    }

    private boolean validName(){
        boolean valid = name.length() > 0;
        if (!valid){
            Toast.makeText(this,"EL nombre esta en blanco",Toast.LENGTH_LONG ).show();
        }
        return valid ;
    }

    private boolean validPassword(){

        if (!Objects.equals(password, confirmPassword)){
            Toast.makeText(this,"Password no coincide",Toast.LENGTH_LONG ).show();
        }
        else if(password.length() == 0){
            Toast.makeText(this,"Password esta en blanco",Toast.LENGTH_LONG ).show();

        }else {
            return true;
        }
        return  false ;
    }

    private void setUser(){
        usuario = new User();
        usuario.setName(name);
        usuario.setPassword(password);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
