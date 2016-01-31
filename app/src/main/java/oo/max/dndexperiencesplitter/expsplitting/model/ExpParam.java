package oo.max.dndexperiencesplitter.expsplitting.model;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
@Getter
public class ExpParam {

    private final int baseExp;

    private final int maxBonus;
}
