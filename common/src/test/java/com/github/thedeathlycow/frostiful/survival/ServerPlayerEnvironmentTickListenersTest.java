/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ServerPlayerEnvironmentTickListenersTest {
    @ParameterizedTest
    @ValueSource(doubles = {
            0.01, 1.0, 5.0, 9.99, 10.0
    })
    void coolTemperatureRangeIsNegativeOne(double temperatureC) {
        int tempChange = ServerPlayerEnvironmentTickListeners.envTemperatureToTemperaturePoint(new TemperatureRecord(temperatureC));
        Assertions.assertEquals(-1, tempChange);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -9.99, -9.0, -5.0, -0.01, 0.0
    })
    void coldTemperatureRangeIsNegativeTwo(double temperatureC) {
        int tempChange = ServerPlayerEnvironmentTickListeners.envTemperatureToTemperaturePoint(new TemperatureRecord(temperatureC));
        Assertions.assertEquals(-2, tempChange);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -19.99, -19.0, -15.0, -10.01, -10.0
    })
    void freezingTemperatureRangeIsNegativeThree(double temperatureC) {
        int tempChange = ServerPlayerEnvironmentTickListeners.envTemperatureToTemperaturePoint(new TemperatureRecord(temperatureC));
        Assertions.assertEquals(-3, tempChange);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -29.99, -29.0, -25.0, -20.01, -20.0
    })
    void extremeFreezingTemperatureRangeIsNegativeFour(double temperatureC) {
        int tempChange = ServerPlayerEnvironmentTickListeners.envTemperatureToTemperaturePoint(new TemperatureRecord(temperatureC));
        Assertions.assertEquals(-4, tempChange);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            10.01, 15.0, 20.0, 30.0, 100.0, Double.MAX_VALUE
    })
    void neutralTemperatureRangeIsZero(double temperatureC) {
        int tempChange = ServerPlayerEnvironmentTickListeners.envTemperatureToTemperaturePoint(new TemperatureRecord(temperatureC));
        Assertions.assertEquals(0, tempChange);
    }
}