package com.mpiegay.automower.service.impl;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.Orientation;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.InvalidInputException;
import com.mpiegay.automower.exception.OutOfLawnException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ActionServiceImplTest {

    private static final LawnDimension LAWN_DIMENSION = new LawnDimension(5, 5);
    @Autowired
    private ActionServiceImpl mowerService;

    @Test
    void shouldGoToTheNorth_whenApplyStepAction_withForwardActionAndNorthOriented() throws OutOfLawnException {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(1, 2, Orientation.N);

        // WHEN
        MowerPosition mowerPosition = mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.F);

        // THEN
        assertThat(mowerPosition).isNotNull()
                .isEqualTo(new MowerPosition(1, 3, Orientation.N));
    }

    @Test
    void shouldBeWestOriented_whenApplyStepAction_withLeftActionAndNorthOriented() throws OutOfLawnException {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(1, 2, Orientation.N);

        // WHEN
        MowerPosition mowerPosition = mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.L);

        // THEN
        assertThat(mowerPosition).isNotNull()
                .isEqualTo(new MowerPosition(1, 2, Orientation.W));
    }

    @Test
    void shouldBeEstOriented_whenApplyStepAction_withRightActionAndNorthOriented() throws OutOfLawnException {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(1, 2, Orientation.N);

        // WHEN
        MowerPosition mowerPosition = mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.R);

        // THEN
        assertThat(mowerPosition).isNotNull()
                .isEqualTo(new MowerPosition(1, 2, Orientation.E));
    }

    @Test
    void shouldThrowOutOfLawnException_whenApplyStepAction_withForwardActionAndNorthOrientedAndAtNorthestPosition() {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(0, LAWN_DIMENSION.getY(), Orientation.N);

        // WHEN / THEN
        assertThatThrownBy(() -> mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.F))
        .isInstanceOf(OutOfLawnException.class)
        .hasMessage("The mower is out of the lawn dimension. LawnDimension{x=5, y=5} and MowerPosition{x=0, y=6, orientation='N'}");
    }

    @Test
    void shouldThrowOutOfLawnException_whenApplyStepAction_withForwardActionAndWestOrientedAndAtWhestestPosition() {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(0, 0, Orientation.W);

        // WHEN / THEN
        assertThatThrownBy(() -> mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.F))
                .isInstanceOf(OutOfLawnException.class)
                .hasMessage("The mower is out of the lawn dimension. LawnDimension{x=5, y=5} and MowerPosition{x=-1, y=0, orientation='W'}");
    }

    @Test
    void shouldThrowOutOfLawnException_whenApplyStepAction_withForwardActionAndSouthOrientedAndAtSouthestPosition() {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(0, 0, Orientation.S);

        // WHEN / THEN
        assertThatThrownBy(() -> mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.F))
                .isInstanceOf(OutOfLawnException.class)
                .hasMessage("The mower is out of the lawn dimension. LawnDimension{x=5, y=5} and MowerPosition{x=0, y=-1, orientation='S'}");
    }

    @Test
    void shouldThrowOutOfLawnException_whenApplyStepAction_withForwardActionAndEastOrientedAndAtEastestPosition() {
        // GIVEN
        MowerPosition beforeStepPosition = new MowerPosition(LAWN_DIMENSION.getX(), 0, Orientation.E);

        // WHEN / THEN
        assertThatThrownBy(() -> mowerService.applyStepAction(LAWN_DIMENSION, beforeStepPosition, StepAction.F))
                .isInstanceOf(OutOfLawnException.class)
                .hasMessage("The mower is out of the lawn dimension. LawnDimension{x=5, y=5} and MowerPosition{x=6, y=0, orientation='E'}");
    }

    @Test
    void shouldThrowException_whenApplyAllInstructions_withInvalidInitPosition() {
        // GIVEN
        MowerPosition initPosition = new MowerPosition(LAWN_DIMENSION.getX() + 1, 0, Orientation.E);

        // WHEN / THEN
        assertThatThrownBy(() -> mowerService.applyAllInstructions(LAWN_DIMENSION, initPosition, List.of()))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("One mower has an incorrect initial position : The mower is out of the lawn dimension. LawnDimension{x=5, y=5} and MowerPosition{x=6, y=0, orientation='E'}");
    }

    @Test
    void shouldContinueNextStep_whenApplyAllInstructions_andInstructionsMakeMowerHitTheBorder() {
        // GIVEN
        MowerPosition initPosition = new MowerPosition(0, LAWN_DIMENSION.getY()-1, Orientation.N);

        // WHEN
        MowerPosition mowerFinalPosition = mowerService.applyAllInstructions(LAWN_DIMENSION, initPosition, List.of(StepAction.F, StepAction.F, StepAction.R));

        // THEN
        assertThat(mowerFinalPosition).isNotNull()
                .isEqualTo(new MowerPosition(0, LAWN_DIMENSION.getY(), Orientation.E));
    }
}