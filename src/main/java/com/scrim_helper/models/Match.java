package com.scrim_helper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Match {
    @JsonProperty("match_id")
    private String matchId;
    @JsonProperty("match_start")
    private String matchStart;
    @JsonProperty("hero_asset_id")
    private String heroAssetId;
    private Stats stats;
}
