/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent.service;

import ent.Memoir;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 陈文航
 */
@Stateless
@Path("ent.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MemoirPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findByMovieReleaseDate/{movieReleaseDate}")
    @Produces({"application/json"})
    public List<Memoir> findByMovieReleaseDate(@PathParam("movieReleaseDate") String movieReleaseDate) {
        Query query = em.createNamedQuery("Memoir.findByMovieReleaseDate");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate;
        try {
            newDate = format1.parse(movieReleaseDate);
            query.setParameter("movieReleaseDate", newDate);
        } catch (ParseException ex) {
            Logger.getLogger(CredentialsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return query.getResultList();
    }

    @GET
    @Path("findByWatchDate/{watchDate}")
    @Produces({"application/json"})
    public List<Memoir> findByWatchDate(@PathParam("watchDate") String watchDate) {
        Query query = em.createNamedQuery("Memoir.findByWatchDate");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate;
        try {
            newDate = format1.parse(watchDate);
            query.setParameter("watchDate", newDate);
        } catch (ParseException ex) {
            Logger.getLogger(CredentialsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return query.getResultList();
    }

    @GET
    @Path("findByComment/{comment}")
    @Produces({"application/json"})
    public List<Memoir> findByComment(@PathParam("comment") String comment) {
        Query query = em.createNamedQuery("Memoir.findByComment");
        query.setParameter("comment", comment);
        return query.getResultList();
    }

    @GET
    @Path("findByMovieName/{movieName}")
    @Produces({"application/json"})
    public List<Memoir> findByMovieName(@PathParam("movieName") String movieName) {
        Query query = em.createNamedQuery("Memoir.findByMovieName");
        query.setParameter("movieName", movieName);
        return query.getResultList();
    }

    @GET
    @Path("findByScore/{score}")
    @Produces({"application/json"})
    public List<Memoir> findByScore(@PathParam("score") String score) {
        Query query = em.createNamedQuery("Memoir.findByScore");
        query.setParameter("score", score);
        return query.getResultList();
    }

    @GET
    @Path("findByCinemaId/{cinemaId}")
    @Produces({"application/json"})
    public List<Memoir> findByCinemaId(@PathParam("cinemaId") Integer cinemaId) {
        Query query = em.createNamedQuery("Memoir.findByCinemaId");
        query.setParameter("cinemaId", cinemaId);
        return query.getResultList();
    }

    @GET
    @Path("findByCredentialsId/{credentialsId}")
    @Produces({"application/json"})
    public List<Memoir> findByCredentialsId(@PathParam("credentialsId") Integer credentialsId) {
        Query query = em.createNamedQuery("Memoir.findByCredentialsId");
        query.setParameter("credentialsId", credentialsId);
        return query.getResultList();
    }

    @GET
    @Path("findByTwoAttributesDynamic/{credentialsId}/{cinemaId}")
    @Produces({"application/json"})
    public List<Memoir> findByTwoAttributesDynamic(@PathParam("credentialsId") Integer credentialsId, @PathParam("cinemaId") Integer cinemaId) {
        TypedQuery<Memoir> q = em.createQuery("SELECT m FROM Memoir m WHERE m.credentialsId.credentialsId = :credentialsId AND m.cinemaId.cinemaId = :cinemaId", Memoir.class);
        q.setParameter("credentialsId", credentialsId);
        q.setParameter("cinemaId", cinemaId);
        return q.getResultList();
    }

    @GET
    @Path("findByTwoAttributesStatic/{credentialsId}/{cinemaId}")
    @Produces({"application/json"})
    public List<Memoir> findByTwoAttributesStatic(@PathParam("credentialsId") Integer credentialsId, @PathParam("cinemaId") Integer cinemaId) {
        Query query = em.createNamedQuery("Memoir.findByTwoAttributesStatic");
        query.setParameter("credentialsId", credentialsId);
        query.setParameter("cinemaId", cinemaId);
        return query.getResultList();
    }

    @GET
    @Path("findNumByUserIdDuringPeriod/{credentialsId}/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findNumByUserIdDuringPeriod(@PathParam("credentialsId") Integer credentialsId,
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate) {
        Query query = em.createQuery(
                "SELECT m.cinemaId.postcode,count(m.cinemaId.postcode) "
                + "FROM Memoir m "
                + "WHERE m.credentialsId.credentialsId = :credentialsId "
                + "AND cast(m.watchDate as DATE) >= :startDate "
                + "AND cast(m.watchDate as DATE) <= :endDate "
                + "GROUP BY m.cinemaId.postcode", Object[].class);
        query.setParameter("credentialsId", credentialsId);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date newStartDate;
        Date newEndDate;
        try {
            newStartDate = format1.parse(startDate);
            newEndDate = format1.parse(endDate);
            query.setParameter("startDate", newStartDate);
            query.setParameter("endDate", newEndDate);
        } catch (ParseException ex) {
            Logger.getLogger(CredentialsFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("postcode", String.valueOf(row[0]))
                    .add("total", String.valueOf(row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findNumPerMonthByUserIdYear/{credentialsId}/{year}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findNumPerMonthByUserIdYear(@PathParam("credentialsId") Integer credentialsId,
            @PathParam("year") String year) {
        String sql = "SELECT MONTH(WATCH_DATE), COUNT(*) "
                + "FROM MEMOIR "
                + "WHERE CREDENTIALS_ID = ?1 "
                + "AND YEAR(WATCH_DATE) = ?2 "
                + "GROUP BY MONTH(WATCH_DATE)";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, credentialsId);
        query.setParameter(2, year);

        List<Object> queryList = query.getResultList();
        int[] months = new int[13];
        for (int i = 0; i < queryList.size(); i++) {
            Object[] row = (Object[]) queryList.get(i);
            months[Integer.parseInt(String.valueOf(row[0]))] += Integer.parseInt(String.valueOf(row[1]));
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 1; i < 13; i++) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("month", String.valueOf(i))
                    .add("count", String.valueOf(months[i])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findReleaseEqualWatchByUserId/{credentialsId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findReleaseEqualWatchByUserId(@PathParam("credentialsId") Integer credentialsId) {
        String sql = "SELECT DISTINCT MOVIE_NAME, MOVIE_RELEASE_DATE "
                + "FROM MEMOIR "
                + "WHERE CREDENTIALS_ID = ?1 "
                + "AND YEAR(WATCH_DATE) = YEAR(MOVIE_RELEASE_DATE) ";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, credentialsId);

        List<Object> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            Object[] row = (Object[]) queryList.get(i);
            JsonObject personObject = Json.createObjectBuilder()
                    .add("movie_name", String.valueOf(row[0]))
                    .add("release_date", String.valueOf(row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findHigestMovieByUserId/{credentialsId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findHigestMovieByUserId(@PathParam("credentialsId") Integer credentialsId) {
        Query query = em.createQuery(
                "SELECT m.movieName,m.score,m.movieReleaseDate "
                + "FROM Memoir m "
                + "WHERE m.credentialsId.credentialsId = :credentialsId "
                + "AND m.score = (SELECT max(m1.score) FROM Memoir m1 WHERE m1.credentialsId.credentialsId = :credentialsId) ", Object[].class);
        query.setParameter("credentialsId", credentialsId);
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("movieName", String.valueOf(row[0]))
                    .add("score", String.valueOf(row[1]))
                    .add("movieReleaseDate", String.valueOf(row[2])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findRemakeMovieByUserId/{credentialsId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findRemakeMovieByUserId(@PathParam("credentialsId") Integer credentialsId) {
        Query query = em.createQuery(
                "SELECT m.movieName, m.movieReleaseDate "
                + "FROM Memoir m,("
                + "SELECT m1.movieName "
                + "FROM Memoir m1 "
                + "WHERE m1.credentialsId.credentialsId = :credentialsId "
                + "GROUP BY m1.movieName "
                + "HAVING COUNT(m1.movieName) > 1 ) as m2 "
                + "WHERE m.movieName = m2.movieName "
                + "GROUP BY m.movieName,m.movieReleaseDate", Object[].class);
        query.setParameter("credentialsId", credentialsId);
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("movieName", String.valueOf(row[0]))
                    .add("movieReleaseDate", String.valueOf(row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Path("findFiveHighestMovieByUserId/{credentialsId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findFiveHighestMovieByUserId(@PathParam("credentialsId") Integer credentialsId) {
        String sql = "SELECT MOVIE_NAME, MOVIE_RELEASE_DATE, SCORE "
                + "FROM MEMOIR "
                + "WHERE ABS(YEAR(MOVIE_RELEASE_DATE)-YEAR(CURRENT_DATE))<=5 "
                + "AND CREDENTIALS_ID = ?1 "
                + "ORDER BY SCORE DESC";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, credentialsId);
        List<Object> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int len = 5 < queryList.size() ? 5 : queryList.size();
        for (int i = 0; i < len; i++) {
            Object[] row = (Object[]) queryList.get(i);
            JsonObject personObject = Json.createObjectBuilder()
                    .add("movie_name", String.valueOf(row[0]))
                    .add("release_date", String.valueOf(row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
