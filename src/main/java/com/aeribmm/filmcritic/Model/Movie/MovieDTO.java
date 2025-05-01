package com.aeribmm.filmcritic.Model.Movie;

public class MovieDTO {
    private String title;
    private String posterURL;
    private String plot;

    public MovieDTO() {
    }

    public MovieDTO(String title, String posterURL, String plot) {
        this.title = title;
        this.posterURL = posterURL;
        this.plot = plot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
