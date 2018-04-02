package com.example.movie.movielist.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lakshkotian on 05/03/18.
 */

public class ExpandedSearchResults implements Serializable {

    public String name;
    public String releaseDate;
    public String summary;
    public String posterPath;
    public String id;
    public String[] genre;
    public String type;
    public String rating;
    public String director;
    public String backdropPath;
    public ArrayList<Cast> castList;

    public ExpandedSearchResults(SearchResult sr){
        name =sr.name;
        releaseDate = sr.releaseDate;
        summary = sr.summary;
        posterPath =sr.posterPath;
        id =sr.id;
        type =sr.type;
        castList = new ArrayList<>();
    }

}
