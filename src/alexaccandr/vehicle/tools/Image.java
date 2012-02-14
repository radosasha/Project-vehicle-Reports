package alexaccandr.vehicle.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image {
	//decodes image and scales it to reduce memory consumption
		public static Bitmap decodeFile(File file){
		    try {
		        //Decode image size
		        BitmapFactory.Options opt = new BitmapFactory.Options();
		        opt.inJustDecodeBounds = true;
		        BitmapFactory.decodeStream(new FileInputStream(file),null,opt);

		        //The new size we want to scale to
		        final int REQUIRED_SIZE=400;

		        //Find the correct scale value. It should be the power of 2.
		        int scale=1;
		        while(opt.outWidth/scale/2>=REQUIRED_SIZE && opt.outHeight/scale/2>=REQUIRED_SIZE)
		            scale*=2;

		        //Decode with inSampleSize
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize=scale;
		        return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
		    } catch (FileNotFoundException e) {
		    	return null;
		    }
		}
}
