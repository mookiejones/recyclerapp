package android.com.solutions.nerd.testapp;

import android.com.solutions.nerd.testapp.model.Boat;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class BoatArrayAdapter extends RecyclerView.Adapter<BoatArrayAdapter.CustomBoatHolder> {
    private List<Boat> boatList;

    /**
     * Created by mookie on 10/30/15.
     * for Nerd.Solutions
     */
    public static  class CustomBoatHolder extends RecyclerView.ViewHolder{
        protected ImageView boatImage;
        protected TextView designerText;
        protected TextView titleText;
        protected TextView rigText;
        protected TextView firstYear;
        protected TextView lastYear;

        public CustomBoatHolder(View itemView) {
            super(itemView);
            this.titleText=(TextView)itemView.findViewById(R.id.title);
            this.boatImage=(ImageView)itemView.findViewById(R.id.boatThumbnail);
            this.rigText=(TextView)itemView.findViewById(R.id.rig_type);
            this.firstYear=(TextView)itemView.findViewById(R.id.first_year);
            this.lastYear=(TextView)itemView.findViewById(R.id.last_year);
            this.designerText=(TextView)itemView.findViewById(R.id.designer);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CustomBoatHolder onCreateViewHolder(ViewGroup viewGroup,int position){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boat_row_layout, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                flipCard();
            }
        });
        return new CustomBoatHolder(view);
    }

    private boolean mShowingBack;


    private final Context mContext;
    public BoatArrayAdapter(Context context,List<Boat> boatList){
        this.boatList=boatList;
        mContext=context;
    }


    @Override
    public void onBindViewHolder(final CustomBoatHolder customBoatHolder, int i) {
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
        customBoatHolder.titleText.setText(boatItem.getTitle());


        String img = boatItem.getImage(0);
        if (img!=null&&!img.isEmpty()){
            new ImageLoaderTask(new OnImageRetrieved() {
                @Override
                public void imageReceived(Bitmap bitmap) {
                    customBoatHolder.boatImage.setImageBitmap(bitmap);
                }
            }).execute(img);

        }

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
        void imageReceived(Bitmap bitmap);
    }
    public class ImageLoaderTask extends AsyncTask<String,Bitmap,Bitmap>{
        private  OnImageRetrieved mOnImageRetrieved;
        public ImageLoaderTask(OnImageRetrieved listener){
            mOnImageRetrieved=listener;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mOnImageRetrieved.imageReceived(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection urlConnection;
            try{
            String urlString = "http://sailsite.meteor.com/"+params[0]+".jpg";
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}
