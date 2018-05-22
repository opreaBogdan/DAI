package dai.entities;

import javax.persistence.*;

/**
 * Created by dragosbontea on 22/05/2018.
 */
@Entity
@Table(name = "commentary")
public class CommentaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "photo", nullable = false)
    private long photo;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "commentary", nullable = true)
    private String commentary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public long getPhoto() {
        return photo;
    }

    public void setPhoto(long photoId) {
        this.photo = photoId;
    }
}
