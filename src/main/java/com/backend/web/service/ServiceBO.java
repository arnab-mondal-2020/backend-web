package com.backend.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.web.exception.BackendWebException;
import com.backend.web.teo.AdminUser;

@Component
public class ServiceBO extends ServiceTemplate {
  @Value("${session.svc.url}")
  private String sessionServiceURL;

  public AdminUser getUserInfoByNumber(String userNumber) throws BackendWebException {
    AdminUser adminUser = new AdminUser();
    adminUser.setUserNumber(userNumber);
    return invokePost(adminUser, AdminUser.class, sessionServiceURL, "getuserbynumber");
  }


}
