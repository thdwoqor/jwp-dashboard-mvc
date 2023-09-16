package com.techcourse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webmvc.org.springframework.web.servlet.HandlerAdapter;
import webmvc.org.springframework.web.servlet.ModelAndView;
import webmvc.org.springframework.web.servlet.mvc.asis.Controller;
import webmvc.org.springframework.web.servlet.view.JspView;

public class ManualHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(final Object handler) {
        if (handler instanceof Controller) {
            return true;
        }
        return false;
    }

    @Override
    public ModelAndView handle(final Object handler, final HttpServletRequest request,
                               final HttpServletResponse response) throws Exception {
        String execute = ((Controller) handler).execute(request, response);

        return new ModelAndView(new JspView(execute));
    }
}
