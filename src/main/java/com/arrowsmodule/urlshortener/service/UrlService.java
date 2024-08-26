package com.arrowsmodule.urlshortener.service;

import com.arrowsmodule.urlshortener.dao.UrlDao;
import com.arrowsmodule.urlshortener.dto.RequestUrlDto;
import com.arrowsmodule.urlshortener.entity.Url;
import com.arrowsmodule.urlshortener.exception.InvalidURLException;
import com.arrowsmodule.urlshortener.exception.NotFoundException;
import com.arrowsmodule.urlshortener.util.Base62;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static com.arrowsmodule.urlshortener.config.RedisConfig.ENCODING_KEY;

@Service
public class UrlService {
    @Autowired
    private UrlDao urlDao;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${url.base}")
    private String BASE_URL;

    @PostConstruct
    public void init(){
        redisTemplate.opsForValue().set(ENCODING_KEY, 1000000L);
    }

    public String generateShortUrl(RequestUrlDto requestUrlDto){
        int length = 5;
        StringBuilder sb = new StringBuilder();

        String originalUrl = requestUrlDto.getUrl();

        try{
            URL url = new URL(originalUrl);
            if (!(url.getProtocol().equals("http") || url.getProtocol().equals("https")))
                throw new InvalidURLException("Invalid Url is given!!");
        }
        catch (MalformedURLException e) {
            throw new InvalidURLException("Invalid Url is given!!");
        }

        Long encoding_key = redisTemplate.opsForValue().increment(ENCODING_KEY);
        String encoding_value = Base62.encode(BigInteger.valueOf(encoding_key));

        while (sb.length() < length - encoding_value.length()) {
            sb.append('0');
        }

        String shortUrl = sb + encoding_value;

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlDao.save(url);

        return "New url is generated -> " + BASE_URL + shortUrl;
    }
    public String getUrl(String shortUrl){

        Url url = urlDao.findByShortUrl(shortUrl)
                .orElseThrow(()-> new NotFoundException("No such url is found!!"));

        return url.getOriginalUrl();
    }
}
