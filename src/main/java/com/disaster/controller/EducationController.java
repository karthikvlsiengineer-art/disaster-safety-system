
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

        List<QuizQuestion> questions = List.of(

            // FLOOD SAFETY

            new QuizQuestion(
                "During a flood, where should you move?",
                List.of("Lower ground", "Higher ground",
                        "River bank", "Under a bridge"),
                1,
                "Move to higher and safer ground."
            ),

            new QuizQuestion(
                "What should you avoid during flooding?",
                List.of("Official warnings", "Emergency kits",
                        "Walking through floodwater", "Evacuation routes"),
                2,
                "Floodwater can contain dangerous currents and hazards."
            ),

            new QuizQuestion(
                "What should you do if officials order evacuation?",
                List.of("Ignore the warning", "Wait until night",
                        "Follow the evacuation instructions", "Visit the river"),
                2,
                "Follow official evacuation instructions promptly."
            ),

            new QuizQuestion(
                "Why is driving through floodwater dangerous?",
                List.of("Roads become wider", "Vehicles can be swept away",
                        "Fuel becomes stronger", "Visibility always improves"),
                1,
                "Moving floodwater can carry vehicles away."
            ),

            new QuizQuestion(
                "When is it safe to return after a flood evacuation?",
                List.of("When rain becomes lighter", "Whenever you want",
                        "When authorities declare it safe", "Immediately after sunset"),
                2,
                "Return only after official clearance."
            ),

            // EARTHQUAKE SAFETY

            new QuizQuestion(
                "What should you do during an earthquake indoors?",
                List.of("Use the lift", "Run near windows",
                        "Drop, Cover and Hold On", "Stand on a balcony"),
                2,
                "Drop, Cover and Hold On protects against falling objects."
            ),

            new QuizQuestion(
                "Which place should you avoid during shaking?",
                List.of("Under sturdy furniture", "Near glass windows",
                        "Away from shelves", "A protected interior area"),
                1,
                "Glass can shatter during an earthquake."
            ),

            new QuizQuestion(
                "What should you expect after a major earthquake?",
                List.of("Aftershocks", "No further shaking",
                        "Instant restoration of utilities", "Guaranteed safe buildings"),
                0,
                "Aftershocks may follow the main earthquake."
            ),

            new QuizQuestion(
                "If you are outside during an earthquake, where should you stay?",
                List.of("Near power lines", "Beside buildings",
                        "In an open area away from hazards", "Under trees"),
                2,
                "Stay away from buildings, trees and power lines."
            ),

            new QuizQuestion(
                "What should you do if you notice a damaged building after an earthquake?",
                List.of("Enter to inspect it", "Avoid entering it",
                        "Use its lift", "Stand near falling debris"),
                1,
                "Damaged structures may collapse during aftershocks."
            ),

            // CYCLONE SAFETY

            new QuizQuestion(
                "Where should you stay during a cyclone?",
                List.of("Near windows", "On the roof",
                        "Inside a strong building", "Under a roadside tree"),
                2,
                "Stay in a safe, sturdy building away from windows."
            ),

            new QuizQuestion(
                "What should you monitor during a cyclone?",
                List.of("Official weather alerts", "Unverified rumours",
                        "Entertainment advertisements", "Old forecasts only"),
                0,
                "Official weather alerts provide important safety instructions."
            ),

            new QuizQuestion(
                "What should you avoid during strong cyclone winds?",
                List.of("Emergency supplies", "Staying indoors",
                        "Standing near windows", "Following warnings"),
                2,
                "Flying debris can break windows and cause injuries."
            ),

            new QuizQuestion(
                "What is storm surge?",
                List.of("A sudden earthquake", "Abnormal rise of seawater",
                        "A forest fire", "A heatwave"),
                1,
                "Storm surge is an abnormal rise in sea level caused by a storm."
            ),

            new QuizQuestion(
                "What should you do if authorities issue a cyclone evacuation order?",
                List.of("Go to the beach", "Ignore the order",
                        "Move to the designated safe shelter", "Wait outdoors"),
                2,
                "Follow official evacuation orders and use designated shelters."
            ),

            // FIRE SAFETY

            new QuizQuestion(
                "What should you do if your clothes catch fire?",
                List.of("Run quickly", "Stop, Drop and Roll",
                        "Use a lift", "Hide in a cupboard"),
                1,
                "Stop, Drop and Roll helps extinguish burning clothing."
            ),

            new QuizQuestion(
                "What should you do when escaping through a smoky area?",
                List.of("Stay low", "Stand upright",
                        "Run toward smoke", "Open every door"),
                0,
                "Smoke and hot gases rise, so staying low may reduce exposure."
            ),

            new QuizQuestion(
                "What should you avoid during a building fire?",
                List.of("Using safe exits", "Alerting others",
                        "Using a lift", "Calling emergency services"),
                2,
                "Lifts can become dangerous during fires."
            ),

            new QuizQuestion(
                "Which extinguisher is generally suitable for energized electrical equipment?",
                List.of("Water extinguisher", "Foam extinguisher",
                        "CO2 extinguisher", "Water bucket"),
                2,
                "A correctly rated CO2 extinguisher can be used on energized electrical equipment."
            ),

            new QuizQuestion(
                "What should you do if a closed door feels hot during a fire?",
                List.of("Open it immediately", "Avoid opening it",
                        "Break it with your hands", "Stand against it"),
                1,
                "A hot door may indicate fire on the other side."
            ),

            // FIRST AID

            new QuizQuestion(
                "What should you do first before helping an injured person?",
                List.of("Check scene safety", "Give food",
                        "Move them immediately", "Take photographs"),
                0,
                "Ensure the scene is safe before approaching."
            ),

            new QuizQuestion(
                "What is the recommended initial action for severe external bleeding?",
                List.of("Apply firm direct pressure", "Wash repeatedly",
                        "Ignore the bleeding", "Apply ice only"),
                0,
                "Apply firm direct pressure and seek emergency assistance."
            ),

            new QuizQuestion(
                "What should you do for a minor thermal burn?",
                List.of("Apply toothpaste", "Apply butter",
                        "Cool under running water for 20 minutes", "Rub the burn"),
                2,
                "Cool the burn with cool running water for 20 minutes."
            ),

            new QuizQuestion(
                "What should you do if someone is unconscious and not breathing normally?",
                List.of("Give them water", "Wait for them to wake up",
                        "Call emergency services and start CPR",
                        "Make them stand"),
                2,
                "Call for emergency help, obtain an AED if available, and begin CPR."
            ),

            new QuizQuestion(
                "What should you do if you suspect a spinal injury?",
                List.of("Twist the neck", "Avoid unnecessary movement",
                        "Make the person walk", "Sit them upright"),
                1,
                "Avoid unnecessary movement unless required for immediate safety."
            ),

            // DISASTER PREPAREDNESS

            new QuizQuestion(
                "What should an emergency kit contain?",
                List.of("Only toys", "Water, food, torch and first aid supplies",
                        "Only books", "Only electronic games"),
                1,
                "Emergency kits should contain essential survival supplies."
            ),

            new QuizQuestion(
                "What is India's unified emergency response number?",
                List.of("1000", "112", "555", "9999"),
                1,
                "112 is India's unified emergency response number."
            ),

            new QuizQuestion(
                "Why should families prepare an evacuation plan?",
                List.of("To increase confusion", "To avoid all warnings",
                        "To identify safe exits and meeting places",
                        "To delay evacuation"),
                2,
                "A family evacuation plan helps everyone know where to go."
            ),

            new QuizQuestion(
                "Which information source is most reliable during a disaster?",
                List.of("Unverified social media posts",
                        "Rumours from strangers",
                        "Official government and emergency alerts",
                        "Old forwarded messages"),
                2,
                "Use verified information from official authorities."
            ),

            new QuizQuestion(
                "What should you do during a disaster preparedness drill?",
                List.of("Ignore instructions", "Practise the safety procedure",
                        "Block emergency exits", "Spread rumours"),
                1,
                "Drills help people practise safe emergency responses."
            )
        );

        model.addAttribute("questions", questions);

        return "education";
    }
}
