package com.example.database.external.kobis.elements;


public class KobisMovieListResponse {
    KobisMovieListResult kobisMovieListResult;

	public KobisMovieListResult getMovieListResult() {
		return kobisMovieListResult;
	}

	public void setMovieListResult(KobisMovieListResult kobisMovieListResult) {
		this.kobisMovieListResult = kobisMovieListResult;
	}

	@Override
	public String toString() {
		return "KobisResponse [movieListResult=" + kobisMovieListResult + ", getMovieListResult()=" + getMovieListResult()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
    
}
