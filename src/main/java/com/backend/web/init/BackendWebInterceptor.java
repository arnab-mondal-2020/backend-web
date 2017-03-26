package com.backend.web.init;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BackendWebInterceptor extends HandlerInterceptorAdapter {
  @Value("${backend.web.static-path}")
  private String staticFilePath;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    request.setAttribute("host", staticFilePath);
    System.out.println("Host - " + staticFilePath);
  }
}
