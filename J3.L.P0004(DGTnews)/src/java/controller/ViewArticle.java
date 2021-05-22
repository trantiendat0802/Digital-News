/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ArticleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Article;

/**
 *
 * @author T.DAT
 */

@WebServlet(name = "ViewArticle", urlPatterns = {"/ViewArticle"})
public class ViewArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ArticleDAO articleDao = new ArticleDAO();
            // get most recent article
            Article mostRecentArticle = articleDao.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            ArrayList<Article> fiveRecentAticle = articleDao.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            Article article = articleDao.getArticleById(id);
            // check article exist or not
            if (article == null) {
                request.setAttribute("error", "Not found article");
                request.getRequestDispatcher("/search.jsp").forward(request, response);
            } else {
                request.setAttribute("articleCurrent", article);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/search.jsp").forward(request, response);
        }
    }

}