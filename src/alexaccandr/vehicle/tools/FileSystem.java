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
}