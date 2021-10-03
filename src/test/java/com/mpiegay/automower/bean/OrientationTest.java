package com.mpiegay.automower.bean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrientationTest {

    @Test
    void toTheLeft() {
        assertThat(Orientation.toTheLeft(Orientation.W)).isEqualTo(Orientation.S);
        assertThat(Orientation.toTheLeft(Orientation.S)).isEqualTo(Orientation.E);
        assertThat(Orientation.toTheLeft(Orientation.E)).isEqualTo(Orientation.N);
        assertThat(Orientation.toTheLeft(Orientation.N)).isEqualTo(Orientation.W);
    }

    @Test
    void toTheRight() {
        assertThat(Orientation.toTheRight(Orientation.N)).isEqualTo(Orientation.E);
        assertThat(Orientation.toTheRight(Orientation.E)).isEqualTo(Orientation.S);
        assertThat(Orientation.toTheRight(Orientation.S)).isEqualTo(Orientation.W);
        assertThat(Orientation.toTheRight(Orientation.W)).isEqualTo(Orientation.N);
    }
}