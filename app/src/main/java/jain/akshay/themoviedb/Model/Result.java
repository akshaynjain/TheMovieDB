package jain.akshay.themoviedb.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by akshay on 16/03/18.
 */

public class Result implements Parcelable{
    private double vote_average;

    protected Result(Parcel in) {
        vote_average = in.readDouble();
        vote_count = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        media_type = in.readString();
        title = in.readString();
        popularity = in.readDouble();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
        original_name = in.readString();
        name = in.readString();
        first_air_date = in.readString();
        origin_country = in.createStringArrayList();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public Result(String type) {
        this.type = type;
    }

    public String type;

    public double getVoteAverage() { return this.vote_average; }

    public void setVoteAverage(double vote_average) { this.vote_average = vote_average; }

    private int vote_count;

    public int getVoteCount() { return this.vote_count; }

    public void setVoteCount(int vote_count) { this.vote_count = vote_count; }

    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private boolean video;

    public boolean getVideo() { return this.video; }

    public void setVideo(boolean video) { this.video = video; }

    private String media_type;

    public String getMediaType() { return this.media_type; }

    public void setMediaType(String media_type) { this.media_type = media_type; }

    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private double popularity;

    public double getPopularity() { return this.popularity; }

    public void setPopularity(double popularity) { this.popularity = popularity; }

    private String poster_path;

    public String getPosterPath() { return this.poster_path; }

    public void setPosterPath(String poster_path) { this.poster_path = poster_path; }

    private String original_language;

    public String getOriginalLanguage() { return this.original_language; }

    public void setOriginalLanguage(String original_language) { this.original_language = original_language; }

    private String original_title;

    public String getOriginalTitle() { return this.original_title; }

    public void setOriginalTitle(String original_title) { this.original_title = original_title; }

    private ArrayList<Integer> genre_ids;

    public ArrayList<Integer> getGenreIds() { return this.genre_ids; }

    public void setGenreIds(ArrayList<Integer> genre_ids) { this.genre_ids = genre_ids; }

    private String backdrop_path;

    public String getBackdropPath() { return this.backdrop_path; }

    public void setBackdropPath(String backdrop_path) { this.backdrop_path = backdrop_path; }

    private boolean adult;

    public boolean getAdult() { return this.adult; }

    public void setAdult(boolean adult) { this.adult = adult; }

    private String overview;

    public String getOverview() { return this.overview; }

    public void setOverview(String overview) { this.overview = overview; }

    private String release_date;

    public String getReleaseDate() { return this.release_date; }

    public void setReleaseDate(String release_date) { this.release_date = release_date; }

    private String original_name;

    public String getOriginalName() { return this.original_name; }

    public void setOriginalName(String original_name) { this.original_name = original_name; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String first_air_date;

    public String getFirstAirDate() { return this.first_air_date; }

    public void setFirstAirDate(String first_air_date) { this.first_air_date = first_air_date; }

    private ArrayList<String> origin_country;

    public ArrayList<String> getOriginCountry() { return this.origin_country; }

    public void setOriginCountry(ArrayList<String> origin_country) { this.origin_country = origin_country; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(media_type);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_name);
        dest.writeString(name);
        dest.writeString(first_air_date);
        dest.writeStringList(origin_country);
    }
}
