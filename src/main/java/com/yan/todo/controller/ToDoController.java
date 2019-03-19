package com.yan.todo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yan.todo.mapper.TaskMapper;
import com.yan.todo.mapper.ImageRefMapper;
import com.yan.todo.mapper.ImageTagMapper;
import com.yan.todo.schema.ImageMain;
import com.yan.todo.schema.ImageRef;
import com.yan.todo.schema.ImageTag;

@Controller
public class ToDoController {
	
	public static final String UPLOAD_FILE_ROOT_DIR = "E:\\uploadfile\\test";
	
	@Value("${image.root.dir}")
	private String imageRootDir;
	
	@Value("${image.server.root.url}")
	private String imageServerRootUrl;
	
	@Autowired
	private TaskMapper imageMainMapper;
	
	@Autowired
	private ImageRefMapper imageRefMapper;
	
	@Autowired
	private ImageTagMapper imageTagMapper;
	
	// 访问路径为：http://127.0.0.1:8080/file
	@RequestMapping("/file")
	public String file() {
		return "file";
	}

	// 访问路径为：http://127.0.0.1:8080/file
	@RequestMapping("/ajaxfile")
	public String ajaxfile() {
		return "ajaxfile";
	}
	
	@RequestMapping("/filelist")
	public String filelist() {
		return "filelist";
	}
	
	/**
	 * 文件上传具体实现方法;
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				/*
				 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
				 * d:/files大家是否能实现呢？ 等等;
				 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
				 * 3、文件格式; 4、文件大小的限制;
				 */
				String category = "image";
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(UPLOAD_FILE_ROOT_DIR + File.separator + category + File.separator + file.getOriginalFilename())));
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			}
			return "上传成功";
		} else {
			return "上传失败，因为文件是空的.";
		}
	}
	
	@RequestMapping("/ajaxupload")
	@ResponseBody
	public String ajaxupload(@RequestParam("file") MultipartFile file, String userCode, String category, String fileNewName, String tags) {
		// uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 计算文件的md5值
		String md5Hex = null;
		try {
			md5Hex = DigestUtils.md5DigestAsHex(file.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(md5Hex != null && !"".equals(md5Hex.trim())) {
			
			String fileName = file.getOriginalFilename();
			if (fileName.indexOf("\\") != -1) {
				fileName = fileName.substring(fileName.lastIndexOf("\\"));
			}
			
			// suffix
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 后缀名都转为小写存储
			suffix = suffix.toLowerCase();
			
			// 不带后缀名得文件名称
			String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
			
			ImageRef imageRef = new ImageRef();
			imageRef.setUuid(uuid);
			imageRef.setMd5(md5Hex);
			
			// 如果前台传入了文件重命名参数，那么文件展示名称设置为重命名后的文件名称，否则展示原文件名
			if(fileNewName != null && !"".equals(fileNewName.trim())) {
				// suffix
				String fileNewNameSuffix = fileNewName.substring(fileNewName.lastIndexOf(".") + 1);
				// 后缀名都转为小写存储
				fileNewNameSuffix = fileNewNameSuffix.toLowerCase();
				
				// 不带后缀名得文件名称
				String fileNewNameWithoutSuffix = fileNewName.substring(0, fileNewName.lastIndexOf("."));
				
				imageRef.setDisplayName(fileNewNameWithoutSuffix);
				imageRef.setSuffix(fileNewNameSuffix);
			}else {
				imageRef.setDisplayName(fileNameWithoutSuffix);
				imageRef.setSuffix(suffix);
			}
			imageRef.setCategory(category);
			imageRef.setUserCode(userCode);
			imageRef.setValidStatus("1");
			imageRef.setInsertTime(new Date());
			imageRef.setUpdateTime(new Date());
			
			// 处理标签tags
			// 将页面传入的tag，根据英文逗号分割后转换为List
			List<String> tagList = new ArrayList<String>();
			if(tags != null && !"".equals(tags.trim())){
				
				String[] tagAry = tags.split(",");
				
				if(tagAry != null){
					for(String tag:tagAry){
						
						if(tag != null && !"".equals(tag)){
							tagList.add(tag);
						}
					}
				}
			}
			
			if(tagList != null && tagList.size() > 0){
				
				// 先删后插
				
				// 需要根据md5删除已有的tag
				imageTagMapper.deletImageTagByMd5(md5Hex);
				
				// 新增tag
				List<ImageTag> imageTagsToInsert = new ArrayList<ImageTag>();
				
				for(String tagName:tagList){
					ImageTag imageTag = new ImageTag();
					
					imageTag.setMd5(md5Hex);
					imageTag.setTagName(tagName);
					imageTag.setValidStatus("1");
					imageTag.setInsertTime(new Date());
					imageTag.setUpdateTime(new Date());
					
					imageTagsToInsert.add(imageTag);
				}
				
				if(imageTagsToInsert != null && imageTagsToInsert.size() > 0){
					imageTagMapper.insertBatchImageTag(imageTagsToInsert);
				}
				
			}else{
				// 需要根据md5删除已有的tag
				imageTagMapper.deletImageTagByMd5(md5Hex);
			}
			
			// 根据md5值判断下文件是否已经在磁盘上
			List<ImageMain> imageMainTemps =  imageMainMapper.findImageMainByMD5(md5Hex);
			
			if(imageMainTemps != null && imageMainTemps.size() > 0) {
				// 如果相同md5值得文件已经上传过了，不再写入到磁盘，不再写入ImageMain对象，写入到ImageRef对象
				imageRefMapper.insertImageRef(imageRef);
			}else {
				// 相同md5值得文件没有上传过
				
				
				// 一张图片可能多人上传，不同人上传的分类可能不同，存在一定冲突
				// 所以写入磁盘时根据日期区分文件夹比较合适
				
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				String dateStr = simpleDateFormat.format(new Date());

				// 图片在磁盘存储的文件夹
				String fileDir = null;
				if(imageRootDir.endsWith("/")) {
					fileDir = imageRootDir + dateStr;
				}else {
					fileDir = imageRootDir + "/" + dateStr;
				}
				
				// 图片存储的位置
				String location = fileDir + "/" + uuid + "." + suffix;
				
				// 组装图片的url
				String url = null;
				
				if(imageServerRootUrl.endsWith("/")) {
					url = imageServerRootUrl + dateStr + "/" + uuid + "." + suffix;
				}else {
					url = imageServerRootUrl + "/" + dateStr + "/" + uuid + "." + suffix;
				}
				
				// 组装ImageMain对象
				ImageMain imageMain = new ImageMain();
				
				imageMain.setUuid(uuid);
				imageMain.setMd5(md5Hex);
				imageMain.setLocation(location);
				
				imageMain.setUrl(url);
				imageMain.setSuffix(suffix);
				imageMain.setValidStatus("1");
				imageMain.setInsertTime(new Date());
				imageMain.setUpdateTime(new Date());
				
				// 文件写入磁盘
				
				File targetFile = new File(fileDir);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(location);
					out.write(file.getBytes());
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
					return "上传失败";
				}
				
				// 将文件数据写入数据库
				imageMainMapper.insertImageMain(imageMain);
				imageRefMapper.insertImageRef(imageRef);
			}
			
		}
		return "上传成功!";
	}

	
	@RequestMapping("/ajaxuploadfiles")
	@ResponseBody
	public String ajaxUploadFiles(@RequestParam("files") MultipartFile[] files, String userCode, String category, String fileNewNames, String tags) {
		
		if(files != null && files.length > 0) {
			
			for(int i=0;i<files.length;i++) {
				MultipartFile file = files[i];
				
				String[] fnewNames = fileNewNames.split(",");
				String fileNewName = "";
				if(fnewNames != null && fnewNames.length == files.length) {
					fileNewName = fnewNames[i];
				}
				this.uploadSingleFile(file, userCode, category, fileNewName, tags);
			}
		}else {
			return "上传失败";
		}
		
		return "上传成功!";
	}
	
	private boolean uploadSingleFile(MultipartFile file, String userCode, String category, String fileNewName, String tags) {
		// uuid
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 计算文件的md5值
		String md5Hex = null;
		try {
			md5Hex = DigestUtils.md5DigestAsHex(file.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(md5Hex != null && !"".equals(md5Hex.trim())) {
			
			String fileName = file.getOriginalFilename();
			if (fileName.indexOf("\\") != -1) {
				fileName = fileName.substring(fileName.lastIndexOf("\\"));
			}
			
			// suffix
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 后缀名都转为小写存储
			suffix = suffix.toLowerCase();
			
			// 不带后缀名得文件名称
			String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
			
			ImageRef imageRef = new ImageRef();
			imageRef.setUuid(uuid);
			imageRef.setMd5(md5Hex);
			
			// 如果前台传入了文件重命名参数，那么文件展示名称设置为重命名后的文件名称，否则展示原文件名
			if(fileNewName != null && !"".equals(fileNewName.trim())) {
				// suffix
				String fileNewNameSuffix = fileNewName.substring(fileNewName.lastIndexOf(".") + 1);
				// 后缀名都转为小写存储
				fileNewNameSuffix = fileNewNameSuffix.toLowerCase();
				
				// 不带后缀名得文件名称
				String fileNewNameWithoutSuffix = fileNewName.substring(0, fileNewName.lastIndexOf("."));
				
				imageRef.setDisplayName(fileNewNameWithoutSuffix);
				imageRef.setSuffix(fileNewNameSuffix);
			}else {
				imageRef.setDisplayName(fileNameWithoutSuffix);
				imageRef.setSuffix(suffix);
			}
			imageRef.setCategory(category);
			imageRef.setUserCode(userCode);
			imageRef.setValidStatus("1");
			imageRef.setInsertTime(new Date());
			imageRef.setUpdateTime(new Date());
			
			// 处理标签tags
			// 将页面传入的tag，根据英文逗号分割后转换为List
			List<String> tagList = new ArrayList<String>();
			if(tags != null && !"".equals(tags.trim())){
				
				String[] tagAry = tags.split(",");
				
				if(tagAry != null){
					for(String tag:tagAry){
						
						if(tag != null && !"".equals(tag)){
							tagList.add(tag);
						}
					}
				}
			}
			
			if(tagList != null && tagList.size() > 0){
				
				// 先删后插
				
				// 需要根据md5删除已有的tag
				imageTagMapper.deletImageTagByMd5(md5Hex);
				
				// 新增tag
				List<ImageTag> imageTagsToInsert = new ArrayList<ImageTag>();
				
				for(String tagName:tagList){
					ImageTag imageTag = new ImageTag();
					
					imageTag.setMd5(md5Hex);
					imageTag.setTagName(tagName);
					imageTag.setValidStatus("1");
					imageTag.setInsertTime(new Date());
					imageTag.setUpdateTime(new Date());
					
					imageTagsToInsert.add(imageTag);
				}
				
				if(imageTagsToInsert != null && imageTagsToInsert.size() > 0){
					imageTagMapper.insertBatchImageTag(imageTagsToInsert);
				}
				
			}else{
				// 需要根据md5删除已有的tag
				imageTagMapper.deletImageTagByMd5(md5Hex);
			}
			
			// 根据md5值判断下文件是否已经在磁盘上
			List<ImageMain> imageMainTemps =  imageMainMapper.findImageMainByMD5(md5Hex);
			
			if(imageMainTemps != null && imageMainTemps.size() > 0) {
				// 如果相同md5值得文件已经上传过了，不再写入到磁盘，不再写入ImageMain对象，写入到ImageRef对象
				imageRefMapper.insertImageRef(imageRef);
			}else {
				// 相同md5值得文件没有上传过
				
				
				// 一张图片可能多人上传，不同人上传的分类可能不同，存在一定冲突
				// 所以写入磁盘时根据日期区分文件夹比较合适
				
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				String dateStr = simpleDateFormat.format(new Date());

				// 图片在磁盘存储的文件夹
				String fileDir = null;
				if(imageRootDir.endsWith("/")) {
					fileDir = imageRootDir + dateStr;
				}else {
					fileDir = imageRootDir + "/" + dateStr;
				}
				
				// 图片存储的位置
				String location = fileDir + "/" + uuid + "." + suffix;
				
				// 组装图片的url
				String url = null;
				
				if(imageServerRootUrl.endsWith("/")) {
					url = imageServerRootUrl + dateStr + "/" + uuid + "." + suffix;
				}else {
					url = imageServerRootUrl + "/" + dateStr + "/" + uuid + "." + suffix;
				}
				
				// 组装ImageMain对象
				ImageMain imageMain = new ImageMain();
				
				imageMain.setUuid(uuid);
				imageMain.setMd5(md5Hex);
				imageMain.setLocation(location);
				
				imageMain.setUrl(url);
				imageMain.setSuffix(suffix);
				imageMain.setValidStatus("1");
				imageMain.setInsertTime(new Date());
				imageMain.setUpdateTime(new Date());
				
				// 文件写入磁盘
				
				File targetFile = new File(fileDir);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(location);
					out.write(file.getBytes());
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
				// 将文件数据写入数据库
				imageMainMapper.insertImageMain(imageMain);
				imageRefMapper.insertImageRef(imageRef);
			}
			
		}
		
		return true;
	}
}
