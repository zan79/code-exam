/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.Article;
import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 *
 * @author Lord Son
 */
public class ArticlesDAO {

    public static void addArticle(Article article) throws Exception {
        PreparedStatement pst = DB.connection().prepareStatement("INSERT INTO articles (title,content) VALUES (?,?)");
        pst.setString(1, article.getTitle());
        pst.setString(2, article.getContent());

        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss");
        //LocalDateTime now = LocalDateTime.now();
        pst.executeUpdate();
        pst.close();
    }

    public static void editArticle(Article article) throws Exception {
        PreparedStatement pst = DB.connection().prepareStatement(
                "UPDATE articles SET title = ?, content= ?"
                + " WHERE id = ?");
        pst.setString(1, article.getTitle());
        pst.setString(2, article.getContent());

        pst.executeUpdate();
        pst.close();
    }

    public static ArrayList getArticles() throws Exception {
        ArrayList<Article> articles = new ArrayList();

        Statement st = DB.connection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM articles");

        while (rs.next()) {
            Article art = new Article(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content")
            //rs.getString("date_created")
            );
            articles.add(art);
        }
        st.close();
        rs.close();

        return articles;
    }

    public static void deleteArticle(int id) throws Exception {
        Statement st = DB.connection().createStatement();
        st.executeUpdate("DELETE FROM articles WHERE id = " + id);
        st.close();
    }
}
