package com.konovalov.converter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Controller
public class ExceptionHandlerController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorTitle", "Ошибка");
        modelAndView.addObject("errorMsg", "Во время обработки запроса произошла ошибка: " + e.getMessage());
        logger.error("");
        return modelAndView;
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorTitle = "Ошибка";
        String errorMsg = "Во время обработки запроса произошла ошибка";
        if (status != null) {
            String code = status.toString();
            errorTitle = "Ошибка " + code;
            errorMsg = switch(code) {
                case "401" : yield "Необходима авторизация";
                case "403" : yield "Доступ запрещен";
                case "404" : yield "Страница не найдена";
                case "500" : yield "Внутренняя ошибка сервиса";
                default: yield "Во время обработки запроса произошла ошибка";
            };
        }
        logger.warn(errorTitle);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorTitle", errorTitle);
        modelAndView.addObject("errorMsg", errorMsg);
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
