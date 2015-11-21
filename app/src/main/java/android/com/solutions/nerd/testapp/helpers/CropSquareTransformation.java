package android.com.solutions.nerd.testapp.helpers;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by mookie on 11/21/15.
 * for Nerd.Solutions
 */
public class CropSquareTransformation implements Transformation {
    private static CropSquareTransformation mInstance;

    public static CropSquareTransformation getInstance() {
        if (mInstance == null)
            mInstance = new CropSquareTransformation();
        return mInstance;
    }

    /**
     * Transform the source bitmap into a new bitmap. If you create a new bitmap instance, you must
     * call {@link Bitmap#recycle()} on {@code source}. You may return the original
     * if no transformation is required.
     *
     * @param source
     */
    @Override
    public Bitmap transform(Bitmap source) {

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    /**
     * Returns a unique key for the transformation, used for caching purposes. If the transformation
     * has parameters (e.g. size, scale factor, etc) then these should be part of the key.
     */
    @Override
    public String key() {
        return "square()";

    }
}
