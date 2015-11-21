package android.com.solutions.nerd.testapp.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by mookie on 11/21/15.
 * for Nerd.Solutions
 */
public class PicassoImage extends ImageView {

    protected static int width;
    private final Context mContext;
    private String mUrl;

    public PicassoImage(Context context) {
        super(context);
        mContext = context;
        initialize();

    }

    public PicassoImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();

    }

    public PicassoImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize();
    }

    public PicassoImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initialize();
    }

    /**
     * Manually render this view (and all of its children) to the given Canvas.
     * The view must have already done a full layout before this function is
     * called.  When implementing a view, implement
     * {@link #onDraw(Canvas)} instead of overriding this method.
     * If you do need to override this method, call the superclass version.
     *
     * @param canvas The Canvas to which the View is rendered.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void initialize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
    }

    public void setImageUrl(final String url) {
        mUrl = url;
        // Get Window width
        Picasso
                .with(getContext())
                .load(url)
                .transform(CropSquareTransformation.getInstance())

//                .transform(CropSquareTransformation.getInstance())
                .into(this);
    }


    /**
     * Created by mookie on 11/21/15.
     * for Nerd.Solutions
     */
    public static class CropSquareTransformation implements Transformation {
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
            int Height = source.getHeight();
            int Width = source.getWidth();
            int newHeight = width;
            int newWidth = width;
            float scaleHeight = ((float) newHeight) / Height;
            float scaleWidth = ((float) newWidth) / Width;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(source, 0, 0, Width, Height, matrix, true);
            if (resizedBitmap != source) {
                source.recycle();
            }
            return resizedBitmap;

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
}
