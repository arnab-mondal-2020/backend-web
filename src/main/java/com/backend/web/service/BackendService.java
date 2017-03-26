package com.backend.web.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.web.constants.CommonConstants;
import com.backend.web.exception.BackendWebException;
import com.backend.web.teo.DegreeList;
import com.backend.web.util.JSONException;
import com.backend.web.util.JSONHelper;

@Component
public class BackendService implements CommonConstants {
  @Value("${backend.svc.url}")
  private String restURL;

  public DegreeList getAllDegrees(DegreeList degreeList) throws BackendWebException {
    try {
      HttpURLConnection connection = getConnection("alldeg");
      String input = JSONHelper.objectToJSON(degreeList);
      OutputStream os = connection.getOutputStream();
      os.write(input.getBytes());
      os.flush();
      BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
      String response = br.readLine();
      System.out.println("Raw response - " + response);
      DegreeList info = JSONHelper.jsonToObject(response, DegreeList.class);
      return info;
    } catch (MalformedURLException e) {
      throw new BackendWebException(e.getMessage());
    } catch (IOException e) {
      throw new BackendWebException(e.getMessage());
    } catch (JSONException e) {
      throw new BackendWebException(e.getMessage());
    }
  }

  private HttpURLConnection getConnection(String uri) throws IOException {
    URL serviceUrl = new URL(restURL + uri);
    HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty(CONTENT_TYPE, APP_JSON);
    return connection;
  }

}
