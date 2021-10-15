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
public class AdminSearchServlet extends HttpServlet {

    private final String ADMIN_PAGE = "adminPage";
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
        String url = (String) siteMap.get(ADMIN_PAGE);
        //get session
        HttpSession session = request.getSession();
        //get session atttibutes
        String sessionTxtSearchArticle = (String) session.getAttribute("ADMINSEARCHARTICLE");
        String sessionTxtSearchContent = (String) session.getAttribute("ADMINSEARCHCONTENT");
        String sessionIndexPage = (String) session.getAttribute("INDEXPAGE");
        String sessionSelectedStatus = (String) session.getAttribute("SELECTEDSTATUS");
        String sessionSearchOpt = (String) session.getAttribute("OPTSEARCH");
        //Init search by name load
        if (sessionTxtSearchContent == null) {
            sessionTxtSearchContent = "";
        }
        //Init search by name load
        if (sessionTxtSearchArticle == null) {
            sessionTxtSearchArticle = "";
        }
        //Init page load
        if (sessionIndexPage == null) {
            sessionIndexPage = "1";
        }
        //Init selected status load
        if (sessionSelectedStatus == null) {
            sessionSelectedStatus = "2";
        }
        //Init option load
        if (sessionSearchOpt == null) {
            sessionSearchOpt = "SearchByArticleName";
        }
        //Get request parameter
        String txtSearchContent = request.getParameter("txtSearchContent");
        String txtSearchArticle = request.getParameter("txtSearchArticleName");
        String index = request.getParameter("indexPage");
        String selectedStatus = request.getParameter("status");
        String searchOpt = request.getParameter("SearchOpt");
        //New search option
        if (searchOpt != null) {
            sessionSearchOpt = searchOpt;
        }
        //New index page
        if (index != null) {
            sessionIndexPage = index;
        }
        //Reset index page
        if (txtSearchContent != null && !sessionTxtSearchContent.equals(txtSearchContent)) {
            sessionIndexPage = "1";
            sessionTxtSearchContent = txtSearchContent.trim();
        }
        if (txtSearchArticle != null && !sessionTxtSearchArticle.equals(txtSearchArticle)) {
            sessionIndexPage = "1";
            sessionTxtSearchArticle = txtSearchArticle.trim();
        }
        if (selectedStatus != null && !sessionSelectedStatus.equals(selectedStatus)) {
            sessionIndexPage = "1";
            sessionSelectedStatus = selectedStatus;
        }
        try {
            //Convert status
            int status = Integer.parseInt(sessionSelectedStatus);
            //Call DAO
            ArticleDAO articleDAO = new ArticleDAO();
            //Calculate total pages
            int total = 0;
            if (sessionSearchOpt.equals("SearchByArticleName")) {
                total = articleDAO.getTotalArticlePageSearchNameByAdmin(sessionTxtSearchArticle, status, NUM_PER_PAGE);
            }else if (sessionSearchOpt.equals("SearchByContent")) {
                total = articleDAO.getTotalArticlePageSearchContentByAdmin(sessionTxtSearchContent, status, NUM_PER_PAGE);
            }
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
            if (sessionSearchOpt.equals("SearchByArticleName")){
                articleDAO.getArticlesOnNameByAdmin(offset, NUM_PER_PAGE, sessionTxtSearchArticle, status);
            }else if (sessionSearchOpt.equals("SearchByContent")) {
                articleDAO.getArticlesOnContentByAdmin(offset, NUM_PER_PAGE, sessionTxtSearchContent, status);
            }
            List<ArticleDTO> result = articleDAO.getList();
            //Set and clear attributes
            request.setAttribute("LIST", result);
            request.setAttribute("PAGES", total);
            session.setAttribute("INDEXPAGE", sessionIndexPage);
            session.setAttribute("OPTSEARCH", sessionSearchOpt);
            session.setAttribute("SELECTEDSTATUS", sessionSelectedStatus);
            if (sessionSearchOpt.equals("SearchByArticleName")) {
                session.setAttribute("ADMINSEARCHARTICLE", sessionTxtSearchArticle);
                session.removeAttribute("ADMINSEARCHCONTENT");
            }else if (sessionSearchOpt.equals("SearchByContent")) {
                session.setAttribute("ADMINSEARCHCONTENT", sessionTxtSearchContent);
                session.removeAttribute("ADMINSEARCHARTICLE");
            }

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
