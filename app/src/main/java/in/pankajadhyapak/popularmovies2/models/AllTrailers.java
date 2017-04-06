package in.pankajadhyapak.popularmovies2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllTrailers {

    @SerializedName("results")
    private ArrayList<Trailer> results;

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
