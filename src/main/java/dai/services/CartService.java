package dai.services;


import dai.entities.ImageEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CartService {

    Map<String, Set<ImageEntity>> cart = new HashMap<>();

    public String addToCart(String username, ImageEntity image) {
        Set<ImageEntity> imageEntities = cart.get(username);
        if(imageEntities == null) {
            imageEntities = new HashSet<>();
            imageEntities.add(image);
            cart.put(username, imageEntities);
            return "true";
        } else if(imageEntities.contains(image)) {
            return "false";
        } else {
            imageEntities.add(image);
            return "true";
        }
    }

    public String removeFromCart(String username, ImageEntity image) {
        Set<ImageEntity> imageEntities = cart.get(username);
        if(imageEntities == null) {
            return "false";
        } else if(imageEntities.contains(image)) {
            imageEntities.remove(image);
            return "true";
        } else {
            return "false";
        }
    }

    public int getPriceOfItemsPerUser(String username) {
        return cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(username))
                .flatMap(a -> a.getValue().stream())
                .mapToInt(ImageEntity::getPrice)
                .sum();
    }

    public Optional<Set<ImageEntity>> getCartPerUser(String username) {
        return cart.entrySet().stream()
                .filter(entry->entry.getKey().equals(username))
                .map(Map.Entry::getValue)
                .findAny();
    }
}
