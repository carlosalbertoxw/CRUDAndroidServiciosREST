package com.carlosalbertoxw.crud_android_servicios_rest.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carlosalbertoxw.crud_android_servicios_rest.R;
import com.carlosalbertoxw.crud_android_servicios_rest.adapters.NoteListAdapter;
import com.carlosalbertoxw.crud_android_servicios_rest.models.Note;
import com.carlosalbertoxw.crud_android_servicios_rest.services.NoteRESTService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class NoteListActivity extends AppCompatActivity {

    private List<Note> list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        new HttpRequest().execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        new HttpRequest().execute();
    }

    private class HttpRequest extends AsyncTask<Void,Void,Map<String,Object>> {

        @Override
        protected Map<String,Object> doInBackground(Void... voids) {
            NoteRESTService service = new NoteRESTService();
            return service.list();
        }

        @Override
        protected void onPostExecute(Map<String,Object> map) {
            if(map!=null){
                if(map.get("message").equals("OK")&&map.get("status").equals(true)){
                    list = new ObjectMapper().convertValue(map.get("result"), new TypeReference<List<Note>>() {});
                    listView = (ListView) findViewById(R.id.lstNotes);
                    listView.setAdapter(new NoteListAdapter(getApplicationContext(),list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(NoteListActivity.this,NoteFormActivity.class);
                            intent.putExtra("id",list.get(position).getId());
                            intent.putExtra("titulo",list.get(position).getTitle());
                            intent.putExtra("texto",list.get(position).getText());
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    Toast.makeText(NoteListActivity.this, map.get("message").toString(), Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(NoteListActivity.this, "Error al comunicarse con el servicio!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
