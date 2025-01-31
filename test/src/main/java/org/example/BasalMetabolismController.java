package org.example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class BasalMetabolismController {

    @GetMapping("/InputSex")
    public String inputForm() {
        return "sex"; // 性別入力フォームのテンプレート
    }

    @PostMapping("/InputSex")
    public String showDetailsInput(@RequestParam("sex") String sex, Model model) {
        model.addAttribute("sex", sex);
        return "HealthDetailsForm"; // 身長・体重・年齢の入力フォーム
    }

    @PostMapping("/HealthDetailsForm")
    public String showActivityLevelForm(
            @RequestParam("sex") String sex,
            @RequestParam("weight") double weight,
            @RequestParam("height") double height,
            @RequestParam("age") int age,
            Model model) {
        model.addAttribute("sex", sex);
        model.addAttribute("weight", weight);
        model.addAttribute("height", height);
        model.addAttribute("age", age);
        return "activityLevelForm"; // 活動強度を選択するフォーム
    }

    @PostMapping("/BasalMetabolism")
    public ModelAndView calculateBMR(
            @RequestParam("muscle") String muscle,
            @RequestParam("sex") String sex,
            @RequestParam("weight") double weight,
            @RequestParam("height") double height,
            @RequestParam("age") int age,
            @RequestParam("activityLevel") double activityLevel
    ) {

        double intBmr;

        if ("man".equalsIgnoreCase(sex)) {
            intBmr = 13.397 * weight + 4.799 * height - 5.677 * age + 88.362;
        } else {
            intBmr = 9.247 * weight + 3.098 * height - 4.33 * age + 447.593;
        }
        int bmr = (int)intBmr;

        double intTdee = intBmr * activityLevel;
        int tdee = (int)intTdee;

    double carbohydrate;
    double fat;
    double protein;

        if ("gain".equalsIgnoreCase(muscle)) {
            //P30％・ F30％・ C40％
            carbohydrate = (int) (tdee * 0.4)/4;
            fat = (int)(tdee * 0.3)/9;
            protein = (int)(tdee * 0.3)/4;
        } else {
            //P30％・ F10％・ C60％
            carbohydrate = (int) (tdee * 0.6)/4;
            fat = (int)(tdee * 0.1)/9;
            protein = (int)(tdee * 0.3)/4;
        }


        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("bmrValue", bmr);
        modelAndView.addObject("tdeeValue", tdee);
        modelAndView.addObject("carbohydrateValue", carbohydrate);
        modelAndView.addObject("fatValue", fat);
        modelAndView.addObject("proteinValue", protein);
        return modelAndView;
    }
}

