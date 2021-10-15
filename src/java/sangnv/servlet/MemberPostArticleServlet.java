/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sangnv.account.AccountDTO;
import sangnv.article.ArticleDAO;
import sangnv.article.ArticleDTO;
import sangnv.utils.RandomId;

/**
 *
 * @author Shang
 */
public class MemberPostArticleServlet extends HttpServlet {

    private final String SEARCH_ACTION = "searchAction";
    private final String INVALID_PAGE = "invalidPage";

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
        String url = (String) siteMap.get(INVALID_PAGE);
        //Get session
        HttpSession session = request.getSession();
        //Get session attributes
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");

        //Get request params
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String content = request.getParameter("content");
        try {
            //call DAO
            ArticleDAO articleDAO = new ArticleDAO();
            if (account != null && title != null && description != null && content != null) {
                String randomId = RandomId.generateId();
                content = content.replaceAll("\n", "\\\n");
                ArticleDTO dto = new ArticleDTO();
                dto.setId(randomId);
                dto.setTitle(new String(title.getBytes("iso-8859-1"), "utf-8"));
                dto.setDescription(new String(description.getBytes("iso-8859-1"), "utf-8"));
                dto.setContent(new String(content.getBytes("iso-8859-1"), "utf-8"));
                dto.setAuthor(account.getEmail());
                boolean isPost = articleDAO.postArticle(dto);
                if (isPost) {
                    url = (String) siteMap.get(SEARCH_ACTION);
                } else {
                    request.setAttribute("INVALID", "Post article failed. Please try again!");
                }
            }
        } catch (UnsupportedEncodingException e) {
            log("Error UnsupportedEncodingException at " + this.getClass().getName() + ": " + e.getMessage());
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
