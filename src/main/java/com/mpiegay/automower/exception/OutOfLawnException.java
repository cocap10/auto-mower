package com.mpiegay.automower.exception;

import com.mpiegay.automower.bean.LawnDimension;
import com.mpiegay.automower.bean.MowerPosition;

/**
 * Exception thrown when the mower goes out of the lawn dimension (on any directions)
 */
public class OutOfLawnException extends Exception {
    public OutOfLawnException(LawnDimension lawnDimension, MowerPosition afterStepPosition) {
        super(String.format("The mower is out of the lawn dimension. %s and %s", lawnDimension, afterStepPosition));
    }
}
