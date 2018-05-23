package dai.controller;

import dai.entities.ImageEntity;
import dai.entities.TransactionEntity;
import dai.entities.Transaction_ImageEntity;
import dai.entities.UserEntity;
import dai.mapper.Image;
import dai.repository.ImageEntityRepository;
import dai.repository.TransactionEntityRepository;
import dai.repository.Transaction_ImageEntityRepository;
import dai.repository.UserEntityRepository;
import dai.utils.MailSendingThread;
import dai.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.stream.Collectors;

import static dai.utils.Constants.*;

@RestController
class UserController {
    String reset_pass = null;
    @Autowired
    private JavaMailSender sender;

    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    TransactionEntityRepository transactionEntityRepository;
    @Autowired
    ImageEntityRepository imageEntityRepository;
    @Autowired
    private Transaction_ImageEntityRepository transactionImageEntityRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Map<String, String> login(@RequestParam Map<String,String> requestParams) throws Exception{
            String username = requestParams.get("username");
        String password = requestParams.get("password");

        List<UserEntity> users = userEntityRepository.findUser(username, password);
        String error = null;
        if (users == null || users.isEmpty())
            error = "Username of password are not correct!";
        else if (!users.get(0).isValidated())
            error =  "The account is not yet validated";

        Map <String, String> response = new HashMap<>();
        if (error == null) {
            response.put(SUCCESS, "true");
            response.put(ERROR, "");
            response.put("type", users.get(0).getType());
        } else {
            response.put(SUCCESS, "false");
            response.put(ERROR, error);
            response.put("type", "");
        }
        return response;
    }

    @RequestMapping(value = "/recover", method = RequestMethod.GET)
    public Map<String, String> recover(@RequestParam Map<String,String> requestParams) throws Exception{
        String email = requestParams.get("email");
        reset_pass = requestParams.get("password");

        RandomString rs = new RandomString();
        String token = rs.nextString();

        int correct = userEntityRepository.updateTokenForRecover(email, token);
        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put(SUCCESS,"false");
            response.put(ERROR,"The email you entered is not correct!");
            return response;
        }

        MailSendingThread mailThread = new MailSendingThread(sender, email,  "Recover password", "To set a new passowrd please visit: http://localhost:8080/reset?token=" + token + " !");
        mailThread.run();

        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS,"true");
        response.put(ERROR,"");
        return response;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public RedirectView reset(@RequestParam Map<String,String> requestParams) throws Exception{
        String token = requestParams.get("token");

        int correct = userEntityRepository.updatePasswordByToken(token, reset_pass);
        reset_pass = null;
        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put("success","false");
            response.put("error","The token is not correct!");
            return new RedirectView("/token_not_valid.html", true);
        }

        Map<String, String> response = new HashMap<>();
        response.put("success","true");
        response.put("error","");
        return new RedirectView("/", true);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public Map<String, String> register(@RequestParam Map<String,String> requestParams) throws Exception{
        String username = requestParams.get("username");
        String password = requestParams.get("password");
        String type = requestParams.get("type");
        String email = requestParams.get("email");

        List<UserEntity> users = userEntityRepository.findUserRegister(username, email);
        if (users != null && users.size() != 0) {
            Map<String, String> response = new HashMap<>();
            response.put(SUCCESS,"false");
            response.put(ERROR,"Username or email already in use!");
            System.err.println("TADAAAA");
            return response;
        }

        String token = rs.nextString();

        userEntityRepository.save(new UserEntity(username, password, type, email, token));
        MailSendingThread mailThread = new MailSendingThread(sender, email,  "Welcome!", "We are glad you want to be part of this family! Please visit: http://localhost:8080/validate?token=" + token + " to validate the account!");
        mailThread.run();

        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS,"true");
        response.put(ERROR,"");
        return response;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public RedirectView validate(@RequestParam Map<String,String> requestParams) throws Exception{
        String token = requestParams.get("token");

        int correct = userEntityRepository.validate(token);
        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put(SUCCESS,"false");
            response.put(ERROR,"The token is not correct!");
            return new RedirectView("/token_not_valid.html", true);
        }
        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS,"true");
        response.put(ERROR,"");
        return new RedirectView("/", true);
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET)
    public Map<String, Object> getHistory(@RequestParam Map<String,String> requestParams)  throws Exception{
        String userName = requestParams.get("username");
        List<TransactionEntity> transactions = transactionEntityRepository.getTransactionsForUser(userName);

        if (transactions == null || transactions.size() == 0) {
            Map<String, Object> response = new HashMap<>();
            response.put(SUCCESS,"false");
            response.put(ERROR,"No image bought");
            return response;
        }

        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS,"true");
        response.put(ERROR,"");
        List transactions_list = new ArrayList();
        Calendar calendar = Calendar.getInstance();

        for (TransactionEntity transaction : transactions) {
            List<Transaction_ImageEntity> image_ids = transactionImageEntityRepository.getImagesForTransaction(transaction.getId());
            Set<ImageEntity> images = new HashSet<>();
            int price = 0;
            System.out.println(image_ids.size());
            for (Transaction_ImageEntity image_id : image_ids) {
                ImageEntity image = imageEntityRepository.findOne(image_id.getImage_id());
                price += image.getPrice();
            }
            calendar.setTimeInMillis(transaction.getTime());

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            Map<String, Object> transaction_map = new HashMap<>();
            transaction_map.put("time", "" + mYear + "-" + mMonth + "-" + mDay);
            transaction_map.put("quantity", image_ids.size());
            transaction_map.put("price", price);
            transactions_list.add(transaction_map);
        }
        response.put(TRANSACTIONS, transactions_list);

        return response;
    }
}