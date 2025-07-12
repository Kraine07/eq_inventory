/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.exception;

import jakarta.servlet.http.HttpServletRequest;
import kraine.app.eq_inventory.SessionHandler;


import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Kraine
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
        return "redirect:/";
    }

    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, RedirectAttributes redirectAttributes) {
        BindingResult bindingResult = ex.getBindingResult();

        // Store validation errors in RedirectAttributes
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
        redirectAttributes.addFlashAttribute("user", bindingResult.getTarget());

        return "redirect:/";
}

    // Handle UserNotFound exception
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }

    // handle password not found
    @ExceptionHandler(PasswordNotFoundException.class)
    public String handlePasswordNotFound(PasswordNotFoundException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error",true);
        redirectAttributes.addFlashAttribute("errorMessage",ex.getMessage());
        return "redirect:/";
    }

    // Special handler for duplicate user errors
    @ExceptionHandler(DuplicateUserException.class)
    public String handleDuplicateUserException(DuplicateUserException ex, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return SessionHandler.hasSessionAttribute(request, "authUser") ? "redirect:/app/admin" : "setup";
    }

    // when manufacturer cannot be deleted because it has related equipment
    @ExceptionHandler(DeleteManufacturerException.class)
    public String handleDeleteManufacturerException(DeleteManufacturerException ex, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }
}
