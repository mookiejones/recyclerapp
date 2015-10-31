package android.com.solutions.nerd.testapp;

import android.com.solutions.nerd.testapp.model.Boat;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class BoatArrayAdapter extends RecyclerView.Adapter<CustomBoatHolder> {
    private final Context context;
    private List<Boat> boatList;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }


    @Override
    public CustomBoatHolder onCreateViewHolder(ViewGroup viewGroup,int position){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boat_row_layout, null);

        CustomBoatHolder viewHolder = new CustomBoatHolder(view);
        return viewHolder;
    }



    public BoatArrayAdapter(Context context,List<Boat> boatList){
        this.boatList=boatList;
        this.context=context;
    }


    @Override
    public void onBindViewHolder(CustomBoatHolder customBoatHolder, int i) {
        Boat boatItem = boatList.get(i);

        //Download image using picasso library
/*        Picasso.with(mContext).load(feedItem.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);*/

        //Setting text view title
//        customBoatHolder.boatImage.textView.setText(Html.fromHtml(feedItem.getTitle()));
        customBoatHolder.designerText.setText(boatItem.getDesigner());
        customBoatHolder.firstYear.setText(boatItem.getFirst_build());
        customBoatHolder.lastYear.setText(boatItem.getLast_build());
    }

    @Override
    public int getItemCount() {
        return (null != boatList ? boatList.size() : 0);
    }

    private void setText(final TextView view,final String value){
        view.setVisibility(value.isEmpty()?View.INVISIBLE:View.VISIBLE);
        view.setText(value);
    }

    public interface OnImageRetrieved{
        void imageRecieved(Bitmap bitmap);
    }
    public class ImageLoaderTask extends AsyncTask<String,Bitmap,Bitmap>{
        private  OnImageRetrieved mOnImageRetrieved;
        public ImageLoaderTask(OnImageRetrieved listener){
            mOnImageRetrieved=listener;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mOnImageRetrieved.imageRecieved(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try{
            String urlString = "http://sailsite.meteor.com/"+params[0]+".jpg";
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Bitmap result = BitmapFactory.decodeStream(in);
                return result;





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}
