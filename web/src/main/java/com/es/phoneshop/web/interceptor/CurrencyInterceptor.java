package com.es.phoneshop.web.interceptor;

import com.es.core.utils.PriceFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrencyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PriceFormatter priceFormatter;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("currencySymbol", priceFormatter.getDefaultCurrency());
    }
}
