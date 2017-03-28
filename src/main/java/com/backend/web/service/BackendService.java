package com.backend.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.backend.web.constants.CommonConstants;
import com.backend.web.exception.BackendWebException;
import com.backend.web.teo.AdminUser;
import com.backend.web.teo.Degree;
import com.backend.web.teo.DegreeList;

@Component
public class BackendService extends ServiceTemplate implements CommonConstants {
  @Value("${backend.svc.url}")
  private String backendServiceURL;

  @Value("${session.svc.url}")
  private String sessionServiceURL;

  public DegreeList getAllDegrees(DegreeList degreeList) throws BackendWebException {
    return invokePost(degreeList, DegreeList.class, backendServiceURL, "alldeg");
  }

  public AdminUser getUserInfoByNumber(String userNumber) throws BackendWebException {
    AdminUser adminUser = new AdminUser();
    adminUser.setUserNumber(userNumber);
    return invokePost(adminUser, AdminUser.class, sessionServiceURL, "getuserbynumber");
  }

  public List<Degree> getAllDegrees() throws BackendWebException {
    DegreeList degreeList = new DegreeList();
    degreeList.setPageNoStr("1");
    degreeList.setRecordCountStr("1000");

    return getAllDegrees(degreeList).getDegrees();
  }

}
