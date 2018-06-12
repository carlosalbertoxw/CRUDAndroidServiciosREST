# CRUDAndroidServiciosREST
Sencillo Ejemplo de comunicación entre una aplicación android y un servicio REST

Como primer paso se agregan las siguientes lineas a las dependencias del proyecto

    implementation 'org.springframework.android:spring-android-rest-template:2.0.0.M3'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.3.2'

En segundo paso se agrega el permiso de acceso a internet

    <uses-permission android:name="android.permission.INTERNET" />

En el tercer paso ya se construye la estructura de paquetes y clases que se muestran en el poyecto
