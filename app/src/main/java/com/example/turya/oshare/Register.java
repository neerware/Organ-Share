package com.example.turya.oshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Register extends AppCompatActivity {
    private EditText name, address, sex, password, confirm;
    Button register;
    String Name, Address, Sex, Password, Confirm;

    //private AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.Address);
        sex = (EditText) findViewById(R.id.sex);
        password = (EditText) findViewById(R.id.Password);
        confirm = (EditText) findViewById(R.id.confirm);


    }

    public void save(View arg0) {
        Name = name.getText().toString();
        Address = address.getText().toString();
        Sex = sex.getText().toString();
        Password = password.getText().toString();
        Confirm = confirm.getText().toString();

        new donorregister().execute(Name, Address, Sex, Password, Confirm);

    }

    private class donorregister extends AsyncTask<String, Void, String> {
        String line=null;
        ProgressDialog Pdiag = new ProgressDialog(Register.this);

        protected void onPreExecute() {
            super.onPreExecute();
            Pdiag.setMessage("\tPlease...wait");
            Pdiag.setCancelable(false);
            Pdiag.show();
        }

        @Override
        protected String doInBackground(String... param) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://192.168.43.246/organshare/register.php");
                Uri.Builder build = new Uri.Builder()
                        .appendQueryParameter("NAME", param[0])
                        .appendQueryParameter("ADDRESS", param[1])
                        .appendQueryParameter("SEX", param[2])
                        .appendQueryParameter("PASSWORD", param[3])
                        .appendQueryParameter("CONFIRM", param[4]);
                String data = build.build().getEncodedQuery();

                //SEND POST DATA REQUEST
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                //WRITING DATA TO THE SERVER
                OutputStreamWriter oswriter = new OutputStreamWriter(conn.getOutputStream());
                oswriter.write(data);
                oswriter.flush();

                //writing back to the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                //String result;
                while((line=reader.readLine())!=null){
                    result.append(line);

                }



            } catch (Exception e) {
                e.printStackTrace();
            }


return result.toString();
        }
        @Override
        public void onPostExecute(String result){
            super.onPostExecute(result);

            Pdiag.dismiss();
        }
    }
}