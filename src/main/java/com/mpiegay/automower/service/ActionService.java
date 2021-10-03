package com.mpiegay.automower.service;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.OutOfLawnException;

import java.util.List;

public interface ActionService {
    /**
     * Applies the requested action step
     *
     * @param lawnDimension  The lawn dimension
     * @param beforeStepPosition The mower position before the step
     * @param currentStepAction The requester action step to apply
     * @return The actual position of the mower after de step
     * @throws OutOfLawnException If the mower goes out of the lawn after the step
     */
    MowerPosition applyStepAction(LawnDimension lawnDimension, MowerPosition beforeStepPosition, StepAction currentStepAction) throws OutOfLawnException;

    /**
     * Applies the sequence of actions
     *
     * @param lawnDimension The lawn dimension
     * @param mowerInitPosition The mower position at the beginning of the sequence
     * @param instructions The list of action steps
     * @return The mower position at the end of the sequence
     */
    MowerPosition applyAllInstructions(LawnDimension lawnDimension, MowerPosition mowerInitPosition, List<StepAction> instructions);
}
