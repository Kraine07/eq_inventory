package kraine.app.eq_inventory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionHandler {

    // Setting an attribute
    public static void addAttribute(HttpServletRequest request, String attribute, Object object) {
        if (attribute == null || object == null) {
            throw new IllegalArgumentException("Attribute name and object cannot be null");
        }

        HttpSession session = request.getSession();
        session.setAttribute(attribute, object);
        System.out.println("Added attribute: " + attribute);
    }


    public static <T> T getAttribute(HttpServletRequest request, String attribute, Class <T> type) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute(attribute);
            if (type.isInstance(obj)){
                return type.cast(obj);
            }
        }
        return null;
    }


    public static void removeAttribute(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(attribute);
            System.out.println("Removed attribute: " + attribute);
        }
    }


    public static boolean hasSessionAttribute(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(attribute) != null;
    }


    public static void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
            System.out.println("Session invalidated.");
        }
    }
}
