package com.echo.moviememoir.utils;

public class RatingStar {
    public static float rating2Star(String rating) {
        float score = Float.parseFloat(rating);
        float star = 0;
        if (score >= 1 && score <= 9) {
            star = 0f;
        } else if (score >= 10 && score <= 18) {
            star = 0.5f;
        } else if (score >= 19 && score <= 27) {
            star = 1f;
        } else if (score >= 28 && score <= 36) {
            star = 1.5f;
        } else if (score >= 37 && score <= 45) {
            star = 2f;
        } else if (score >= 46 && score <= 54) {
            star = 2.5f;
        } else if (score >= 55 && score <= 63) {
            star = 3f;
        } else if (score >= 64 && score <= 72) {
            star = 3.5f;
        } else if (score >= 73 && score <= 81) {
            star = 4f;
        } else if (score >= 82 && score <= 90) {
            star = 4.5f;
        } else if (score >= 91 && score <= 99) {
            star = 5f;
        }
        return star;
    }
}
