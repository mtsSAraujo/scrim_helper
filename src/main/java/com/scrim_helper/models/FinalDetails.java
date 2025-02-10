package com.scrim_helper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class FinalDetails {
    @JsonProperty("match_end")
    private String matchEnd;
    @JsonProperty("hero_asset_id")
    private String hero;
    @JsonProperty("placement")
    private int placement;
    private Stats stats;
    private Player player;
}
