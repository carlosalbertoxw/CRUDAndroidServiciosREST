package com.carlosalbertoxw.crud_android_servicios_rest.services;

import android.util.Log;

import com.carlosalbertoxw.crud_android_servicios_rest.models.Note;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class NoteRESTService {
    private String BASE_URL = "http://10.0.2.2:8080/CRUD-Servicios-REST-Java-Spring/";
    private RestTemplate restTemplate;

    public NoteRESTService(){
        restTemplate = new RestTemplate();
    }

    public Map<String,Object> save(Note item){
        try {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authentication","qwerty");
            HttpEntity entity = new HttpEntity(item,headers);
            return restTemplate.exchange(BASE_URL+"api/notes", HttpMethod.POST, entity, Map.class).getBody();
        }catch (Exception e){
            Log.e("NoteRESTService.save", e.getMessage());
            return null;
        }
    }

    public Map<String,Object> list(){
        try{
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authentication","qwerty");
            HttpEntity entity = new HttpEntity(headers);
            return restTemplate.exchange(BASE_URL+"api/notes", HttpMethod.GET, entity, Map.class).getBody();
        }catch (Exception e){
            Log.e("NoteRESTService.list", e.getMessage());
            return null;
        }
    }

    public Map<String,Object> update(Note item){
        try {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authentication","qwerty");
            HttpEntity entity = new HttpEntity(item,headers);
            return restTemplate.exchange(BASE_URL+"api/notes/"+item.getId(), HttpMethod.PUT, entity, Map.class).getBody();
        }catch (Exception e){
            Log.e("NoteRESTService.update", e.getMessage());
            return null;
        }
    }

    public Map<String,Object> delete(Note item){
        try {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authentication","qwerty");
            HttpEntity entity = new HttpEntity(headers);
            return restTemplate.exchange(BASE_URL+"api/notes/"+item.getId(), HttpMethod.DELETE, entity, Map.class).getBody();
        }catch (Exception e){
            Log.e("NoteRESTService.delete", e.getMessage());
            return null;
        }
    }
}
