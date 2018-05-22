package dai.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import dai.entities.CommentaryEntity;

/**
 * Created by dragosbontea on 22/05/2018.
 */
public class Commentary {

    @JsonProperty("id")
    public long id;

    @JsonProperty("photoId")
    public long photoId;

    @JsonProperty("author")
    public String author;

    @JsonProperty("commentary")
    public String commentary;

    public Commentary() {
    }

    public Commentary(CommentaryEntity commentaryEntity) {
        this.id = commentaryEntity.getId();
        this.photoId = commentaryEntity.getPhoto();
        this.author = commentaryEntity.getAuthor();
        this.commentary = commentaryEntity.getCommentary();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authorName) {
        this.author = authorName;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
