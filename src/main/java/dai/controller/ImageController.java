package dai.controller;

import dai.mapper.Image;
import dai.mapper.Receive;
import dai.entities.ImageEntity;
import dai.repository.ImageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static dai.utils.Constants.*;

@RestController
public class ImageController {

    @Autowired
    private ImageEntityRepository imageEntityRepository;

    @ResponseBody
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST ,consumes = {"application/x-www-form-urlencoded","multipart/form-data"})
    public Map<String, String> uploadFile(@ModelAttribute Receive response) throws Exception {
        if(response.getFile() == null) {
            throw new IllegalArgumentException("File not found");
        }
        int price = response.getPrice();
        String username = response.getUsername();

        String fileName = rs.nextString();
        BufferedWriter writeFile = new BufferedWriter(new FileWriter(new File(fileName)));
        BufferedReader readFile = new BufferedReader(new InputStreamReader(response.getFile().getInputStream(), "UTF-8"));

        String line;
        while ((line = readFile.readLine()) != null) {
            writeFile.write(line + "\n");
        }

        imageEntityRepository.save(new ImageEntity(username, fileName, price));

        Map<String, String> result = new HashMap<>();
        result.put("success","true");
        result.put("error","");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    public Map<String, Object> getPictures(@RequestParam Map<String, String> requestParams) {
        List<ImageEntity> imagesForUser = Collections.emptyList();

        String success = "true";
        String error = "";
        if(!requestParams.isEmpty()) {
            int minPrice = Integer.parseInt(requestParams.get("minPrice"));
            int maxPrice = Integer.parseInt(requestParams.get("maxPrice"));
            String author = requestParams.get("author");


            try {
                imagesForUser = imageEntityRepository
                        .getImagesForUser(author, minPrice, maxPrice);
            } catch (Exception e) {
                success = "false";
                error = e.getMessage();
            }
        } else {
            imagesForUser = imageEntityRepository.getAllImages();
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(SUCCESS, success);
        responseMap.put(ERROR, error);

        if (success.equalsIgnoreCase("true"))
            responseMap.put("images",
                    imagesForUser
                            .stream()
                            .map(i -> {
                                Image.Builder builder = new Image.Builder(i.getId());
                                return builder
                                        .withLocation(i.getLocation())
                                        .withPrice(i.getPrice())
                                        .build();
                            })
                            .collect(Collectors.toList()));
        return responseMap;
    }

    @ResponseBody
    @RequestMapping(value = "/picturesDetails", method = RequestMethod.GET)
    public Map<String, Object> getPictureDetails(@RequestParam long id) {
        String success = "true";
        String error = "";
        Map<String, Object> responseMap = new HashMap<>();

        Optional<ImageEntity> image = Optional.empty();
        try {
            image = imageEntityRepository.getImage(id).stream().findFirst();
        } catch (Exception e) {
            error = e.getMessage();
            success = "false";
        }

        if (image.isPresent()) {
            ImageEntity i = image.get();
            responseMap.put(PICTURE_ID, i.getId());
            responseMap.put(PICTURE_LOCATION, i.getLocation());
            responseMap.put(AUTHOR, i.getUsername());
            responseMap.put(PRICE, i.getPrice());
        } else {
            success = "false";
            error = "No image with id:" + id + " was found";
        }
        responseMap.put(SUCCESS, success);
        responseMap.put(ERROR, error);
        return responseMap;
    }
}
