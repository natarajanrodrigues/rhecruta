//
//import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
//import br.edu.ifpb.dac.rhecruta.web.utils.SessionUtils;
//import java.io.IOException;
//import javax.inject.Inject;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author natarajan
// */
//@WebFilter(filterName = "loggedFilter", urlPatterns = {"/faces/manager/*", "/faces/candidate/*"})
//public class LoggedFilter implements Filter {
//
////    @Inject
////    User loggedUser;
//    
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//         // Get the loginBean from session attribute
//        User logged = (User)((HttpServletRequest)request).getSession().getAttribute("loggedUser");
//        SessionUtils.getSession(true);
//        
//        
//        // For the first application request there is no loginBean in the session so user needs to log in
//        // For other requests loginBean is present but we need to check if user has logged in successfully
//        if (logged == null) {
//            String contextPath = ((HttpServletRequest)request).getContextPath();
//            ((HttpServletResponse)response).sendRedirect(contextPath + "/faces/index.xhtml");
//        }
//         
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//}
