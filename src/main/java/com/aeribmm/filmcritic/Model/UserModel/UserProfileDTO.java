package com.aeribmm.filmcritic.Model.UserModel;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;

import java.util.List;

public class UserProfileDTO {
    private String username;
    private String email;
    private int totalViewed;
    private double averageScore;
    private String timeSpend;
    private List<WatchList> list;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String username, String email, int totalViewed, double averageScore, String timeSpend, List<WatchList> list) {
        this.username = username;
        this.email = email;
        this.totalViewed = totalViewed;
        this.averageScore = averageScore;
        this.timeSpend = timeSpend;
        this.list = list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalViewed() {
        return totalViewed;
    }

    public void setTotalViewed(int totalViewed) {
        this.totalViewed = totalViewed;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(String timeSpend) {
        this.timeSpend = timeSpend;
    }

    public List<WatchList> getList() {
        return list;
    }

    public void setList(List<WatchList> list) {
        this.list = list;
    }
}
