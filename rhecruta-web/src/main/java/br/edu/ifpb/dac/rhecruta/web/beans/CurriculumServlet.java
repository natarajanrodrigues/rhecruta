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

/**
 *
 * @author Pedro Arthur
 */
@WebServlet(name = "CurriculumServlet", urlPatterns = {"/curriculum"})
public class CurriculumServlet extends HttpServlet {

    @Inject
    private CurriculumService curriculumService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Content-Disposition", "inline; filename=\"curriculo.pdf\"");
        response.setContentType("application/pdf");

        String strCandidateId = request.getParameter("candidateId");

        if (strCandidateId == null || strCandidateId.isEmpty()) {
            response.sendRedirect("curriculum");
        }

        try {
            Curriculum curriculum = curriculumService.get(Long.valueOf(strCandidateId));
            ServletOutputStream output = response.getOutputStream();
            output.write(curriculum.getBytes());
        } catch (EJBException ex) {            
            response.sendRedirect("faces/candidate/curriculum.xhtml?success=false");
        }
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
