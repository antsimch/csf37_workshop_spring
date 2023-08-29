package sg.edu.nus.iss.csf37_workshop.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FeedsRepository {

    private static final String SQL_INSERT_POSTS_INTO_FEEDS = """
            insert into posts 
            (post_id, comments, picture)
            values (?, ?, ?)
            """;
    
    private JdbcTemplate template;

    public FeedsRepository(JdbcTemplate template) {
        this.template = template;
    }

    public String insertPostsIntoFeeds(
            MultipartFile picture, 
            String comments) throws IOException
    {
        String id = UUID.randomUUID().toString().substring(0, 8);

        InputStream fis = picture.getInputStream();     
        InputStream cis = new ByteArrayInputStream(comments.getBytes());

        Boolean insertSuccessful = template.update(
                SQL_INSERT_POSTS_INTO_FEEDS, id, cis, fis) > 0;

        fis.close();
        cis.close();

        if (insertSuccessful)
            return id;
        
        return null;
    }
}
