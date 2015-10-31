package android.com.solutions.nerd.testapp.model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class Boat  {
    private static final String TAG=Boat.class.getSimpleName();

    private String title="";
    private String construct="";
    private String rig_type="";
    private String lwl="";
    private String ballast="";
    private String designer="";
    private String first_build="";
    private String hull="";
    private String beam="";
    private String sa_fore="";
    private String e="";
    private String i="";
    private String j="";
    private String p="";
    private String last_build="";

    public static final String TAG_MAKE="make";
    private String make="";

    public static final String TAG_LAST_YEAR="last_built";
    public String getLast_build(){return last_build;}

    private String disp="";
    public static final String TAG_DISP="disp";
    public String getDisp() {return disp;}

    public static final String TAG_SA_DISP="sa_disp";
    private String sa_disp="";
    public String getSa_disp() {return sa_disp;}


    public static final String TAG_SA_LIST="sa_list";
    private String sa_list="";
    public String getSa_list() {return sa_list;}


    public static final String TAG_DRAFT_MAX="draft_max";
    private String draft_max="";
    public String getDraft_max() {return draft_max;}


    private String total_calc="";
    public static final String TAG_TOTAL_CALC="total_calc";
    public String getTotal_calc() {return total_calc;}


    public static final String TAG_LOA="loa";
    private String loa="";
    public String getLoa() {return loa;}


    public static final String TAG_TITLE="title";
    public String getTitle() {return title;}

    public static final String TAG_BAL_DISP="bal_disp";
    private String bal_disp="";
    public String getBal_disp() {return bal_disp;}

    public static final String TAG_CONSTRUCT="construct";
    public String getConstruct() {return construct;}

    public static final String TAG_RIG_TYPE="rig_type";
    public String getRig_type() {return rig_type;}

    public static final String TAG_LWL="lwl";
    public String getLwl() {return lwl;}

    public static final String TAG_BALLAST="ballast";
    public String getBallast() {return ballast;}

    public static final String TAG_DESIGNER="designer";
    public String getDesigner() {return designer;}

    public static final String TAG_FIRST_BUILD="first_built";
    public String getFirst_build() {        return first_build;    }

    public static final String TAG_HULL="hull";
    public String getHull() {        return hull;}

    public static final String TAG_BEAM="beam";
    public String getBeam() {
        return beam;
    }
    public static final String TAG_SA_FORE="sa_fore";

    public String getSa_fore() {
        return sa_fore;
    }


    public static final String TAG_E="e";
    public String getE() {
        return e;
    }



    public static final String TAG_I="i";
    public String getI() {
        return i;
    }


    public static final String TAG_J="j";
    public String getJ() {
        return j;
    }


    public static final String TAG_P="p";
    public String getP() {
        return p;
    }

    public static final String TAG_NUM_BUILT="num_built";
    private String num_built="";
    public String getNumBuilt(){return num_built;}

    public static final String TAG_BAL_TYPE="bal_type";
    private String bal_type="";
    public String getBalType(){return bal_type;}

    public static final String TAG_FUEL="fuel";
    private String fuel="";
    public String getFuel(){return fuel;}


    public static final String TAG_TYPE="type";
    private String type="";
    public String getType(){return type;}


    public static final String TAG_WATER="water";
    public String water="";
    public String getWater(){return water;}

    public static final String TAG_HP="hp";
    public String hp="";
    public String getHP(){return hp;}

    public static final String TAG_MAST_HEIGHT="mast_height";
    public String mast_height="";
    public String getMastHeight(){return mast_height;}


    public String[] images;
    public static final String TAG_IMAGES="images";
    public String[] getImages(){return images;}

    public String getImage(int position){return images[position];}

    public Boat(JSONObject boat){
        try {

            if (boat.has(TAG_IMAGES))
            {
                JSONArray imageArray=boat.getJSONArray(TAG_IMAGES);
                List<String> tmpImages = new ArrayList<>();
                for(int i=0;i<imageArray.length();i++){
                    String img = imageArray.get(i).toString();
                    tmpImages.add(img);
                }
                images = tmpImages.toArray(new String[tmpImages.size()]);
            }

            if (boat.has(TAG_MAST_HEIGHT))
                mast_height=boat.getString(TAG_MAST_HEIGHT);

            if (boat.has(TAG_HP))
                hp=boat.getString(TAG_HP);

            if (boat.has(TAG_WATER))
                water=boat.getString(TAG_WATER);

            if (boat.has(TAG_TYPE))
                type=boat.getString(TAG_TYPE);


            if (boat.has(TAG_FUEL))
                fuel=boat.getString(TAG_FUEL);

            if (boat.has(TAG_BAL_TYPE))
                bal_type=boat.getString(TAG_BAL_TYPE);

            if (boat.has(TAG_NUM_BUILT))
                num_built=boat.getString(TAG_NUM_BUILT);

            if (boat.has(TAG_BAL_DISP))
                bal_disp=boat.getString(TAG_BAL_DISP);

            if (boat.has(TAG_BALLAST))
                ballast=boat.getString(TAG_BALLAST);

            if(boat.has(TAG_P))
                p=boat.getString(TAG_P);

            if(boat.has(TAG_J))
                j=boat.getString(TAG_J);

            if(boat.has(TAG_E))
                e=boat.getString(TAG_E);

            if(boat.has(TAG_I))
                i=boat.getString(TAG_I);

            if(boat.has(TAG_SA_FORE))
                sa_fore=boat.getString(TAG_SA_FORE);

            if(boat.has(TAG_HULL))
                hull=boat.getString(TAG_HULL);

            if(boat.has(TAG_BEAM))
                beam=boat.getString(TAG_BEAM);

            if(boat.has(TAG_FIRST_BUILD))
                first_build=boat.getString(TAG_FIRST_BUILD);

            if (boat.has(TAG_LAST_YEAR))
                last_build=boat.getString(TAG_LAST_YEAR);


            if(boat.has(TAG_CONSTRUCT))
                construct=boat.getString(TAG_CONSTRUCT);

            if(boat.has(TAG_RIG_TYPE))
                rig_type=boat.getString(TAG_RIG_TYPE);

            if(boat.has(TAG_DESIGNER))
                designer=boat.getString(TAG_DESIGNER);

            if(boat.has(TAG_BALLAST))
                ballast=boat.getString(TAG_BALLAST);

            if(boat.has(TAG_LWL))
                lwl=boat.getString(TAG_LWL);

            if(boat.has(TAG_RIG_TYPE))
                rig_type=boat.getString(TAG_RIG_TYPE);

            if (boat.has(TAG_TITLE))
                title=boat.getString(TAG_TITLE);

            if (boat.has(TAG_LOA))
                loa=boat.getString(TAG_LOA);

            if (boat.has(TAG_MAKE))
                make=boat.getString(TAG_MAKE);



        } catch (JSONException e1) {
            e1.printStackTrace();
        }




    }
}
