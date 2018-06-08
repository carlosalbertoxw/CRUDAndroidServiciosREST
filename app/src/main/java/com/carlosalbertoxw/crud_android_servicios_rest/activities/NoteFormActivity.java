package com.carlosalbertoxw.crud_android_servicios_rest.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carlosalbertoxw.crud_android_servicios_rest.R;
import com.carlosalbertoxw.crud_android_servicios_rest.models.Note;
import com.carlosalbertoxw.crud_android_servicios_rest.services.NoteRESTService;

import java.util.Map;

public class NoteFormActivity extends AppCompatActivity {

    private EditText txtTitle, txtText;
    private Button btnSave, btnDelete;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        intent = getIntent();

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtText = (EditText) findViewById(R.id.txtText);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        if(intent.getLongExtra("id",0)!=0){
            txtTitle.setText(intent.getStringExtra("titulo"));
            txtText.setText(intent.getStringExtra("texto"));
            btnSave.setVisibility(View.VISIBLE);
        }else{
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTitle.getText().toString().length()>0 && txtText.getText().toString().length()>0){
                    Note item = new Note();
                    item.setTitle(txtTitle.getText().toString());
                    item.setText(txtText.getText().toString());
                    item.setId(intent.getLongExtra("id",0));
                    new HttpRequest(item,1).execute();
                }else{
                    Toast.makeText(NoteFormActivity.this, "Llena todos los campos para poder continuar!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note item = new Note();
                item.setId(intent.getLongExtra("id",0));
                new HttpRequest(item,2).execute();
            }
        });
    }

    private void limpiarCampos(){
        txtTitle.setText("");
        txtText.setText("");
    }

    private class HttpRequest extends AsyncTask<Void,Void,Map<String,Object>> {

        private Note item;
        private int i;

        public HttpRequest(Note item,int i){
            this.item = item;
            this.i = i;
        }

        @Override
        protected Map<String,Object> doInBackground(Void... voids) {
            NoteRESTService service = new NoteRESTService();
            if(this.i==1){
                if (item.getId()==0){
                    return service.save(item);
                }else {
                    return service.update(item);
                }
            }else{
                return service.delete(item);
            }
        }

        @Override
        protected void onPostExecute(Map<String,Object> map) {
            if(map!=null) {
                if (map.get("status").equals(true)) {
                    Toast.makeText(NoteFormActivity.this, map.get("result").toString(), Toast.LENGTH_LONG).show();
                    limpiarCampos();
                }else{
                    Toast.makeText(NoteFormActivity.this, map.get("result").toString(), Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(NoteFormActivity.this, "Error al comunicarse con el servicio!!", Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(NoteFormActivity.this, NoteListActivity.class);
            startActivity(i);
            finish();
        }
    }
}
