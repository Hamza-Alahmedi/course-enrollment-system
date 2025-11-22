package com.hamza.courseenrollmentsystem.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Page Not Found");
                model.addAttribute("errorDescription", "The page you are looking for doesn't exist.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorMessage", "Internal Server Error");
                model.addAttribute("errorDescription", "Something went wrong on our end. Please try again later.");

                // Log the actual error for debugging
                Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                if (exception != null) {
                    exception.printStackTrace();
                }
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorMessage", "Access Denied");
                model.addAttribute("errorDescription", "You don't have permission to access this resource.");
            } else {
                model.addAttribute("errorCode", statusCode.toString());
                model.addAttribute("errorMessage", "Error");
                model.addAttribute("errorDescription", "An error occurred.");
            }
        }

        return "error";
    }
}

