package com.example.database.mongoDB;

import com.example.crawler.cgv.elements.CrawlResult;
import com.example.crawler.cgv.elements.MovieDetailCrawlResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DoorToMongoDB {
    String url = "mongodb://localhost:27017";
    String dbName = "popcorn";

    public void insertOne(MovieDetailCrawlResult result) {
        try(MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);

            if(selectOneByUrl(result.getUrl()) != null){
                //추후에 업데이트 코드 작성
                return;
            }

            MongoCollection<Document> collection = db.getCollection("movie_info");

            Document document = new Document();
            document.append("url", result.getUrl());
            document.append("title", result.getTitle());
            document.append("titleOther", result.getTitleOther());
            document.append("directors", result.getDirectors());
            document.append("actors", result.getActors());
            document.append("genres", result.getGenres());
            document.append("age", result.getAge());
            document.append("runtime", result.getRuntime());
            document.append("nations", result.getNations());
            document.append("releaseDate", result.getReleaseDate());
            document.append("story", result.getStory());

            collection.insertOne(document);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MovieDetailCrawlResult selectOneByUrl(String url){
        try(MongoClient mongoClient = MongoClients.create(this.url)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection("movie_info");
            Document doc = collection.find(Filters.eq("url", url))
                                                  .projection(Projections.excludeId())
                                                  .first();
            ObjectMapper mapper = new ObjectMapper();
            if (doc != null) {
                return mapper.readValue(doc.toJson(), MovieDetailCrawlResult.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void insertOne(CrawlResult result){
        Document document = Document.parse(result.toJsonString());
        try(MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = db.getCollection("crawl_result");

            Document filter = new Document();
            filter.append("theater_code", result.getTheater_code());
            filter.append("file_targetDate", result.getFile_targetDate());

            // Create an update operation
            Document update = new Document("$set", Document.parse(result.toJsonString()));

            // Update a single document that matches the filter, or insert if it doesn't exist
            collection.updateOne(filter, update, new UpdateOptions().upsert(true));
        }catch (Exception e){
            e.printStackTrace();
            //return null;
        }
    }

    public CrawlResult selectOneByTheaterAndDate(String theaterCode, String date){
        try(MongoClient mongoClient = MongoClients.create(this.url)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection("crawl_result");

            Document filter = new Document();
            filter.append("theater_code", theaterCode);
            filter.append("file_targetDate", date);

            Document doc = collection.find(filter).first();

            ObjectMapper mapper = new ObjectMapper();
            if (doc != null) {
                return mapper.readValue(doc.toJson(), CrawlResult.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void insertOne(String errorMessage, String theaterCode, String targetDate){
        Document document = new Document();
        document.append("time", new Date());
        document.append("error", errorMessage);
        document.append("theater_code", theaterCode);
        document.append("target_date", targetDate);

        try(MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = db.getCollection("error_log");

            collection.insertOne(document);
        }
    }

}
