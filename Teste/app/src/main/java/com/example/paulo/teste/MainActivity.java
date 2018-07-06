package com.example.paulo.teste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paulo.teste.dao.ClienteDAO;
import com.example.paulo.teste.model.Cliente;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView lista;
    Button cadastrar;
    Cliente cliente;
    ClienteDAO clienteDAO;
    ArrayList<Cliente> arrayCliente;
    ArrayAdapter<Cliente> arrayAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.ListaClientes);
        registerForContextMenu(lista);

        cadastrar = (Button) findViewById(R.id.button_cadastrar_cliente);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, FormCliente.class);
                startActivity(it);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente clienteEnviado = (Cliente) arrayAdapterCliente.getItem(position);

                if (clienteEnviado.getuf().equalsIgnoreCase("sc") ||
                        clienteEnviado.getuf().equalsIgnoreCase("rs") ||
                        clienteEnviado.getuf().equalsIgnoreCase("pr")
                        ) {
                    Intent i = new Intent(MainActivity.this, FormCliente.class);
                    i.putExtra("cliente-enviado", clienteEnviado);
                    startActivity(i);
                } else {
                    alert("Você não é do SUL");
                }
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                cliente = arrayAdapterCliente.getItem(i);
                return false;
            }
        });

    }

    public void populaListaClientes() {
        clienteDAO = new ClienteDAO(MainActivity.this);
        arrayCliente = clienteDAO.listaClientes();
        clienteDAO.close();

        if (lista != null) {
            arrayAdapterCliente = new ArrayAdapter<Cliente>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayCliente);

            lista.setAdapter(arrayAdapterCliente);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        populaListaClientes();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Excluir Cliente");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
            long retorno;
            clienteDAO = new ClienteDAO(MainActivity.this);

            retorno = clienteDAO.excluirCliente(cliente);

            if(retorno != -1){
                alert("Excluido Com Sucesso");
            }
                populaListaClientes();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}


