package myapps.jherrera.nezter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import myapps.jherrera.nezter.database.DbUsuariosHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword;
    Button btnLogin, btnRegister;
    DbUsuariosHelper usuariosHelper = new DbUsuariosHelper(this);
    String name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = "";
        password = "";

        etName = findViewById(R.id.et_nombre_usuario);
        etPassword = findViewById(R.id.et_password_usuario);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.btn_login:

                name = String.valueOf(etName.getText());
                password = String.valueOf(etPassword.getText());

                try {
                    if (usuariosHelper.checkUser(name,password)) {
                        Intent intent = new Intent(this, ConfiguracionActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this,"Usuario o password incorrecto",Toast.LENGTH_LONG ).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_register:

                    Intent intent = new Intent(this, RegistroActivity.class);
                    startActivity(intent);

                break;
        }
    }
}
