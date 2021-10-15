/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sangnv.account.AccountDAO;
import sangnv.account.AccountDTO;
import sangnv.utils.RandomId;
import sangnv.utils.SHA256Converter;
import sangnv.utils.SendEmail;

/**
 *
 * @author Shang
 */
public class RegisterServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalidPage";
    private final String CONFIRM_PAGE = "confirmPage";

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

        //Get request parameters
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        try {
            AccountDAO dao = new AccountDAO();
            boolean isDuplicate = dao.isDuplicate(email);
            if (!isDuplicate) {
                String sha256Password = SHA256Converter.toSHA256HexString(password);
                String authenCode = RandomId.generateId();
                AccountDTO dto = new AccountDTO();
                dto.setEmail(email);
                dto.setPassword(sha256Password);
                dto.setName(new String(name.getBytes("iso-8859-1"), "utf-8"));
                dto.setAuthenCode(authenCode);
                boolean register = dao.registerAccount(dto);
                if (register) {
                    SendEmail.sendMail(email, authenCode);
                    url = (String) siteMap.get(CONFIRM_PAGE);
                } else {
                    request.setAttribute("INVALID", "Register failed");
                }
            } else {
                request.setAttribute("INVALID", "The input email already exists in the system.");
            }
        } catch (UnsupportedEncodingException e) {
            log("Error UnsupportedEncodingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            log("Error NoSuchAlgorithmException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (SQLException e) {
            log("Error SQLException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (NamingException e) {
            log("Error NamingException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (RuntimeException e) {
            log("Error RuntimeException at " + this.getClass().getName() + ": " + e.getMessage());
        } catch (MessagingException e) {
            log("Error MessagingException at " + this.getClass().getName() + ": " + e.getMessage());
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
