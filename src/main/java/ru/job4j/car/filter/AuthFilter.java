package ru.job4j.car.filter;

import org.springframework.stereotype.Component;
import ru.job4j.car.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    /**
     * Интерфейс Filter содержит метод doFilter. Через этот метод будут проходить запросы к сервлетам.
     * Если запрос идет к адресам loginPage или login, то мы их пропускаем сразу.
     * Если запросы идут к другим адресам, то проверяем наличие пользователя в HttpSession
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage")
                || uri.endsWith("login")
                || uri.endsWith("saveUser")
                || uri.endsWith("index")
                || uri.contains("photoAdFirst")) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        User user = (User) req.getSession().getAttribute("user");
        if ((uri.endsWith("settings")
                || uri.endsWith("addBody")
                || uri.endsWith("addMark")
                || uri.endsWith("addEngine")
                || uri.endsWith("addTransmission")
                || uri.endsWith("addUSer"))
                && !user.getName().equals("Admin")
        ) {
            res.sendRedirect(req.getContextPath() + "/index");
            return;
        }
        chain.doFilter(req, res);
    }
}
