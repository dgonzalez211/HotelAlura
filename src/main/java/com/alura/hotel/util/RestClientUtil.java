package com.alura.hotel.util;

import com.alura.hotel.exceptions.AuthenticationException;
import com.alura.hotel.session.SessionManagerFactory;
import javafx.concurrent.Task;
import okhttp3.*;

import java.io.IOException;
import java.util.logging.Logger;

public class RestClientUtil {

    private static final Logger logger = Logger.getLogger(RestClientUtil.class.getSimpleName());

    private static OkHttpClient client;

    public RestClientUtil() {
        client = new OkHttpClient();
    }

    public Task<String> get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .build();

        return createTask(request);
    }

    public Task<String> post(String url, String jsonBody) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .post(body)
                .build();

        return createTask(request);
    }

    public Task<String> put(String url, String jsonBody) {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .put(body)
                .build();

        return createTask(request);
    }

    public Task<String> delete(String url) {
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .delete()
                .build();

        return createTask(request);
    }

    private Headers getHeaders() {
        Headers.Builder headers = new Headers.Builder();
        headers.add("Content-Type", "application/json");
        try {
            String token = SessionManagerFactory.getInstance().getToken();
            headers.add("Authorization", "Bearer " + token);
            return headers.build();
        } catch (AuthenticationException exception) {
            logger.warning("An unauthenticated request is being sent: " + exception.getMessage());
        }
        return headers.build();
    }

    private Task<String> createTask(Request request) {
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        throw new IOException("Unexpected response code: " + response);
                    }
                }
            }
        };

        new Thread(task).start();
        return task;
    }

}
