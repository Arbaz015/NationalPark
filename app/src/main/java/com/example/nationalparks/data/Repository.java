package com.example.nationalparks.data;

import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nationalparks.controller.AppController;
import com.example.nationalparks.model.Activities;
import com.example.nationalparks.model.EntranceFees;
import com.example.nationalparks.model.Images;
import com.example.nationalparks.model.OperatingHours;
import com.example.nationalparks.model.Park;
import com.example.nationalparks.model.StandardHours;
import com.example.nationalparks.model.Topics;
import com.example.nationalparks.util.Util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static  List<Park> parkList= new ArrayList<>();
        public static void getParks(final AsyncResponse callBack)
        {
            JsonObjectRequest jsonObjectRequest=
                    new JsonObjectRequest(Request.Method.GET, Util.PARKS_URL,null, response -> {
                        try {
                            JSONArray jsonArray=response.getJSONArray("data");
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                Park park= new Park();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                park.setId(jsonObject.getString("id"));
                                park.setFullName(jsonObject.getString("fullName"));
                                park.setLatitude(jsonObject.getString("latitude"));
                                park.setLongitude(jsonObject.getString("longitude"));
                                park.setParkCode(jsonObject.getString("parkCode"));
                                park.setStates(jsonObject.getString("states"));
                                JSONArray imageList=jsonObject.getJSONArray("images");
                                List<Images> list= new ArrayList<>();
                                for (int j=0; j<imageList.length();j++)
                                {
                                    Images images= new Images();
                                    images.setCredit(imageList.getJSONObject(j).getString("credit"));
                                    images.setTitle(imageList.getJSONObject(j).getString("title"));
                                    images.setUrl(imageList.getJSONObject(j).getString("url"));
                                    list.add(images);
                                }
                                park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                                park.setDesignation(jsonObject.getString("designation"));
                                park.setName(jsonObject.getString("name"));
                                park.setImages(list);

                                //SetUp Activities of JsonArray
                                JSONArray activitiesArray= jsonObject.getJSONArray("activities");
                                List<Activities> activitiesList= new ArrayList<>();
                                for (int j = 0; j < activitiesArray.length(); j++) {
                                            Activities activities= new Activities();
                                            activities.setId(activitiesArray.getJSONObject(j).getString("id"));
                                            activities.setName(activitiesArray.getJSONObject(j).getString("name"));
                                            activitiesList.add(activities);
                                }
                                park.setActivities(activitiesList);

                                //Setting Topics

                                JSONArray topicsArray=jsonObject.getJSONArray("topics");
                                List<Topics> topicsList=new ArrayList<>();
                                for (int j = 0; j < topicsArray.length(); j++) {
                                    Topics topics= new Topics();
                                    topics.setId(topicsArray.getJSONObject(j).getString("id"));
                                    topics.setName(topicsArray.getJSONObject(j).getString("name"));
                                    topicsList.add(topics);
                                }
                                park.setTopics(topicsList);

                                JSONArray opArray=jsonObject.getJSONArray("operatingHours");
                                List<OperatingHours> operatingHoursList=new ArrayList<>();
                                for (int j = 0; j <opArray.length() ; j++) {
                                    OperatingHours op= new OperatingHours();
                                    op.setDescription(opArray.getJSONObject(j).getString("description"));

                                    StandardHours standardHours=new StandardHours();
                                    JSONObject hours=opArray.getJSONObject(j).getJSONObject("standardHours");
                                    standardHours.setSunday(hours.getString("wednesday"));
                                    standardHours.setMonday(hours.getString("monday"));
                                    standardHours.setMonday(hours.getString("thursday"));
                                    standardHours.setMonday(hours.getString("sunday"));
                                    standardHours.setMonday(hours.getString("tuesday"));
                                    standardHours.setMonday(hours.getString("friday"));
                                    standardHours.setMonday(hours.getString("saturday"));
                                    op.setStandardHours(standardHours);
                                    operatingHoursList.add(op);
                                }
                                park.setOperatingHours(operatingHoursList);
                                park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                                JSONArray entranceFees1=jsonObject .getJSONArray("entranceFees");
                                List <EntranceFees> entranceFeesList=new ArrayList<>();
                                for (int j = 0; j <entranceFees1.length(); j++) {
                                    EntranceFees entranceFees = new EntranceFees();
                                    entranceFees.setCost(entranceFees1.getJSONObject(j).getString("cost"));
                                    entranceFees.setDescription(entranceFees1.getJSONObject(j).getString("description"));
                                    entranceFees.setTitle(entranceFees1.getJSONObject(j).getString("title"));
                                    entranceFeesList.add(entranceFees);
                                    Log.d("Fees","Repos"+ entranceFees.getCost());


                                }
                                park.setEntranceFees(entranceFeesList);
                                park.setDescription(jsonObject.getString("description"));
                                park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                                parkList.add(park);
                            }
                            if (null!=callBack){callBack.processPark(parkList);}



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }, Throwable::printStackTrace
                    );
            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }

}
