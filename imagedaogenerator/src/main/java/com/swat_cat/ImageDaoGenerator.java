package com.swat_cat;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ImageDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "swat_cat.com.imagefetcher.models");
        Entity image = schema.addEntity("Image");
        image.addIdProperty();
        image.addStringProperty("title");
        image.addStringProperty("url");
        image.addStringProperty("thumbUrl");
        image.addIntProperty("imageHeight");
        image.addIntProperty("imageWidth");
        image.addIntProperty("thumbHeight");
        image.addIntProperty("thumbWidth");
        image.addStringProperty("Uri").index();
        image.addBooleanProperty("isSaved");
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
