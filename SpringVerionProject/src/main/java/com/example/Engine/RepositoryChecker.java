package com.example.Engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositoryChecker {

	public static List<File> filterRepository(String path, String filterName,List <File> filearray){
		File RepoDir = new File(path);
		File[] listFiles = RepoDir.listFiles();
		if(listFiles == null){
			return filearray;
		}
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			if(file.isDirectory()){
				filterRepository(file.getAbsolutePath(),filterName,filearray);
			} else {
				if(file.getName().contains(filterName)){
					filearray.add(file);
				}
			}
		}
		return filearray;
	}
	
	
}
