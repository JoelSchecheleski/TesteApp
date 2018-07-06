package com.example.paulo.teste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.paulo.teste.dao.ClienteDAO;
import com.example.paulo.teste.model.Cliente;

public class FormCliente extends AppCompatActivity {
    EditText editTNome, editTEndereco, editTnumero, editTUf, editTNasc, editTelefone;
    Button salvar;
    RadioButton Masc, Fem;
    Cliente cliente, altcliente;
    ClienteDAO clienteDAO;
    long retorno;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);

        Intent intent = getIntent();
        altcliente = (Cliente) intent.getSerializableExtra("cliente-enviado");
        cliente = new Cliente();
        clienteDAO = new ClienteDAO(FormCliente.this);

        editTNome = (EditText) findViewById(R.id.editText_Nome);
        editTEndereco = (EditText) findViewById(R.id.editText_Endereco);
        editTnumero = (EditText) findViewById(R.id.editText_numero);
        editTUf = (EditText) findViewById(R.id.editText_Uf);
        editTNasc = (EditText) findViewById(R.id.editText_Nasc);
        editTelefone = (EditText) findViewById(R.id.editText_Telefone);

        salvar = (Button) findViewById(R.id.button_Salvar);

        Masc = (RadioButton) findViewById(R.id.radioButton_Masc);
        Fem = (RadioButton) findViewById(R.id.radioButton_Fem);


        if(altcliente != null){
            salvar.setText("Alterar");

            editTNome.setText(altcliente.getNome());
            editTEndereco.setText(altcliente.getEndereco());
            editTnumero.setText(String.valueOf(altcliente.getNumero()));
            editTUf.setText(altcliente.getuf());
            editTNasc.setText(altcliente.getDataNasc());
            editTelefone.setText(altcliente.getTelefone());

            if(altcliente.getSexo() == 1){
             Masc.setChecked(true);
             Fem.setChecked(false);
            }else if(altcliente.getSexo() == 2){
             Fem.setChecked(true);
             Masc.setChecked(false);
            }

            cliente.setId(altcliente.getId());

        }else{
            salvar.setText("Salvar");
        }


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cliente.setNome(editTNome.getText().toString());
                cliente.setEndereco(editTEndereco.getText().toString());
                cliente.setNumero(Integer.parseInt(editTnumero.getText().toString()));
                cliente.setUF(editTUf.getText().toString());
                cliente.setDataNasc(editTNasc.getText().toString());
                cliente.setTelefone(editTelefone.getText().toString());

                    int x = 0;
                    if(Masc.isChecked()){
                        x = 1;
                    }else if(Fem.isChecked()){
                        x = 2;
                    }
                cliente.setSexo(x);

                if(salvar.getText().toString().equals("Salvar")){
                    retorno = clienteDAO.cadastrarCliente(cliente);
                    clienteDAO.close();
                    if (retorno == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Salvo com Sucesso");
                    }
                }else{
                    retorno = clienteDAO.alterarCliente(cliente);
                    clienteDAO.close();
                    if (retorno != -1){
                        alert("Sul é meu País");
                    }
                }
                finish();
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
