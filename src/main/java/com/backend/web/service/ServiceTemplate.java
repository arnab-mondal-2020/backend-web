package com.backend.web.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.backend.web.exception.BackendWebException;
import com.backend.web.util.JSONException;
import com.backend.web.util.JSONHelper;

public abstract class ServiceTemplate {
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APP_JSON = "Application/JSON";

  public <T, E> T invokePost(E in, Class<T> returnType, String baseUrl, String uri)
      throws BackendWebException {
    try {
      HttpURLConnection connection = getConnection(baseUrl, uri);
      String input = JSONHelper.objectToJSON(in);
      OutputStream os = connection.getOutputStream();
      os.write(input.getBytes());
      os.flush();
      BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
      String response = br.readLine();
      System.out.println("Raw response - " + response);
      T info = JSONHelper.jsonToObject(response, returnType);
      return info;
    } catch (MalformedURLException e) {
      throw new BackendWebException(e.getMessage());
    } catch (IOException e) {
      throw new BackendWebException(e.getMessage());
    } catch (JSONException e) {
      throw new BackendWebException(e.getMessage());
    }
  }

  private HttpURLConnection getConnection(String restURL, String uri) throws IOException {
    URL serviceUrl = new URL(restURL + uri);
    HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty(CONTENT_TYPE, APP_JSON);
    return connection;
  }
}
