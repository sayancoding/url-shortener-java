package com.arrowsmodule.urlshortener.controller;

import com.arrowsmodule.urlshortener.dto.RequestUrlDto;
import com.arrowsmodule.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<Object> getShortUrl(@RequestBody RequestUrlDto requestUrlDto){

        String res = urlService.generateShortUrl(requestUrlDto);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @GetMapping("/{url}")
    public ResponseEntity<Object> getShortUrl(@PathVariable String url){

        String res = urlService.getUrl(url);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
