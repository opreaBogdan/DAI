package dai.controller;

import dai.entities.UserEntity;
import dai.repository.UserEntityRepository;
import dai.utils.MailSendingThread;
import dai.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static dai.utils.Constants.rs;

@RestController
class UserController {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    UserEntityRepository userEntityRepository;

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
            response.put("success", "true");
            response.put("error", "");
            response.put("type", users.get(0).getType());
        } else {
            response.put("success", "false");
            response.put("error", error);
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
            response.put("success","false");
            response.put("error","The email you entered is not correct!");
            return response;
        }

        MailSendingThread mailThread = new MailSendingThread(sender, email,  "Recover password", "To set a new passowrd please visit: http://localhost:8080/reset?token=" + token + " !");
        mailThread.run();

        Map<String, String> response = new HashMap<>();
        response.put("success","true");
        response.put("error","");
        return response;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public Map<String, String> reset(@RequestParam Map<String,String> requestParams) throws Exception{
        String token = requestParams.get("token");
        String passowrd = requestParams.get("password");

        int correct = userEntityRepository.updatePasswordByToken(token, passowrd);

        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put("success","false");
            response.put("error","The token is not correct!");
            return response;
        }
        Map<String, String> response = new HashMap<>();
        response.put("success","true");
        response.put("error","");
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
            response.put("success","false");
            response.put("error","Username or email already in use!");
            return response;
        }

        String token = rs.nextString();

        userEntityRepository.save(new UserEntity(username, password, type, email, token));
        MailSendingThread mailThread = new MailSendingThread(sender, email,  "Welcome!", "We are glad you want to be part of this family! Please visit: http://localhost:8080/validate?token=" + token + " to validate the account!");
        mailThread.run();

        Map<String, String> response = new HashMap<>();
        response.put("success","true");
        response.put("error","");
        return response;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public Map<String, String> validate(@RequestParam Map<String,String> requestParams) throws Exception{
        String token = requestParams.get("token");

        int correct = userEntityRepository.validate(token);
        if (correct != 1) {
            Map<String, String> response = new HashMap<>();
            response.put("success","false");
            response.put("error","The token is not correct!");
            return response;
        }
        Map<String, String> response = new HashMap<>();
        response.put("success","true");
        response.put("error","");
        return response;
    }


}