package in.pankajadhyapak.popularmovies2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllReviews {

    @SerializedName("results")
    private ArrayList<Review> results;

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }
}
