package com.example.database.external.kobis.elements;



import java.util.List;

public class KobisMovieListResult {
    String totCnt;
    String source;
    List<KobisMovieVO> movieList;
	public String getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<KobisMovieVO> getMovieList() {
		return movieList;
	}
	public void setMovieList(List<KobisMovieVO> movieList) {
		this.movieList = movieList;
	}
	@Override
	public String toString() {
		return "MovieListResult [totCnt=" + totCnt + ", source=" + source + ", movieList=" + movieList
				+ ", getTotCnt()=" + getTotCnt() + ", getSource()=" + getSource() + ", getMovieList()=" + getMovieList()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
