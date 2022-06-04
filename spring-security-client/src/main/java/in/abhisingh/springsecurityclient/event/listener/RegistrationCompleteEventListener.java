package in.abhisingh.springsecurityclient.event.listener;

import in.abhisingh.springsecurityclient.entity.User;
import in.abhisingh.springsecurityclient.event.RegistrationCompleteEvent;
import in.abhisingh.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the Verification Token for the User with link.
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        // Send mail to user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        // Actual send email method over here.
        log.info("Click the link to verify your account: {}", url);
    }
}