package example.test.loginandsignup.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageResizeUtils {
    public static void resizeFile (File file, File newFile, int newWidth, Boolean isCamera){
        //이미지 크기를 좀 맞출려고 만든 파일입니다. 크게 중요하진 않습니다. 하면 예쁘겠다 생각해서 해봤습니다.
        String TAG = "Item";

        Bitmap originalBm = null;
        Bitmap resizedBitmap = null;

        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inDither = true;

            originalBm = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            if(isCamera){
                try{
                    ExifInterface exif = new ExifInterface(file.getAbsoluteFile());
                    int exifOrientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int exifDegree = exifOrientationToDegrees(exifOrientation);
                    Log.d(TAG,"exifDegree : " + exifDegree);

                    originalBm = rotate(originalBm, exifDegree);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(originalBm == null){
                Log.e(TAG,("file error"));
                return;
            }

            int width = originalBm.getWidth();
            int height = originalBm.getHeight();

            float aspect, scaleWidth, scaleHeight;
            if(width > height){
                if(width <= newWidth) return;

                aspect = (float) width / height;

                scaleWidth = newWidth;
                scaleHeight = scaleWidth / aspect;
            } else {
                if(height <= newWidth) return;
                aspect = (float) height / width;
                scaleHeight = newWidth;
                scaleWidth = scaleHeight / aspect;
            }
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth/width, scaleHeight / height);
            resizedBitmap = Bitmap.createBitmap(originalBm, 0, 0, width, height, matrix, true);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, new FileOutputStream(newFile));
            }else{
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 80, new FileOutputStream(newFile));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(originalBm != null){
                originalBm.recycle();
            }
            if (resizedBitmap != null){
                resizedBitmap.recycle();
            }
        }
    }
    public static int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) return 90;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) return 180;
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) return 270;
        return 0;
    }
    public static Bitmap rotate(Bitmap bitmap, int degrees){
        if(degrees != 0 && bitmap != null){
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() /2,
                    (float) bitmap.getHeight() / 2);
            try{
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap!=converted){
                    bitmap.recycle();;
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }
}