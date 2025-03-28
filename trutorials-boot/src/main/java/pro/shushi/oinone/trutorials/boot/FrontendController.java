package pro.shushi.oinone.trutorials.boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FrontendController {

    @RequestMapping("/")
    public ModelAndView frontend() {
        return new ModelAndView("forward:/index.html");
    }
}