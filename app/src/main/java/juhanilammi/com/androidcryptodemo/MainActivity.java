package juhanilammi.com.androidcryptodemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SimpleDataModel simpleDataModel;
    private AlertDialog loadingDialog;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleDataModel = new SimpleDataModel();
        final TextView outputView = (TextView) findViewById(R.id.output_textview);
        final Button showEncryptionDetails = (Button) findViewById(R.id.show_encryption_details);
        showEncryptionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEncryptionDetails();
            }
        });
        Button showEncrypted = (Button) findViewById(R.id.show_encrypted_string);
        showEncrypted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputView.setText(simpleDataModel.getEncryptedString());
            }
        });
        Button showDecrypted = (Button) findViewById(R.id.show_decrypted_string);
        showDecrypted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputView.setText(simpleDataModel.decryptString());
            }
        });
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        final EditText inputFirst = new EditText(this);
        dialog1.setView(inputFirst);
        dialog1.setTitle("Set a password");
        dialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingDialog = createLoadingDialog();
                loadingDialog.show();
                dialog.dismiss();
                time = Calendar.getInstance().getTimeInMillis();
                new KeyGenerationTask().execute((inputFirst.getText().toString()));
            }
        });
        dialog1.show();
    }

    private AlertDialog createLoadingDialog() {
        AlertDialog.Builder loadingDialog = new AlertDialog.Builder(this);
        loadingDialog.setTitle("Generating key...");
        loadingDialog.setCancelable(false);
        ProgressBar bar = new ProgressBar(this);
        loadingDialog.setView(bar);
        return loadingDialog.create();
    }

    private void showEncryptionDetails() {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        final TextView details = new TextView(this);
        dialog1.setView(details);
        dialog1.setTitle("Details");
        details.setText(simpleDataModel.getCryptoDetails()+"\r\nKey generated in "+time+" seconds");
        dialog1.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog1.show();
    }

    private void showInputStringDialog() {
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
        final EditText inputSecond = new EditText(this);
        dialog1.setView(inputSecond);
        dialog1.setTitle("Save a String");
        dialog1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simpleDataModel.saveStringAndEncrypt(inputSecond.getText().toString());
                dialog.dismiss();
            }
        });
        dialog1.show();
    }

    public void loadFinished(Boolean aBoolean) {
        loadingDialog.dismiss();
        time = (Calendar.getInstance().getTimeInMillis()-time)/1000;
        showInputStringDialog();
    }
    /**
     * A simple task for loading...
     */
    private class KeyGenerationTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Log.d("AAA", "doInBackground: "+params[0]);
            boolean result = simpleDataModel.insertPassword(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d("TASK", "onPostExecute: ");
            loadFinished(aBoolean);
        }
    }


}
