package top.flyfire.degetation.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import top.flyfire.degetation.Const;
import top.flyfire.degetation.go.Go;
import top.flyfire.degetation.go.KVTask;
import top.flyfire.degetation.go.Go.GoTask;
import top.flyfire.degetation.go.TaskList;

public class PackageUtil implements Const {
	public static List<String> allClassNameFrom(String pckgName){
		try {
			return allClassNameFrom(pckgName,true);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public static List<String> allClassNameFrom(String pckgName,final boolean childPackage) throws URISyntaxException{
		final List<String> classNameList = new ArrayList<String>();
		pckgName = pckgName.replace(".", URL_SEPARATOR$);
		final URL url = LOADER.getResource(pckgName);
		final URI uri = url.toURI();
		Go.notNullTo(uri, new Go.GoTask() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Go.to(url.getProtocol(), new TaskList<String>(new KVTask<String>() {

					@Override
					public String key() {
						// TODO Auto-generated method stub
						return Protocol.FILE;
					}

					@Override
					public GoTask val() {
						// TODO Auto-generated method stub
						return new Go.GoTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								classNameList.addAll(allClassNameByFile(uri, childPackage));
							}
						};
					}
				},new KVTask<String>() {

					@Override
					public String key() {
						// TODO Auto-generated method stub
						return Protocol.JAR;
					}

					@Override
					public GoTask val() {
						// TODO Auto-generated method stub
						return new Go.GoTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								classNameList.addAll(allClassNameByJar(uri, childPackage));
							}
						};
					}
				}));
			}
		});
		return classNameList;
	}
	
	private static final List<String> allClassNameByFile(URI filePath,  boolean childPackage){
		 List<String> classNameList = new ArrayList<String>(); 
		File file = new File(filePath);
		File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                if (childPackage) {  
                	classNameList.addAll(allClassNameByFile(childFile.toURI(),  childPackage));  
                }  
            } else {  
                String childFilePath = childFile.toURI().getPath();  
                if (childFilePath.endsWith(Suffix.CLASS)) {
                    childFilePath = childFilePath.substring(PATH.length(), childFilePath.lastIndexOf("."));  
                    childFilePath = childFilePath.replace(URL_SEPARATOR$, PCKG_SEPARATOR$);  
                    classNameList.add(childFilePath);  
                }  
            }  
        }  
		return classNameList;
	}
	private static final String identity = Suffix.JAR+IN_SEPARATOR$;
	private static final List<String> allClassNameByJar(URI filePath,  boolean childPackage){
		List<String> classNameList = new ArrayList<String>(); 
		String packagePath = ProtocolUtil.unProtocol(filePath);
		int index = packagePath.lastIndexOf(identity);
		String classPath = packagePath.substring(index+identity.length()+1);
		packagePath = packagePath.substring(0, index+identity.length()-1);
		File file = new File(packagePath);
		allClassNameByJar$(file, childPackage, classNameList, classPath);
		return classNameList;
	}
	@SuppressWarnings("resource")
	private static final void allClassNameByJar$(File file,boolean childPackage,List<String> classNameList,String classPath){
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entrys = jarFile.entries();  
            while (entrys.hasMoreElements()) {  
                JarEntry jarEntry = entrys.nextElement();  
                String entryName = jarEntry.getName();  
                if (entryName.endsWith(Suffix.CLASS)&&entryName.startsWith(classPath)) {
                    if (childPackage) { 
                        if (entryName.startsWith(classPath)) {  
                            entryName = entryName.replace(URL_SEPARATOR$, PCKG_SEPARATOR$).substring(0, entryName.lastIndexOf(Suffix.CLASS));  
                            classNameList.add(entryName);  
                        }  
                    } else {  
                        int index = entryName.lastIndexOf("/");  
                        String myPackagePath;  
                        if (index != -1) {  
                            myPackagePath = entryName.substring(0, index);  
                        } else {  
                            myPackagePath = entryName;  
                        }  
                        if (myPackagePath.equals(classPath)) {  
                            entryName = entryName.replace(URL_SEPARATOR$, PCKG_SEPARATOR$).substring(0, entryName.lastIndexOf(Suffix.CLASS));  
                            classNameList.add(entryName);  
                        }  
                    }  
                }  
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
}
