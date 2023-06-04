package com.example.crawler.cgv;

import com.example.crawler.cgv.elements.ColTime;
import com.example.crawler.cgv.elements.CgvMovieDetailCrawlResult;
import com.example.crawler.cgv.elements.CgvCrawlResult;
import com.example.crawler.cgv.elements.CgvScreen;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class CrawlerCGV {

    public static void main(String[] args) {
        CrawlerCGV crawlerCGV = new CrawlerCGV();
        System.out.println(crawlerCGV.crawlMovie("http://www.cgv.co.kr/movies/detail-view/?midx=87034"));
    }

    String url = "http://www.cgv.co.kr/theaters/?areacode=02"; //오리
    String str1 = "&theaterCode=";
    String str2 = "&date=";

    public CgvCrawlResult crawl(String theater, String date) {
        String s1 = str1 + theater;
        String s2 = str2 + date;
        WebDriver driver = null;

        CgvCrawlResult crawlResult = new CgvCrawlResult();
        crawlResult.setCompany("CGV");
        crawlResult.setTheater_code(theater);
        crawlResult.setFile_targetDate(date);
        crawlResult.setFile_createdDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        crawlResult.setColTimes(new ArrayList<>());

        try {
            driver = new ChromeDriver();
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.get(url + s1 + s2);
            driver.switchTo().frame("ifrm_movie_time_table");
            List<WebElement> coltimesList = driver.findElement(By.className("showtimes-wrap"))
                                                  .findElement(By.className("sect-showtimes"))
                                                  .findElements(By.className("col-times"));
            //이따가 그림으로 정리하기! -> 일단 정리함!
            for (WebElement we : coltimesList) {
                String movieTitle = we.findElement(By.className("info-movie"))
                                      .findElement(By.tagName("strong")).getText();
                String movieURL = we.findElement(By.className("info-movie"))
                                    .findElement(By.tagName("a")).getAttribute("href");
                String genres = we.findElement(By.className("info-movie"))
                                  .findElements(By.tagName("i")).get(1).getText().trim();
                String runtime = we.findElement(By.className("info-movie"))
                        .findElements(By.tagName("i")).get(2).getText().trim().replace("분", "");
                String releaseDate = we.findElement(By.className("info-movie"))
                                       .findElements(By.tagName("i")).get(3).getText().replace("개봉", "").replace(".", "").trim();

                ColTime colTime = new ColTime();
                colTime.setMovie_title(movieTitle);
                colTime.setRuntime(Integer.parseInt(runtime));
                colTime.setUrl(movieURL);
                colTime.setMovie_genres(Arrays.asList(genres.split(", ")));
                colTime.setRelease_date(releaseDate);
                colTime.setScreens(new ArrayList<>());

                List<WebElement> typehallList = we.findElements(By.className("type-hall"));
                    for(WebElement hall : typehallList){
                        hall.findElements(By.className("info-hall")).get(0).findElement(By.tagName("li")).getText();
                        //String screenTime = hall.findElement(By.className("info-timetable")).getText().replace("상영시간", "").trim();

                        List<WebElement> wes = hall.findElement(By.className("info-hall")).findElements(By.tagName("li"));

                        List<WebElement> screenTimes = hall.findElement(By.className("info-timetable")).findElements(By.tagName("li"));
                        screenTimes.forEach(screenTime -> {
                            CgvScreen screen = new CgvScreen();
                            screen.setScreen_name(wes.get(1).getText());
                            screen.setScreen_seats(wes.get(2).getText());
                            WebElement a;
                            try {
                                a = screenTime.findElement(By.tagName("a"));
                                screen.setScreen_startTime(a.getAttribute("data-playstarttime"));
                                screen.setScreen_endTime(a.getAttribute("data-playendtime"));
                                colTime.getScreens().add(screen);
                            }catch (Exception e){
                                //e.printStackTrace();
                                System.out.println(colTime.getMovie_title() + " : 예매가능한 시간이 없는듯?");
                            }
                        });
                    }
                crawlResult.getColTimes().add(colTime);
                }
            driver.close();
            return crawlResult;

            } catch (TimeoutException e) {
            if(driver!=null) driver.close();
            e.printStackTrace();
        }
        return null;
    }

    public CgvMovieDetailCrawlResult crawlMovie(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element e1 = doc.getElementsByClass("wrap-movie-detail").get(0);
            String title1 = e1.getElementsByClass("title").get(0).getElementsByTag("strong").get(0).text();
            String title2 = e1.getElementsByClass("title").get(0).getElementsByTag("p").get(0).text();

                Element e2 = e1.getElementsByClass("spec").get(0);
                String directorString = e2.getElementsByTag("dd").get(0).text();
                String[] directors = directorString.split(" , ");
                String actorString = e2.getElementsByClass("on").get(0).text();
                String subInfoString = e2.getElementsByClass("on").get(1).text();
                    String[] subInfo = subInfoString.split(", ");
                    String grade = subInfo[0];
                    String runtime = subInfo[1].replace("분", "");
                    List<String> nations = new ArrayList<>(Arrays.asList(subInfo).subList(2, subInfo.length));
                String releaseDate = e2.getElementsByClass("on").get(2).text();
                String[] actors = actorString.split(" , ");

                Elements es = e1.getElementsByTag("dt");
                String[] genres = new String[0];
                for(Element e : es){
                    if(e.text().contains("장르 : ")){
                        genres = e.text().replace("장르 : ", "").split(", ");
                        break;
                    }
                }

            String plot = doc.getElementsByClass("sect-story-movie").get(0).text();

            CgvMovieDetailCrawlResult movieDetailCrawlResult = new CgvMovieDetailCrawlResult();
            movieDetailCrawlResult.setUrl(url);
            movieDetailCrawlResult.setTitle(title1);
            movieDetailCrawlResult.setTitleOther(title2);
            movieDetailCrawlResult.setDirectors(Arrays.asList(directors));
            movieDetailCrawlResult.setActors(Arrays.asList(actors));
            movieDetailCrawlResult.setGenres(Arrays.asList(genres));
            movieDetailCrawlResult.setAge(grade);
            movieDetailCrawlResult.setRuntime(runtime);
            movieDetailCrawlResult.setNations(nations);
            movieDetailCrawlResult.setReleaseDate(releaseDate);
            movieDetailCrawlResult.setStory(plot);
            return movieDetailCrawlResult;
        } catch (IOException e) {
            e.printStackTrace();
            CgvMovieDetailCrawlResult movieDetailCrawlResult = new CgvMovieDetailCrawlResult();
            movieDetailCrawlResult.setTitle("error");
            movieDetailCrawlResult.setTitleOther(e.getMessage());
            return movieDetailCrawlResult;
        }
    }
}
