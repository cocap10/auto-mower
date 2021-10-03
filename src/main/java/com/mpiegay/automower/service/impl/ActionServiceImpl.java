package com.mpiegay.automower.service.impl;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.Orientation;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.InvalidInputException;
import com.mpiegay.automower.exception.OutOfLawnException;
import com.mpiegay.automower.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActionServiceImpl implements ActionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionServiceImpl.class);

    @Override
    public MowerPosition applyStepAction(LawnDimension lawnDimension, MowerPosition beforeStepPosition, StepAction currentStepAction) throws OutOfLawnException {
        MowerPosition afterStepPosition = new MowerPosition(beforeStepPosition);
        switch (currentStepAction) {
            case L:
                Orientation afterStepOrientation = Orientation.toTheLeft(beforeStepPosition.getOrientation());
                afterStepPosition.setOrientation(afterStepOrientation);
                break;
            case R:
                afterStepOrientation = Orientation.toTheRight(beforeStepPosition.getOrientation());
                afterStepPosition.setOrientation(afterStepOrientation);
                break;
            case F:
                afterStepPosition = applyForward(beforeStepPosition);
                break;
        }
        verifyPosition(lawnDimension, afterStepPosition);
        return afterStepPosition;
    }

    private MowerPosition applyForward(MowerPosition beforeStepPosition) {
        Orientation orientation = beforeStepPosition.getOrientation();
        int x = beforeStepPosition.getX();
        int y = beforeStepPosition.getY();
        switch (orientation) {
            case N:
                y++;
                break;
            case E:
                x++;
                break;
            case S:
                y--;
                break;
            case W:
                x--;
                break;
        }
        return new MowerPosition(x, y, orientation);
    }

    private void verifyPosition(LawnDimension lawnDimension, MowerPosition afterStepPosition) throws OutOfLawnException {
        if (afterStepPosition.getX() < 0 || afterStepPosition.getX() > lawnDimension.getX()) {
            throw new OutOfLawnException(lawnDimension, afterStepPosition);
        }
        if (afterStepPosition.getY() < 0 || afterStepPosition.getY() > lawnDimension.getY()) {
            throw new OutOfLawnException(lawnDimension, afterStepPosition);
        }
    }

    @Override
    public MowerPosition applyAllInstructions(LawnDimension lawnDimension, MowerPosition mowerInitPosition, List<StepAction> instructions) {
        try {
            this.verifyPosition(lawnDimension, mowerInitPosition);
        } catch (OutOfLawnException e) {
            LOGGER.error("Initial position error :", e);
            throw new InvalidInputException("One mower has an incorrect initial position : " + e.getMessage());
        }
        MowerPosition mowerCurrentPosition = mowerInitPosition;
        for (StepAction stepAction : instructions) {
            try {
                mowerCurrentPosition = this.applyStepAction(lawnDimension, mowerCurrentPosition, stepAction);
            } catch (OutOfLawnException e) {
                LOGGER.warn("Hit the boarder on action {} : ", stepAction, e);
            }
        }
        return mowerCurrentPosition;
    }
}
