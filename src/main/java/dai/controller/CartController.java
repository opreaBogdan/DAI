package dai.controller;

import dai.entities.TransactionEntity;
import dai.entities.Transaction_ImageEntity;
import dai.repository.TransactionEntityRepository;
import dai.repository.Transaction_ImageEntityRepository;
import dai.services.CartService;
import dai.mapper.Image;
import dai.entities.ImageEntity;
import dai.repository.ImageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dai.utils.Constants.ERROR;
import static dai.utils.Constants.SUCCESS;

@RestController
public class CartController {

    @Autowired
    private ImageEntityRepository imageEntityRepository;
    @Autowired
    private TransactionEntityRepository transactionEntityRepository;
    @Autowired
    private Transaction_ImageEntityRepository transactionImageEntityRepository;
    @Autowired
    private CartService cart;

    // the prettiest and most useless cache
    private Map<Long, ImageEntity> cachedImageRepository = new HashMap<>();

    @ResponseBody
    @RequestMapping(value = "/addToCart/{imageId}/{username}", method = RequestMethod.GET)
    public Map<String, String> addToCart(
            @PathVariable("imageId") long imageId, @PathVariable("username") String userName) {
        ImageEntity imageToBeAdded = cachedImageRepository.computeIfAbsent(imageId,
                id -> imageEntityRepository.getImage(id)
                        .stream()
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new)
        );

        String success = cart.addToCart(userName, imageToBeAdded);
        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS, success);
        if (!Boolean.parseBoolean(success)) {
            response.put(ERROR, "The image is already in the users' cart");
        } else {
//            imageEntityRepository.updatePictureAsBought(imageId, true);
            response.put(ERROR, "");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/removeFromCart/{imageId}/{username}", method = RequestMethod.GET)
    public Map<String, String> removeFromCart(
            @PathVariable("imageId") long imageId, @PathVariable("username") String userName) {
        ImageEntity imageToBeRemoved = cachedImageRepository.computeIfAbsent(imageId,
                id -> imageEntityRepository.getImage(id)
                        .stream()
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new)
        );

        String success = cart.removeFromCart(userName, imageToBeRemoved);
        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS, success);
        if (!Boolean.parseBoolean(success)) {
            response.put(ERROR, "The image is already in the users' cart");
        } else {
//            imageEntityRepository.updatePictureAsBought(imageId, false);
            response.put(ERROR, "");
        }
        return response;
    }


    @ResponseBody
    @RequestMapping(value = "/seeCart/{username}", method = RequestMethod.GET)
    public Map<String, Object> seeCart(@PathVariable("username") String userName) {

        Optional<Set<ImageEntity>> currentCart = cart.getCartPerUser(userName);
        Map<String, Object> response = new HashMap<>();
        if (currentCart.isPresent()) {
            response.put("cart",
                    currentCart.get()
                            .stream()
                            .map(Image::new)
                            .collect(Collectors.toList()));
            response.put(SUCCESS, "true");
            response.put(ERROR, "");
        } else {
            response.put(SUCCESS, "false");
            response.put(ERROR, "There are no images in the users cart");
        }

        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/buy/{username}", method = RequestMethod.GET)
    public Map<String, Object> buy(@PathVariable("username") String userName) {
        System.out.println("yeah");
        Optional<Set<ImageEntity>> currentCart = cart.getCartPerUser(userName);
        Map<String, Object> response = new HashMap<>();
        if (currentCart.isPresent()) {
            TransactionEntity transaction = transactionEntityRepository.save(new TransactionEntity(userName, System.currentTimeMillis()));
            for (ImageEntity image : currentCart.get()) {
                transactionImageEntityRepository.save(new Transaction_ImageEntity(image.getId(), transaction.getId()));
                cart.removeFromCart(userName, image);
            }
            response.put(SUCCESS, "true");
            response.put(ERROR, "");
        } else {
            response.put(SUCCESS, "false");
            response.put(ERROR, "There are no images in the users cart");
        }

        return response;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(@RequestParam Map<String,String> requestParams)  throws Exception{
        String userName = requestParams.get("userName");
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS, cart.removeUser(userName));
        response.put(ERROR, "");

        return response;
    }
}
