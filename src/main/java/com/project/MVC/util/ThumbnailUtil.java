package com.project.MVC.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ThumbnailUtil {

    public static void createThumbnail(String filename) throws IOException {
        Thumbnails.of(filename).scale(0.5).toFile(filename);
    }

}
