package com.mpiegay.automower.service.impl;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;
import com.mpiegay.automower.bean.Orientation;
import com.mpiegay.automower.bean.StepAction;
import com.mpiegay.automower.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ParserServiceImplTest {
    @Autowired
    private ParserServiceImpl parserService;

    @Test
    void shouldReturnLawnDimension_whenParseLawnDimension() {
        assertThat(parserService.parseLawnDimension("5 5"))
                .isNotNull()
                .isEqualTo(new LawnDimension(5, 5));
        assertThat(parserService.parseLawnDimension(" 2 10 "))
                .isNotNull()
                .isEqualTo(new LawnDimension(2, 10));
    }

    @Test
    void shouldThrowInvalidInputException_whenParseLawnDimension_withNullInput() {
        assertThatThrownBy(() -> parserService.parseLawnDimension(null))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string 'null' for lawn dimension. Expected 'x y' with x and y two not null positive integers");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseLawnDimension_withEmptyInput() {
        assertThatThrownBy(() -> parserService.parseLawnDimension(""))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '' for lawn dimension. Expected 'x y' with x and y two not null positive integers");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseLawnDimension_with3InputIntegers() {
        assertThatThrownBy(() -> parserService.parseLawnDimension("1 2 3"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 2 3' for lawn dimension. Expected 'x y' with x and y two not null positive integers");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseLawnDimension_withoutValidInput() {
        assertThatThrownBy(() -> parserService.parseLawnDimension("1 otherThing"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 otherThing' for lawn dimension. Expected 'x y' with x and y two not null positive integers");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseLawnDimension_withNegativeValue() {
        assertThatThrownBy(() -> parserService.parseLawnDimension("5 -1"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '5 -1' for lawn dimension. Expected 'x y' with x and y two not null positive integers");
    }

    @Test
    void shouldReturnMowerPosition_whenParseMowerInitPosition() {
        assertThat(parserService.parseMowerInitPosition("1 2 N"))
                .isNotNull()
                .isEqualTo(new MowerPosition(1, 2, Orientation.N));

        assertThat(parserService.parseMowerInitPosition("  10 44 W  "))
                .isNotNull()
                .isEqualTo(new MowerPosition(10, 44, Orientation.W));
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withNullInput() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition(null))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string 'null' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withEmptyInput() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition(""))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withLessThan3Block() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition("1 2"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 2' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withMoreThan3Blocks() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition("1 2 N 4"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 2 N 4' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withInvalidNumberInput() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition("1 A E"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 A E' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseMowerInitPosition_withInvalidOrientationInput() {
        assertThatThrownBy(() -> parserService.parseMowerInitPosition("1 2 O"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string '1 2 O' for initial mower position. Expected 'x y O' with x, y two not null positive integers and O one of N,W,S or E upper-case character");
    }

    @Test
    void shouldReturnListOfActionStep_whenParseListPosition() {
        assertThat(parserService.parseListInstructions("FLFRFF"))
                .isNotEmpty()
                .contains(
                        StepAction.F,
                        StepAction.L,
                        StepAction.F,
                        StepAction.R,
                        StepAction.F,
                        StepAction.F
                );

        assertThat(parserService.parseListInstructions("  F "))
                .isNotEmpty()
                .contains(StepAction.F);
    }

    @Test
    void shouldThrowInvalidInputException_whenParseListInstructions_withInvalidActionInput() {
        assertThatThrownBy(() -> parserService.parseListInstructions("FF F"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string for instructions:\n'FF F'\nExpected a sequence of actions (R,L,F) without whitespaces (at least one instruction)");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseListInstructions_withEmptyInput() {
        assertThatThrownBy(() -> parserService.parseListInstructions(""))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string for instructions:\n''\nExpected a sequence of actions (R,L,F) without whitespaces (at least one instruction)");
    }

    @Test
    void shouldThrowInvalidInputException_whenParseListInstructions_withNullInput() {
        assertThatThrownBy(() -> parserService.parseListInstructions(null))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("Invalid input string for instructions:\n'null'\nExpected a sequence of actions (R,L,F) without whitespaces (at least one instruction)");
    }
}