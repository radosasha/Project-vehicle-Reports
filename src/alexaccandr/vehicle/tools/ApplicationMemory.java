package alexaccandr.vehicle.tools;

import android.graphics.Bitmap;

public class ApplicationMemory {

	// комментарии излишни
	public static void eraseBitmapBuffer(Bitmap bt) {
		if (bt != null) {
			bt.recycle();
			bt = null;
			System.gc();
		}
	}

}
