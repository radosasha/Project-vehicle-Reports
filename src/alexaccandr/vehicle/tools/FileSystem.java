package alexaccandr.vehicle.tools;

import java.io.File;

public class FileSystem {
	
	// рекурсивно из указанной папки все файлы + подкаталоги
	public static void deleteFolder(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				deleteFolder(child);
		fileOrDirectory.delete();
	}
	
	/*
	 * создает директорию в файловой системе
	 * @param dir директория которую необходимо создать
	 * @return true если создаение каталога прошло успешно, 
	 * false - если директория существует или не может быть создана
	 */
	public static boolean createDirectory(String dir){
		return new File(dir).mkdirs();
	}
	
	// возвращает список всех файлов по указанной директории
	public static String[] getFilesList(String inDir){
		return new File(inDir).list();
	}
	
	// удалить файл
	public static void removeFile(String path){
		File file = new File(path);
		file.delete();
	}

}
