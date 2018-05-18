package com.ygy.service;


import com.ygy.dao.OssclientUtilDao;
import com.ygy.dao.OssclientUtilDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class fileServiceImpl implements fileService {
    @Autowired
    OssclientUtilDao ossclientUtilDao;
    @Override
    public void updateFileDao(MultipartFile file){
       ossclientUtilDao.uplod(file.getOriginalFilename(),file);
    }

}