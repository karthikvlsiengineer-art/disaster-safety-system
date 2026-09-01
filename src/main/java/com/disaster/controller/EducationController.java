package com.disaster.controller;

import com.disaster.model.QuizQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EducationController {

    @GetMapping("/education")
    public String education(Model model) {

        List<QuizQuestion> questions =
                List.of(

                        new QuizQuestion(
                                "During a flood, where should you move?",
                                List.of(
                                        "Lower ground",
                                        "Higher ground",
                                        "River bank",
                                        "Under a bridge"
                                ),
                                1,
                                "Move to higher and safer ground during a flood."
                        ),

                        new QuizQuestion(
                                "What should you do during an earthquake?",
                                List.of(
                                        "Run near windows",
                                        "Use the lift",
                                        "Drop, Cover and Hold",
                                        "Stand outside immediately"
                                ),
                                2,
                                "Drop, Cover and Hold is a recommended earthquake safety action."
                        ),

                        new QuizQuestion(
                                "During a cyclone, what should you avoid?",
                                List.of(
                                        "Staying indoors",
                                        "Following official warnings",
                                        "Standing near windows",
                                        "Keeping emergency supplies"
                                ),
                                2,
                                "Stay away from windows during high winds."
                        )
                );

        model.addAttribute(
                "questions",
                questions
        );

        return "education";
    }
}
