package jain.akshay.themoviedb.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by akshay on 16/03/18.
 */

public class RootObject implements Parcelable{
    private int page;

    protected RootObject(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
        results = in.createTypedArrayList(Result.CREATOR);
    }

    public static final Creator<RootObject> CREATOR = new Creator<RootObject>() {
        @Override
        public RootObject createFromParcel(Parcel in) {
            return new RootObject(in);
        }

        @Override
        public RootObject[] newArray(int size) {
            return new RootObject[size];
        }
    };

    public int getPage() { return this.page; }

    public void setPage(int page) { this.page = page; }

    private int total_results;

    public int getTotalResults() { return this.total_results; }

    public void setTotalResults(int total_results) { this.total_results = total_results; }

    private int total_pages;

    public int getTotalPages() { return this.total_pages; }

    public void setTotalPages(int total_pages) { this.total_pages = total_pages; }

    private ArrayList<Result> results;

    public ArrayList<Result> getResults() { return this.results; }

    public void setResults(ArrayList<Result> results) { this.results = results; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(total_results);
        dest.writeInt(total_pages);
        dest.writeTypedList(results);
    }
}
