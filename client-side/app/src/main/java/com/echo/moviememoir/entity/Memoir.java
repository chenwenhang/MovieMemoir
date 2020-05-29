package com.echo.moviememoir.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.echo.moviememoir.entity.convert.CinemaTypeConverter;
import com.echo.moviememoir.entity.convert.CredentialTypeConverter;
import com.echo.moviememoir.entity.convert.DateTypeConverter;
import com.google.gson.annotations.Expose;

import java.util.Date;

@Entity
public class Memoir {
    @PrimaryKey(autoGenerate = true)
    private Integer memoirId;

    @TypeConverters(CinemaTypeConverter.class)
    private Cinema cinemaId;

    @TypeConverters(CredentialTypeConverter.class)
    private Credential credentialsId;

    private String movieName;

    private String comment;

    @TypeConverters(DateTypeConverter.class)
    private Date movieReleaseDate;

    private String score;

    @TypeConverters(DateTypeConverter.class)
    private Date watchDate;

    @Expose(serialize = false, deserialize = false)
    private String description;

    @Expose(serialize = false, deserialize = false)
    @TypeConverters(DateTypeConverter.class)
    private Date addDateTime;

    @Expose(serialize = false, deserialize = false)
    private String imageUrl;

    public Memoir() {
    }

    public Memoir(Integer memoirId, Cinema cinemaId, Credential credentialsId, String movieName, String comment, Date movieReleaseDate, String score, Date watchDate, String description, Date addDateTime, String imageUrl) {
        this.memoirId = memoirId;
        this.cinemaId = cinemaId;
        this.credentialsId = credentialsId;
        this.movieName = movieName;
        this.comment = comment;
        this.movieReleaseDate = movieReleaseDate;
        this.score = score;
        this.watchDate = watchDate;
        this.description = description;
        this.addDateTime = addDateTime;
        this.imageUrl = imageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(Date addDateTime) {
        this.addDateTime = addDateTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
                ", watchDate=" + watchDate +
                ", description='" + description + '\'' +
                ", addDateTime=" + addDateTime +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
