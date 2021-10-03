package com.mpiegay.automower.service.impl;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.Orientation;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.InvalidInputException;
import com.mpiegay.automower.service.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParserServiceImpl implements ParserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserServiceImpl.class);
    private static final String SPACE_CHAR = " ";
    private static final String INITIAL_MOWER_POSITION = "initial mower position";
    private static final String LAWN_DIMENSION = "lawn dimension";

    @Override
    public LawnDimension parseLawnDimension(String lawnDimensionStr) {
        if (lawnDimensionStr == null || lawnDimensionStr.trim().split(SPACE_CHAR).length != 2) {
            this.throwInvalidInputExceptionForLawnDimension(lawnDimensionStr);
        }

        String[] coordinate = lawnDimensionStr.trim().split(SPACE_CHAR);
        if (coordinate.length != 2) {
            this.throwInvalidInputExceptionForLawnDimension(lawnDimensionStr);
        }

        int x = 0;
        int y = 0;
        try {
            x = Integer.parseInt(coordinate[0]);
            y = Integer.parseInt(coordinate[1]);
        } catch (Exception e) {
            LOGGER.warn("Something went wrong while parsing " + INITIAL_MOWER_POSITION, e);
            this.throwInvalidInputExceptionForLawnDimension(lawnDimensionStr);
        }

        if (x <= 0 || y <= 0) {
            this.throwInvalidInputExceptionForLawnDimension(lawnDimensionStr);
        }
        return new LawnDimension(x, y);
    }

    private void throwInvalidInputExceptionForLawnDimension(String invalidInput) {
        String explanation = "Expected 'x y' with x and y two not null positive integers";
        throw new InvalidInputException(invalidInput, explanation, LAWN_DIMENSION);
    }

    @Override
    public MowerPosition parseMowerInitPosition(String mowerInitPositionStr) {
        if (mowerInitPositionStr == null || mowerInitPositionStr.trim().split(SPACE_CHAR).length != 3) {
            throwInvalidInputExceptionForInitPosition(mowerInitPositionStr);
        }
        String[] coordiateAndOrientationStr = mowerInitPositionStr.trim().split(SPACE_CHAR);
        int x = 0;
        int y = 0;
        Orientation orientation = null;
        try {
            x = Integer.parseInt(coordiateAndOrientationStr[0]);
            y = Integer.parseInt(coordiateAndOrientationStr[1]);
            orientation = Orientation.valueOf(coordiateAndOrientationStr[2]);
        } catch (Exception e) {
            LOGGER.warn("Something went wrong while parsing " + INITIAL_MOWER_POSITION, e);
            throwInvalidInputExceptionForInitPosition(mowerInitPositionStr);
        }

        return new MowerPosition(x, y, orientation);
    }

    private void throwInvalidInputExceptionForInitPosition(String invalidInput) {
        String explanation = "Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character";
        throw new InvalidInputException(invalidInput, explanation, INITIAL_MOWER_POSITION);
    }

    @Override
    public List<StepAction> parseListInstructions(String stepsInstructions) {
        if (stepsInstructions == null || stepsInstructions.length() == 0) {
            throwInvalidInputExceptionForListInstructions(stepsInstructions);
        }
        List<StepAction> instructions = null;
        try {
            instructions = stepsInstructions.strip().chars()
                    .mapToObj(c -> StepAction.valueOf(String.valueOf((char) c)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.warn("Something went wrong while parsing " + INITIAL_MOWER_POSITION, e);
            throwInvalidInputExceptionForListInstructions(stepsInstructions);
        }

        return instructions;
    }

    private void throwInvalidInputExceptionForListInstructions(String invalidInput) {
        throw new InvalidInputException(String.format("Invalid input string for instructions:\n'%s'\nExpected a sequence of actions (R,L,F) without whitespaces (at least one instruction)", invalidInput));
    }
}
