/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import br.edu.ifpb.dac.rhecruta.shared.domain.dto.Curriculum;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Candidate;
import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CandidateService;
import br.edu.ifpb.dac.rhecruta.shared.interfaces.CurriculumService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Pedro Arthur
 */
@WebServlet(name = "CurriculumServlet", urlPatterns = {"/curriculum"})
public class CurriculumServlet extends HttpServlet {

    @Inject
    private CurriculumService curriculumService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String strCandidateId = request.getParameter("candidateId");

        Object from = request.getParameter("from");
        System.out.println("[CurriculumServlet] from: " + from);

        if (strCandidateId == null || strCandidateId.isEmpty()) {
            response.sendRedirect("curriculum");
        }

        try {
            Curriculum curriculum = curriculumService.get(Long.valueOf(strCandidateId));
            String extension = FilenameUtils.getExtension(curriculum.getFilename());
            response.setContentType("application/" + extension);
            response.setHeader("Content-Disposition", "inline; filename=\"curriculo." + extension + "\"");
            ServletOutputStream output = response.getOutputStream();
            output.write(curriculum.getBytes());
        } catch (EJBException ex) {
            String url = request.getHeader("Referer");
            response.sendRedirect(addParam(url,"error","curriculumNotFound"));
        }
    }
    
    private String addParam(String url, String key, String value) {
        String expression = key + "=" + value;
        if(url.contains(expression))
            return url;
        if(url.contains("?"))
            return url + "&" + expression;
        return url + "?" + expression;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
