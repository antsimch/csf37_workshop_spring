package sg.edu.nus.iss.csf37_workshop.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.csf37_workshop.repositories.FeedsRepository;

@RestController
@RequestMapping(path = "/api")
public class FeedsController {

    private FeedsRepository feedsRepo;

    public FeedsController(FeedsRepository feedsRepo) {
        this.feedsRepo = feedsRepo;
    }
    
    @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFeed(
            @RequestPart MultipartFile picture, 
            @RequestPart String comments) throws IOException 
    {
        String id = feedsRepo.insertPostsIntoFeeds(picture, comments);
        
        JsonObject obj = Json.createObjectBuilder()
                .add("result", (id != null)? "success" : "failure")
                .build();

        return ResponseEntity.ok(obj.toString());
    }
}
