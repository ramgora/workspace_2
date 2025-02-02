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
/*@GetMapping("/PFCForm")
    public String showMuscleSelectionForm() {
        return "PFCForm";  // PFCForm.html を表示
    }*/

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
        return "PFCForm";  // PFCForm.html を表示
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
        if (muscle == null || muscle.isEmpty()) {
            throw new IllegalArgumentException("エラー: muscle パラメータが送信されていません！");
        }
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

        System.out.println("受け取った muscle の値: " + bmr);
        System.out.println("受け取った muscle の値: " + tdee);

        //TODO　PFCフォームに値を渡すこと。

        BmrDTO result = new BmrDTO(bmr, tdee);
        return pfcController.calculatePFC(muscle, result);




    }
}


/*@PostMapping("")
public ModelAndView pfcController(
        @RequestParam("muscle") String muscle,
        @ModelAttribute BmrDTO result
) {

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
}*/