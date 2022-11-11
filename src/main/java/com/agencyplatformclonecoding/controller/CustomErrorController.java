package com.agencyplatformclonecoding.controller;

import com.agencyplatformclonecoding.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletResponse response, Model model) {

		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_SERVER_ERROR;

		model.addAttribute("status", status);
		model.addAttribute("errorCode", errorCode);
		model.addAttribute("message", errorCode.getMessage(status.getReasonPhrase()));

        return "error/error";

	}
}
