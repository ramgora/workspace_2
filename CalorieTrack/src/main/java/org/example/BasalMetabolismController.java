package org.example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@Controller
public class BasalMetabolismController {

    private final PfcController pfcController;

    public BasalMetabolismController(PfcController pfcController) {

        this.pfcController = pfcController;
    }

    @GetMapping("/InputSex")
    public String inputForm() {
        return "sex";//性別入力フォーム
    }

    @PostMapping("/InputSex")
    public String showDetailsInput(@RequestParam("sex") String sex, Model model) {
        model.addAttribute("sex", sex);
        return "HealthDetailsForm"; //身長・体重・年齢の入力フォーム
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
        return "activityLevelForm"; //生活活動強度を選択するフォーム
    }

    @PostMapping("/PFCForm")
    public String showMuscleSelectionForm(
            @RequestParam("sex") String sex,
            @RequestParam("weight") double weight,
            @RequestParam("height") double height,
            @RequestParam("age") int age,
            @RequestParam("activityLevel") double activityLevel,
            Model model) {
        model.addAttribute("sex", sex);
        model.addAttribute("weight", weight);
        model.addAttribute("height", height);
        model.addAttribute("age", age);
        model.addAttribute("activityLevel", activityLevel);
        return "PFCForm";  //PFCForm.htmlのやつ
    }

    @PostMapping("/BasalMetabolism")
    public ModelAndView calculateBMR(
            @RequestParam("sex") String sex,
            @RequestParam("weight") double weight,
            @RequestParam("height") double height,
            @RequestParam("age") int age,
            @RequestParam("activityLevel") double activityLevel,
            @RequestParam("muscle") String muscle,
            Model model
    ) {
        System.out.println("受け取った muscle の値: " + muscle);

        double intBmr;
        if ("man".equalsIgnoreCase(sex)) {
            intBmr = 13.397 * weight + 4.799 * height - 5.677 * age + 88.362;
        } else {
            intBmr = 9.247 * weight + 3.098 * height - 4.33 * age + 447.593;
        }

        int bmr = (int) intBmr;
        double intTdee = intBmr * activityLevel;
        int tdee = (int) intTdee;

        model.addAttribute("bmrValue", bmr);
        model.addAttribute("tdeeValue", tdee);

        System.out.println("基礎代謝" + bmr);
        System.out.println("消費カロリー" + tdee);

        BmrDTO result = new BmrDTO(bmr, tdee);
        return pfcController.calculatePFC(muscle, result);
    }
}