package dai.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class Receive {


    @JsonProperty("file")
    public MultipartFile file;
    @JsonProperty("price")
    public int price;
    @JsonProperty("username")
    public String username;

    public Receive() {
    }

    public void setPrice(int perioada) {
        this.price = perioada;
    }

    public int getPrice() {
        return this.price;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
