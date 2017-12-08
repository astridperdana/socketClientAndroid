package com.example.a5213100176.socketclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 5213100176 on 07/12/2017.
 */

public class Client extends AsyncTask<Void, Void, Void> {
    String dstAddress, usrChoice, dstPath;
    int dstPort;
    String response = "";
    TextView textResponse;

    Client(String addr, int port, String choice, String path, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        usrChoice = choice;
        dstPath = path;
        this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... args0) {
        while(true) {
            try {
                Socket client = new Socket(dstAddress, dstPort);
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);

                out.writeUTF("Connected client : " + client.getLocalSocketAddress());
                out.writeUTF(usrChoice);
                if (usrChoice.equals("0")) {
                    response = in.readUTF();
                } else if (usrChoice.equals("1")) {
                    out.writeUTF(dstPath);
                    response = in.readUTF();
                } else if (usrChoice.equals("2")) {
                    out.writeUTF(dstPath);
                    response = in.readUTF();
                } else if (usrChoice.equals("3")){
                    out.writeUTF(dstPath);
                    response = in.readUTF();
                } else {
                    response = in.readUTF();
                }

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IO Exception" + e.toString();
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }
}
