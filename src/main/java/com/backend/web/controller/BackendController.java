package com.backend.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.backend.web.constants.BackendConstants;
import com.backend.web.constants.RequestMappings;
import com.backend.web.exception.BackendWebException;
import com.backend.web.service.BackendService;
import com.backend.web.teo.AdminUser;
import com.backend.web.teo.Degree;
import com.backend.web.teo.DegreeList;
import com.backend.web.util.CommonUtil;


@Controller
public class BackendController implements RequestMappings, BackendConstants {
  @Autowired
  private BackendService service;

  @RequestMapping(value = REQUEST_LOAD_BACK_END, method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView loadPage(HttpServletRequest request) throws BackendWebException {
    String userNumber = (String) request.getAttribute(USER_NUMBER);
    userNumber = userNumber == null ? "00000005" : userNumber;
    AdminUser user = null;
    ModelAndView mav = new ModelAndView(RETURN_BACK_END);
    try {
      user = service.getUserInfoByNumber(userNumber);
      request.setAttribute(USER_INFO, user);
      request.setAttribute(FIRST_NAME_PROP, user.getFullName());
      CommonUtil.setWelcomeMessage(request);

      if (!user.getAuthLevel().equalsIgnoreCase("SU")) {
        return new ModelAndView("forward:/unauthorized");
      }
      List<Degree> degrees;
      degrees = service.getAllDegrees();
      request.setAttribute(BackendConstants.ALL_DEGREES_OPT, degrees);
    } catch (BackendWebException e) {
      System.err.println(e.getMessage());
    }

    return mav;
  }

  @RequestMapping(value = REQUEST_SHOW_ALL_DEGREE, method = RequestMethod.GET)
  public ModelAndView showAllDegrees(HttpServletRequest request) throws BackendWebException {
    ModelAndView mav = new ModelAndView(REQUEST_LOAD_BACK_END_FWD);
    String pageNoStr = request.getParameter(PAGE_NO);
    String recordCountStr = request.getParameter(RECORD_PER_PAGE);
    DegreeList degreeList = new DegreeList();
    degreeList.setPageNoStr(pageNoStr);
    degreeList.setRecordCountStr(recordCountStr);

    degreeList = service.getAllDegrees(degreeList);
    List<Degree> degrees = degreeList.getDegrees();
    request.setAttribute(FROM_RECORD, degreeList.getFromReord() + "");
    request.setAttribute(TO_RECORD, degreeList.getToRecord() + "");
    request.setAttribute(TOTAL_RECORD, degreeList.getTotalRecord());

    request.setAttribute(RECORD_SET, degreeList.getRecordSet());
    request.setAttribute(RECORD_PER_PAGE, degreeList.getRecordPerPage() + "");
    request.setAttribute(PAGE_NO, degreeList.getPageNo() + "");

    request.setAttribute(BackendConstants.ALL_DEGREES, degrees);
    return mav;
  }

}
