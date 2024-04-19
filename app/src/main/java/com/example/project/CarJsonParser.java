package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CarJsonParser {
    public static List<CarType> getObjectFromJson(String json) {
        List<CarType> carTypes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                CarType carType = new CarType();
                carType.setType(jsonObject.getString("type"));

                // Handle the id as a string or int based on your requirements
                if (jsonObject.has("id")) {
                    if (jsonObject.get("id") instanceof String) {
                        carType.setId(Integer.parseInt(jsonObject.getString("id")));
                    } else if (jsonObject.get("id") instanceof Integer) {
                        carType.setId(jsonObject.getInt("id"));
                    }
                }

                carTypes.add(carType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return carTypes;
    }
}
