package marxbank.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Tester oppkobling til vue frontend

@RestController
@RequestMapping("/messages")
public class MessageController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Vue frontenden kommuniserer nå med backend. Yay";
    }
}
