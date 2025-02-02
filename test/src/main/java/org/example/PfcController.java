package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PfcController {
    @PostMapping("/PFC")
    public ModelAndView calculatePFC(String muscle, BmrDTO result) {
        double intCarbohydrate;
        double intFat;
        double intProtein;

        if ("gain".equalsIgnoreCase(muscle)) {
            // P30％・ F30％・ C40％
            intCarbohydrate = (result.getTdee() * 0.4) / 4;
            intFat = (result.getTdee() * 0.3) / 9;
            intProtein = (result.getTdee() * 0.3) / 4;
        } else {
            // P30％・ F10％・ C60％
            intCarbohydrate = (result.getTdee() * 0.6) / 4;
            intFat = (result.getTdee() * 0.1) / 9;
            intProtein = (result.getTdee() * 0.3) / 4;
        }

        int carbohydrate = (int) intCarbohydrate;
        int fat = (int) intFat;
        int protein = (int) intProtein;

        ModelAndView modelAndView = new ModelAndView("PFCresult");
        modelAndView.addObject("carbohydrateValue", carbohydrate);
        modelAndView.addObject("fatValue", fat);
        modelAndView.addObject("proteinValue", protein);

        return modelAndView;
    }
}

