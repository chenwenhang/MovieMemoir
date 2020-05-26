package com.echo.moviememoir.entity;

import java.util.Date;

public class Memoir {
    private Integer memoirId;
    private Cinema cinemaId;
    private Credential credentialsId;
    private String movieName;
    private String comment;
    private Date movieReleaseDate;
    private String score;
    private Date watchDate;

    public Memoir() {
    }

    public Memoir(Integer memoirId, Cinema cinemaId, Credential credentialsId, String movieName, String comment, Date movieReleaseDate, String score, Date watchDate) {
        this.memoirId = memoirId;
        this.cinemaId = cinemaId;
        this.credentialsId = credentialsId;
        this.movieName = movieName;
        this.comment = comment;
        this.movieReleaseDate = movieReleaseDate;
        this.score = score;
        this.watchDate = watchDate;
    }

    public Integer getMemoirId() {
        return memoirId;
    }

    public void setMemoirId(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Credential getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Credential credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(Date watchDate) {
        this.watchDate = watchDate;
    }

    @Override
    public String toString() {
        return "Memoir{" +
                "memoirId=" + memoirId +
                ", cinemaId=" + cinemaId +
                ", credentialsId=" + credentialsId +
                ", movieName='" + movieName + '\'' +
                ", comment='" + comment + '\'' +
                ", movieReleaseDate=" + movieReleaseDate +
                ", score='" + score + '\'' +
                ", watchDate='" + watchDate + '\'' +
                '}';
    }
}
