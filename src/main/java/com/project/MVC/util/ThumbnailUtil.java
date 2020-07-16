package com.project.MVC.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ThumbnailUtil {


    public static void createThumbnail(String filename, String uploadPath, boolean isMessage) throws IOException {

        File uploadDir = new File(uploadPath);
        File thumbnails = new File(uploadPath + "/thumbs/");
        if (!thumbnails.exists()) thumbnails.mkdir();

        if (isMessage) {
            Thumbnails.of(uploadDir.getAbsolutePath() + "\\" + filename).scale(0.75).toFile(uploadDir.getAbsolutePath() + "\\" + filename);
            Thumbnails.of(uploadDir.getAbsolutePath() + "\\" + filename).scale(0.25).toFile(thumbnails.getAbsolutePath() + "\\" + filename);
        } else {
            BufferedImage bufferedImage = ImageIO.read(new File(uploadDir.getAbsolutePath() + "\\" + filename));

            int maxSize = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());

            Thumbnails.of(uploadDir.getAbsolutePath() + "\\" + filename).sourceRegion(Positions.CENTER, maxSize, maxSize)
                    .size(300, 300).toFile(uploadDir.getAbsolutePath() + "\\" + filename);
            Thumbnails.of(uploadDir.getAbsolutePath() + "\\" + filename).size(40, 40).toFile(thumbnails.getAbsolutePath() + "\\" + filename);
        }
    }

    public static String createFile(MultipartFile file, String uploadPath, boolean isMessage) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) uploadDir.mkdir();

        String filename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + filename));
        ThumbnailUtil.createThumbnail(filename, uploadPath, isMessage);

        return filename;
    }

}
