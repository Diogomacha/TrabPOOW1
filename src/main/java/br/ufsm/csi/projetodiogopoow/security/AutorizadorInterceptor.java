package br.ufsm.csi.projetodiogopoow.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutorizadorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String url = request.getRequestURI();

        if (
                url.equals("/") ||
                        url.equals("/login") ||
                        url.equals("/logout") ||
                        url.equals("/index") ||
                        url.equals("/dirigente/cadastro") || 
                        url.startsWith("/resources/") ||
                        url.startsWith("/css/") ||
                        url.startsWith("/js/") ||
                        url.startsWith("/images/")
        ) {
            return true;
        }


        if (request.getSession(false) == null ||
                request.getSession(false).getAttribute("dirigenteLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
