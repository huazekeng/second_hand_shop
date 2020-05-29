package com.mrli.second_shop.util;

import java.io.File;
import java.util.Date;

public class FileDirUtil {


    public static String getFilePath(String parentPath) {
        Date date = new Date();
        String path = parentPath + date.getYear() + "//" + date.getMonth() + "//" + date.getDay();
        File file = new File(path);


        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }
}
