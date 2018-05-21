package dai.controller;

import dai.entities.ImageEntity;
import dai.entities.TransactionEntity;
import dai.entities.UserEntity;
import dai.repository.TransactionEntityRepository;
import dai.repository.UserEntityRepository;
import dai.utils.MailSendingThread;
import dai.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import static dai.utils.Constants.ERROR;
import static dai.utils.Constants.SUCCESS;
import static dai.utils.Constants.rs;

@RestController
class UserController {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    TransactionEntityRepository transactionEntityRepository;

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
    public Map<String, String> reset(@RequestParam Map<String,String> requestParams) throws Exception{
        String token = requestParams.get("token");
        String passowrd = requestParams.get("password");

        int correct = userEntityRepository.updatePasswordByToken(token, passowrd);

        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put(SUCCESS,"false");
            response.put(ERROR,"The token is not correct!");
            return response;
        }
        Map<String, String> response = new HashMap<>();
        response.put(SUCCESS,"true");
        response.put(ERROR,"");
        return response;
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

    @ResponseBody
    @RequestMapping(value = "/buy/{username}", method = RequestMethod.GET)
    public Map<String, Object> buy(@PathVariable("username") String userName) {

        List<TransactionEntity> transactions = transactionEntityRepository.getTransactionsForUser(userName);

        // TODO
        Map<String, Object> response = new HashMap<>();
        final String[] command = {""};
//        currentCart.get().stream().map(x -> command[0] = command[0] + x.getId() + ";");
        transactionEntityRepository.save(new TransactionEntity(userName, command[0], System.currentTimeMillis()));
        response.put(SUCCESS, "true");
        response.put(ERROR, "");

        return response;
    }
}