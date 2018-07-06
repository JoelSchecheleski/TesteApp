package com.example.paulo.teste.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.paulo.teste.model.Cliente;

import java.util.ArrayList;

public class ClienteDAO extends SQLiteOpenHelper {

    private static final String nome_banco = "Banco.db";
    private static final int version = 1;
    private static final String tabela = "tbcliente";

    private static final String id = "id";
    private static final String nome = "nome";
    private static final String endereco = "endereco";
    private static final String numero = "numero";
    private static final String uf = "uf";
    private static final String dataNasc = "dataNasc";
    private static final String telefone = "telefone";
    private static final String sexo = "sexo";


    public ClienteDAO(Context context) {

        super(context, nome_banco, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+tabela+" ("+
                " "+id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " "+nome+" TEXT, "+
                " "+endereco+" TEXT, "+
                " "+numero+" INTEGER, "+
                " "+uf+" TEXT,"+
                " "+dataNasc+" TEXT,"+
                " "+telefone+" TEXT,"+
                " "+sexo+" INTEGER );";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+tabela;

        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);


    }

    public long cadastrarCliente(Cliente c){
        ContentValues values = new ContentValues();
        long retorno;

        values.put(nome, c.getNome());
        values.put(endereco, c.getEndereco());
        values.put(numero, c.getNumero());
        values.put(uf, c.getuf());
        values.put(dataNasc, c.getDataNasc());
        values.put(telefone, c.getTelefone());
        values.put(sexo, c.getSexo());

        retorno = getWritableDatabase().insert(tabela,null, values);

        return retorno;
    }

    public long alterarCliente(Cliente c){
        ContentValues values = new ContentValues();
        long retorno;

        values.put(nome, c.getNome());
        values.put(endereco, c.getEndereco());
        values.put(numero, c.getNumero());
        values.put(uf, c.getuf());
        values.put(dataNasc, c.getDataNasc());
        values.put(telefone, c.getTelefone());
        values.put(sexo, c.getSexo());

        String[] args  = {String.valueOf(c.getId())};
        retorno = getWritableDatabase().update(tabela, values, "id=?", args);

        return retorno;
    }

    public long excluirCliente(Cliente c){
        ContentValues values = new ContentValues();
        long retorno;

        String[] args  = {String.valueOf(c.getId())};
        retorno = getWritableDatabase().delete(tabela, "id=?", args);

        return retorno;
    }

    public ArrayList<Cliente> listaClientes(){
        String[] colunas = {id, nome, endereco, numero, uf, dataNasc, telefone, sexo};
        Cursor cursor = getWritableDatabase().query(tabela, colunas, null, null, null, null, nome, null);

        ArrayList<Cliente> listClientes = new ArrayList<Cliente>();

        while (cursor.moveToNext()){
          Cliente c = new Cliente();
          c.setId(cursor.getInt(0));
          c.setNome(cursor.getString(1));
          c.setEndereco(cursor.getString(2));
          c.setNumero(cursor.getInt(3));
          c.setUF(cursor.getString(4));
          c.setDataNasc(cursor.getString(5));
          c.setTelefone(cursor.getString(6));
          c.setSexo(cursor.getInt(7));

          listClientes.add(c);

        }
        return listClientes;
    }
}
