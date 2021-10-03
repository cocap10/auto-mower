package com.mpiegay.automower.service;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.StepAction;

import java.util.List;

/**
 * This service provides methodes to parse input files for auto-mower program
 */
public interface ParserService {
    LawnDimension parseLawnDimension(String lawnDimensionStr);

    MowerPosition parseMowerInitPosition(String mowerInitPositionStr);

    List<StepAction> parseListInstructions(String stepsInstructions);

}
