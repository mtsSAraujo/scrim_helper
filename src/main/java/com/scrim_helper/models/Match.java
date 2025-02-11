package com.scrim_helper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scrim_helper.utils.GameType;
import com.scrim_helper.utils.Hunters;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Match {
    @JsonProperty("match_id")
    private String matchId;
    @JsonProperty("queue_id")
    private GameType typeOfMatch;
    @JsonProperty("match_start")
    private String matchStart;
    @JsonProperty("hero_asset_id")
    private Hunters heroAssetId;
    private Stats stats;
}
