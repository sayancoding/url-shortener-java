package com.arrowsmodule.urlshortener.dao;

import com.arrowsmodule.urlshortener.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlDao extends MongoRepository<Url,Long> {
    public Optional<Url> findByShortUrl(String shortUrl);
}
