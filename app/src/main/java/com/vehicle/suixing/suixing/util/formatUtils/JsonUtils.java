package com.vehicle.suixing.suixing.util.formatUtils;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class JsonUtils {

    private static JsonBuilder builder;

    private JsonUtils() {

    }

    public static JsonBuilder getInstance() {
        builder = new JsonBuilder();
        return builder;
    }


    public static class JsonBuilder {
        private String json = "{\"isYours\":\"0x111\"";

        private JsonBuilder() {
        }

        public JsonBuilder setName(String name) {
            json += ",\"name\":\""+name+"\"";
            return builder;
        }

        public JsonBuilder setTel(String tel) {
            json += ",\"tel\":\""+tel+"\"";
            return builder;
        }

        public JsonBuilder setTime(String time){
            json += ",\"time\":\""+time+"\"";
            return builder;
        }

        public JsonBuilder setOilStyle(String oilStyle){
            json += ",\"oilStyle\":\""+oilStyle+"\"";
            return builder;
        }

        public JsonBuilder setMoney(String money){
            json += ",\"money\":\""+money+"\"";
            return builder;
        }
        public JsonBuilder setGasTel(String gasTel){
            json += ",\"gasTel\":\""+gasTel+"\"";
            return builder;
        }
        public JsonBuilder setGasName(String gasName){
            json += ",\"gasName\":\""+gasName+"\"";
            return builder;
        }
        public JsonBuilder setGasLocation(String gasLocation){
            json += ",\"gasLocation\":\""+gasLocation+"\"";
            return builder;
        }

        public String complete(){
            json+= "}";
            return json;
        }

    }
}
