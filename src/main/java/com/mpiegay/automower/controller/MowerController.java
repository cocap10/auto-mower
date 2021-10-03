package com.mpiegay.automower.controller;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.InvalidInputException;
import com.mpiegay.automower.service.ActionService;
import com.mpiegay.automower.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/mower")
public class MowerController {

    public static final String NEW_LINE_ANY_OS = "\\r?\\n";

    @Autowired
    private ParserService parserService;

    @Autowired
    private ActionService actionService;

    @PostMapping("/runFromInput")
    public ResponseEntity<String> runFromInput(@RequestBody String input) {
        String result =  "";
        if (input == null || input.split(NEW_LINE_ANY_OS).length < 2) {
            throw new InvalidInputException("Invalid input file. The file must contain first the lawn dimention and at least one mower position and instructions sequence");
        }
        String[] inputLinesArray = input.split(NEW_LINE_ANY_OS);
        final LawnDimension lawnDimension = parserService.parseLawnDimension(inputLinesArray[0]);
        for (int lineNb = 1; lineNb+2 <= inputLinesArray.length;lineNb = lineNb+2) {
            MowerPosition mowerInitPosition = parserService.parseMowerInitPosition(inputLinesArray[lineNb]);
            List<StepAction> instructions = parserService.parseListInstructions(inputLinesArray[lineNb + 1]);
            MowerPosition mowerEndPosition = actionService.applyAllInstructions(lawnDimension, mowerInitPosition, instructions);
            result += String.format("%d %d %s\n", mowerEndPosition.getX(), mowerEndPosition.getY(), mowerEndPosition.getOrientation());
        }
        return ResponseEntity.ok(result);
    }

}
