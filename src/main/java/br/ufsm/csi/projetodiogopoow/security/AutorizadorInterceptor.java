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

        // Permitir acesso liberado para login, logout e recursos estáticos
        if (url.equals("/") || url.equals("/login") || url.equals("/logout") || url.startsWith("/resources/")) {
            return true;
        }

        // Verificar se o dirigente está na sessão
        if (request.getSession(false) == null || request.getSession(false).getAttribute("dirigenteLogado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
