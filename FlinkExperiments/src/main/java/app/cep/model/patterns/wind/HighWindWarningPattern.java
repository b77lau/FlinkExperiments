// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package app.cep.model.patterns.wind;

import app.cep.model.IWarningPattern;
import app.cep.model.warnings.wind.HighWindWarning;
import model.LocalWeatherData;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

import java.util.List;
import java.util.Map;

public class HighWindWarningPattern implements IWarningPattern<LocalWeatherData, HighWindWarning> {

    public HighWindWarningPattern() {

    }

    @Override
    public HighWindWarning create(Map<String, List<LocalWeatherData>> pattern) {
        LocalWeatherData first = pattern.get("First Event").get(0);

        return new HighWindWarning(first);
    }

    @Override
    public Pattern<LocalWeatherData, ?> getEventPattern() {
        return Pattern
                .<LocalWeatherData>begin("First Event").where(
                        new SimpleCondition<LocalWeatherData>() {
                            @Override
                            public boolean filter(LocalWeatherData event) throws Exception {
                                return event.getWindSpeed() >= 39 && event.getWindSpeed() <= 110;
                            }
                        });
    }

    @Override
    public Class<HighWindWarning> getWarningTargetType() {
        return HighWindWarning.class;
    }
}
