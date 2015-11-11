package com.example.andresp.show_url_type;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Get_URL_show_type_Activity extends AppCompatActivity {

    private TextView finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__url_show_type_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Button butt = (Button) findViewById(R.id.button);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finalResult = (TextView) findViewById(R.id.type_result_text);
                final String url_text = ((TextView) findViewById(R.id.url_editText)).getText().toString();
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(url_text);

//                Snackbar.make(view, URLContentType(url_text), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get__url_show_type_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by andresp on 10/11/2015.
     *
     * Private class which runs the long operation.
     */
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String ret="unknown";
        private URLConnection con;

        @Override
        protected String doInBackground(String... params) {



            publishProgress("Make URL..."); // Calls onProgressUpdate()

            // Do your long operations here and return the result
            URL url= null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            } catch (Exception e){
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            }
            publishProgress("Connecting..."); // Calls onProgressUpdate()
            try {
                con = url.openConnection();

            } catch (IOException e) {
                Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            } catch (Exception e){
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            }

            publishProgress("Get content type..."); // Calls onProgressUpdate()

            try {
                ret = con.getContentType();
            } catch (Exception e){
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            }
            ret="tipo: ".concat(ret);

            publishProgress("Get content length..."); // Calls onProgressUpdate()

            long long_date;
            try {

                long_date = con.getDate();

            } catch (Exception e){
                e.printStackTrace();
                ret = e.getMessage();
                return ret;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");


            ret = ret.concat("\n");
            ret = ret.concat(dateFormat.format(long_date));
            //ret = ret.concat(" ");

            publishProgress("Exiting..."); // Calls onProgressUpdate()

            if (ret == null) ret="error en URL";

            return ret;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            finalResult.setText(result);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }


}
