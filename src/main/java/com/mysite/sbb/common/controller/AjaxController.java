package com.mysite.sbb.common.controller;

import com.mysite.sbb.common.vo.AttachImageVO;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class AjaxController {

    private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(@RequestParam("uploadfile") MultipartFile[] uploadFile) {
        logger.info("uploadAjaxActionPOST .......");

        /* 이미지 파일 체크 */
        for (MultipartFile multipartFile : uploadFile) {
            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {
                type = Files.probeContentType(checkfile.toPath());
                logger.info("MIME TYPE : " + type);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!type.startsWith("image")) {
                List<AttachImageVO> list = null;
                return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
            }
        }

        String osName = System.getProperty("os.name").toLowerCase();
        String uploadFolder;

        logger.info("접속한 운영체제 : " + osName);

        if (osName.contains("win")) { // Windows
            uploadFolder = "C:\\upload";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) { // Linux, macOS
            uploadFolder = System.getProperty("user.home") + "/workspace/upload/lifechain/";
        } else {
            throw new UnsupportedOperationException("지원되지 않는 운영체제입니다.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        String datePath = str.replace("-", File.separator);

        /* 폴더 생성 */
        File uploadPath = new File(uploadFolder, datePath);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        List<AttachImageVO> list = new ArrayList<>(); //이미지 정보를 담는 객체

        for (MultipartFile multipartFile : uploadFile) {
            logger.info("-----------------------------------------------");
            logger.info("파일 이름 : " + multipartFile.getOriginalFilename());
            logger.info("파일 타입 : " + multipartFile.getContentType());
            logger.info("파일 크기 : " + multipartFile.getSize());

            //이미지 정보 객체
            AttachImageVO vo = new AttachImageVO();

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();

            vo.setFileName(uploadFileName);
            vo.setUploadPath(datePath);

            /* uuid 적용 파일 이름 */
            String uuid = UUID.randomUUID().toString();
            vo.setUuid(uuid);

            uploadFileName = uuid + "_" + uploadFileName;

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);

            /* 파일 저장 */
            try {
                multipartFile.transferTo(saveFile);

                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(vo);
        } //for문 끝

        ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
        logger.info("result : " + result);

        return result;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String fileName) {
        logger.info("getImage() ......... " + fileName);

        String osName = System.getProperty("os.name").toLowerCase();
        String uploadFolder;

        logger.info("접속한 운영체제 : " + osName);

        if (osName.contains("win")) { // Windows
            uploadFolder = "C:\\upload";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) { // Linux, macOS
            uploadFolder = System.getProperty("user.home") + "/workspace/upload/lifechain/";
        } else {
            throw new UnsupportedOperationException("지원되지 않는 운영체제입니다.");
        }

        File file = new File(uploadFolder + fileName);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", URLConnection.guessContentTypeFromName(file.getName()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        logger.info("deleteFile()........" + fileName);
        File file = null;

        try {
            String osName = System.getProperty("os.name").toLowerCase();
            String uploadFolder;

            logger.info("접속한 운영체제 : " + osName);

            if (osName.contains("win")) { // Windows
                uploadFolder = "C:\\upload";
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) { // Linux, macOS
                uploadFolder = System.getProperty("user.home") + "/workspace/upload/lifechain/";
            } else {
                throw new UnsupportedOperationException("지원되지 않는 운영체제입니다.");
            }

            //썸네일 파일 삭제
            file = new File(uploadFolder + URLDecoder.decode(fileName, "UTF-8"));
            file.delete();

            //원본 파일 삭제
            String originFileName = file.getAbsolutePath().replaceFirst("s_", "");
            logger.info("삭제 할 originFileName : " + originFileName);
            file = new File(originFileName);
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }


}

