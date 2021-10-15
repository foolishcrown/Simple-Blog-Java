/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.article.ArticleDAO;
import sangnv.article.ArticleDTO;
import sangnv.utils.MyConstants;

/**
 *
 * @author Shang
 */
public class SearchServlet extends HttpServlet {

    private final String HOME_PAGE = "homePage";
    private final int NUM_PER_PAGE = MyConstants.TOTAL_ITEM_IN_PAGE;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(HOME_PAGE);
        //get session
        HttpSession session = request.getSession();
        //get session atttibutes
        String sessionTxtSearch = (String) session.getAttribute("SEARCH");
        String sessionIndexPage = (String) session.getAttribute("INDEXPAGE");

        //Init search by name load
        if (sessionTxtSearch == null) {
            sessionTxtSearch = "";
        }
        //Init page load
        if (sessionIndexPage == null) {
            sessionIndexPage = "1";
        }
        //Get request parameter
        String txtSearch = request.getParameter("txtSearch");
        String index = request.getParameter("indexPage");
        //Reset index page
        if (txtSearch != null && !sessionTxtSearch.equals(txtSearch)) {
            sessionIndexPage = "1";
            sessionTxtSearch = txtSearch.trim();
        }
        //New index page
        if (index != null) {
            sessionIndexPage = index;
        }
        try {
            //Call DAO
            ArticleDAO articleDAO = new ArticleDAO();
            //Calculate total pages
            int total = articleDAO.getTotalArticlePageSearchContent(sessionTxtSearch, NUM_PER_PAGE);
            //Convert and check index page
            int indexPage = Integer.parseInt(sessionIndexPage);
            if (indexPage > total) {
                indexPage = 1;
                sessionIndexPage = String.valueOf(indexPage);
            }
            //OFFSET_START
            int offset = (NUM_PER_PAGE * (indexPage - 1));
            if (offset < 0) {
                offset = 0;
            }
            //Get articles
            articleDAO.getArticlesOnContent(offset, NUM_PER_PAGE, sessionTxtSearch);
            List<ArticleDTO> result = articleDAO.getList();
            //Set and clear attributes
            request.setAttribute("LIST", result);
            request.setAttribute("PAGES", total);
            session.setAttribute("INDEXPAGE", sessionIndexPage);
            session.setAttribute("SEARCH", sessionTxtSearch);

        } catch (NumberFormatException e) {
            log("Error NumberFormatException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
