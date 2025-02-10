package com.scrim_helper.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrim_helper.models.FinalDetails;
import com.scrim_helper.models.Match;
import com.scrim_helper.models.ResponseData;

import java.io.IOException;
import java.util.List;

public class JsonParser {

    private final ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Match> parseMatches(String jsonResponse) throws IOException {
        ResponseData responseData = objectMapper.readValue(jsonResponse, ResponseData.class);
        return responseData.getData();
    }

    public List<FinalDetails> parseFinalDetails(String jsonResponse) throws IOException {
        return objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
    }
}
