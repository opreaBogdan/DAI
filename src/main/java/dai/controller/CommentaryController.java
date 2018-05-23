package dai.controller;

import dai.entities.CommentaryEntity;
import dai.entities.ImageEntity;
import dai.mapper.Commentary;
import dai.repository.CommentaryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dragosbontea on 22/05/2018.
 */
@RestController
public class CommentaryController {

    @Autowired
    CommentaryEntityRepository commentaryEntityRepository;

    @ResponseBody
    @RequestMapping(path = "commentary",  method = RequestMethod.GET)
    public Map<String, Object> getCommentariesForPhoto(@NotNull long photoId) {

        List<CommentaryEntity> commentaries = commentaryEntityRepository.getImagesForUser(photoId);

        Map<String, Object> response = new HashMap();
        String success = "false";
        String error = "no commentaries for picture with id: " + photoId;
        if(!commentaries.isEmpty()) {
            success = "true";
            error = "";
            response.put("commentaryList",
                    commentaries.stream()
                        .map(Commentary::new)
                        .collect(Collectors.toList()));
        }
        response.put("success", success);
        response.put("error", error);
        return response;
    }

    @ResponseBody
    @RequestMapping(path = "newCommentary/{photoId}/{authorName}", method = RequestMethod.POST)
    public Map<String, String> putCommentaryForPhoto(
            @NotNull @PathVariable("photoId") long photoId,
            @NotNull @PathVariable("authorName") String authorName,
            @RequestBody String commentary) {
        String comm_final = commentary.replaceAll("\\+"," ");
        String comm_final2 = comm_final.replaceAll("=","");
        String success = "true";
        String error = "";
        try {
            commentaryEntityRepository.insertCommentaryToPicture(photoId, authorName, comm_final2);
        }catch (Exception e) {
            success = "false";
            error = e.getMessage();
        }
        Map<String, String> response = new HashMap<>();
        response.put("success", success);
        response.put("error", error);
        return response;
    }
}
