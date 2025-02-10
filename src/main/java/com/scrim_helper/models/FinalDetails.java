package com.scrim_helper.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scrim_helper.utils.Hunters;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class FinalDetails {
    @JsonProperty("match_end")
    private String matchEnd;
    @JsonProperty("team_id")
    private int teamId;
    @JsonProperty("hero_asset_id")
    private Hunters hero;
    @JsonProperty("placement")
    private int placement;
    private Stats stats;
    private Player player;
}
