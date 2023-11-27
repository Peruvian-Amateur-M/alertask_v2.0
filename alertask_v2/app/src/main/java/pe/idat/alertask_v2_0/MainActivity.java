package pe.idat.alertask_v2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    Button btnListar, btnRegistrar, btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        context = getApplicationContext();
        btnRegistrar = findViewById(R.id.btnregistrar);
        btnBuscar = findViewById(R.id.btnbuscar);
        btnListar = findViewById(R.id.btnlistar);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnregistrar ){

            Toast.makeText(context, "Registrar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, GestionarTareaActivity.class);

            Bundle bolsa = new Bundle();
            bolsa.putInt("id", 0);
            intent.putExtras(bolsa);

            startActivity(intent);
        } else if (view.getId() == R.id.btnbuscar) {
            Intent intent = new Intent(context, BuscarTareaActivity.class);
            startActivity(intent);
            Toast.makeText(context, "Buscar", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnlistar) {
            Intent intent = new Intent(context, ListadoTareasActivity.class);
            startActivity(intent);
            Toast.makeText(context, "Listar", Toast.LENGTH_SHORT).show();
        }
    }
}