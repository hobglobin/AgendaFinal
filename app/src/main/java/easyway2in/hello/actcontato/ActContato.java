package easyway2in.hello.actcontato;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import easyway2in.hello.actcontato.app.MessageBox;
import easyway2in.hello.actcontato.database.Database;
import easyway2in.hello.actcontato.dominio.RepositorioContato;
import easyway2in.hello.actcontato.dominio.entidades.Contato;

public class ActContato extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton btnAdicionar;
    private EditText edtPesquisa;
    private ListView lstContatos;
    private Database objDatabase;
    private SQLiteDatabase objCon;
    private ArrayAdapter<Contato> adpContatos;
    private RepositorioContato repositorioContato;
    private FiltraDados filtraDados;

    private SearchView searchView;
    private MenuItem menuItem;

    public static final String PAR_CONTATO = "CONTATO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_contato);


        btnAdicionar = (ImageButton)findViewById(R.id.btnAdicionar);
        edtPesquisa = (EditText)findViewById(R.id.edtPesquisa);
        lstContatos = (ListView)findViewById((R.id.lstContatos));

        btnAdicionar.setOnClickListener(this);
        lstContatos.setOnItemClickListener(this);

        try
        {
        objDatabase = new Database(this);
        objCon = objDatabase.getWritableDatabase();

        repositorioContato = new RepositorioContato(objCon);
        adpContatos = repositorioContato.buscaContatos(this);

        lstContatos.setAdapter(adpContatos);
        filtraDados = new FiltraDados(adpContatos);
        edtPesquisa.addTextChangedListener(filtraDados);

        }
        catch (SQLException ex) {
        MessageBox.show(this, "Erro", "Erro ao gravar os dados: " + ex.getMessage());
        }
        }

@Override
protected void onDestroy() {
        super.onDestroy();
        if (objCon != null)
        objCon.close();
        }

@Override
protected void onStop() {
        Log.d("Teste", "onStop");
        if (menuItem != null && searchView != null) {
        if (menuItem.isVisible()) {
        searchView.setQuery("", false);
        Log.d("Teste", "CollapseMenuItem");
        }
        }

        super.onStop();
        }

@Override
protected void onStart() {
        Log.d("Teste", "onStart");
        super.onStart();
        }

@Override
protected void onResume() {
        Log.d("Teste", "onResume");
        super.onResume();
        }

@Override
protected void onPause() {
        Log.d("Teste", "onPause");
        super.onPause();
        }

@Override
public void onClick(View v) {
        Intent it = new Intent(this, ActCadContatos.class);
        startActivityForResult(it, 0);
        }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        adpContatos = repositorioContato.buscaContatos(this);
        filtraDados.setArrayAdapter(adpContatos);
        lstContatos.setAdapter(adpContatos);
        }

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Contato contato =   adpContatos.getItem(position);
        Intent it = new Intent(this, ActCadContatos.class);
        it.putExtra(PAR_CONTATO, contato);
        startActivityForResult(it, 0);

        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_contato, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menuItem = (MenuItem)menu.findItem(R.id.search);
        menuItem.getActionView();
        searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        }

private class FiltraDados implements TextWatcher {

    private ArrayAdapter<Contato> arrayAdapter;

    private FiltraDados(ArrayAdapter<Contato> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<Contato> arrayAdapter)
    {
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        arrayAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    }
}
