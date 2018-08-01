package com.driftman.fuckingandroid.log;

import com.driftman.fuckingandroid.log.db.dao.LogDAO;
import com.driftman.fuckingandroid.log.entity.Log;
import com.driftman.fuckingandroid.log.network.LogAPI;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by abk on 30/07/2018.
 */

public class LogWorker {

    public static final String TAG = "LogWorker";

    private BlockingQueue<Log> queue = new LinkedBlockingQueue<>();

    private LogAPI logAPI;
    private LogDAO logDAO;

    public LogWorker(LogDAO logDAO, LogAPI logAPI) {

        this.logAPI = logAPI;
        this.logDAO = logDAO;

        // Starting the looper thread
        Appender appender = new Appender();
        Thread thread = new Thread(appender);

        thread.start();
    }

    public void addToQueue(Log log) {
        queue.add(log);
    }

    private class Appender implements Runnable {

        final long SECOND_IN_MILLISECONDS = 1000;
        final long FIVE_SECONDS = 5 * SECOND_IN_MILLISECONDS;

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        public void run() {
            long lastSend = System.currentTimeMillis();
            while (true) {
                Log log = queue.poll();
                if (log != null) {
                    logDAO.create(log);
                }
                long sinceLastSend = System.currentTimeMillis() - lastSend;
                if (sinceLastSend >= FIVE_SECONDS) {
                    try {
                        List<Log> logs = logDAO.read();
                        if (!logs.isEmpty()) {

                            String bulk = getLogsNaive(logs);

                            RequestBody body = RequestBody.create(JSON, bulk);

                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:9200/_bulk")
                                    .addHeader("Content-Type", "application/x-ndjson")
                                    .post(body)
                                    .build();

                            okhttp3.Response response = okHttpClient.newCall(request).execute();

                            if (response.isSuccessful()) {
                                android.util.Log.d(TAG, "response -> successful.");
                                android.util.Log.d(TAG, "now clearing the local db.");
                                logDAO.delete();
                            } else {
                                String error = response.body().string();
                                android.util.Log.d(TAG, "response -> not successful.");
                            }
                        }
                    } catch (Exception e) {
                        android.util.Log.e(TAG, e.getMessage());
                    }
                }
            }
        }

        private void writeWithNewLine(BufferedWriter writer, String jsonString) {
            try {
                writer.write(jsonString);
                writer.newLine();
            } catch (IOException e) {
                android.util.Log.e(TAG, "Error while writing the object to stream", e);
            }
        }

        public void generate(List<JSONObject> ndJsonList, OutputStream outputStream) throws IOException {

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            for (JSONObject o : ndJsonList) {
                String jsonString = o.toString();
                jsonString = jsonString.replace("\n", "").replace("\r", "").trim();
                writeWithNewLine(bufferedWriter, jsonString);
            }
            bufferedWriter.flush();
        }

        private String getLogsNaive(List<Log> logs) {
            StringBuilder output = new StringBuilder();
            List<JSONObject> jsonObjects = new ArrayList<>();
            JSONObject oo = null;
            JSONObject o = null;
            try {
                // Creating ES metadata objects
                o = new JSONObject();
                o.put("_index", "profacteo");
                o.put("_type", "log");
                oo = new JSONObject();
                oo.put("index", o);

                for (Log log: logs) {
                    // Adding metadata before log entry
                    output.append(oo.toString());
                    output.append("\n");
                    // Creating log json object
                    JSONObject jsonLog = new JSONObject();
                    jsonLog.put("message", log.getMessage());
                    jsonLog.put("type", log.getType());
                    // Adding json log entry
                    output.append(jsonLog.toString());
                    output.append("\n");
                }

            } catch (JSONException e) {
                return null;
            }
            return output.toString();
        }

        private String getLogs(List<Log> logs) {
            List<JSONObject> jsonObjects = new ArrayList<>();
            JSONObject oo = null;
            JSONObject o = null;
            try {
                o = new JSONObject();
                o.put("_index", "profacteo");
                o.put("_type", "log");
                oo = new JSONObject();
                oo.put("index", o);
            } catch (JSONException e) {
                return null;
            }

            for (Log log : logs) {
                // index
                jsonObjects.add(oo);
                try {
                    JSONObject jsonLog = new JSONObject();
                    jsonLog.put("message", log.getMessage());
                    jsonLog.put("type", log.getType());
                    jsonObjects.add(jsonLog);
                } catch (JSONException e) {
                    return null;
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                generate(jsonObjects, outputStream);
            } catch (IOException e) {
                return null;
            }
            return outputStream.toString();
        }
    }

}
