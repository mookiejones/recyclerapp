package android.com.solutions.nerd.testapp.boat;

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

    public static final String TAG_DISPLACEMENT ="disp";
    public static final String TAG_SA_DISPLACEMENT ="sa_disp";
    public static final String TAG_SA_LIST="sa_list";
    public static final String TAG_DRAFT_MAX="draft_max";
    public static final String TAG_TOTAL_CALC="total_calc";
    public static final String TAG_LOA="loa";
    public static final String TAG_TITLE="title";
    public static final String TAG_BAL_DISP="bal_disp";
    public static final String TAG_CONSTRUCT="construct";
    public static final String TAG_RIG_TYPE="rig_type";
    public static final String TAG_LWL="lwl";
    public static final String TAG_BALLAST="ballast";
    public static final String TAG_DESIGNER="designer";
    public static final String TAG_FIRST_BUILD="first_built";
    public static final String TAG_HULL="hull";
    public static final String TAG_BEAM="beam";
    public static final String TAG_SA_FORE="sa_fore";
    public static final String TAG_E="e";
    public static final String TAG_I="i";
    public static final String TAG_J="j";
    public static final String TAG_P="p";
    public static final String TAG_NUM_BUILT="num_built";
    public static final String TAG_BAL_TYPE="bal_type";
    public static final String TAG_FUEL="fuel";
    public static final String TAG_TYPE="type";
    public static final String TAG_WATER="water";
    public static final String TAG_HP="hp";
    public static final String TAG_MAST_HEIGHT="mast_height";
    public static final String TAG_IMAGES="images";
    private static final String TAG=Boat.class.getSimpleName();

    public String getBallast() {
        return ballast;
    }

    public String getBeam() {
        return beam;
    }

    public String getBuilder() {
        return builder;
    }

    public String getConstruct() {
        return construct;
    }

    public String getDesigner() {
        return designer;
    }

    public String getDisp() {
        return disp;
    }

    public String getDraft_min() {
        return draft_min;
    }

    public String getE() {
        return e;
    }

    public String getEy() {
        return ey;
    }

    public String getFirst_built() {
        return first_built;
    }

    public String getHp() {
        return hp;
    }

    public String getHull() {
        return hull;
    }

    public String getI() {
        return i;
    }

    public String[] getImages() {
        return images;
    }

    public String getIsp() {
        return isp;
    }

    public String getJ() {
        return j;
    }

    public String getLast_built() {
        return last_built;
    }

    public String getLwl() {
        return lwl;
    }

    public String getMake() {
        return make;
    }

    public String getMast_height() {
        return mast_height;
    }

    public String getModel() {
        return model;
    }

    public String getMore() {
        return more;
    }

    public String getP() {
        return p;
    }

    public String getPy() {
        return py;
    }

    public String getRig_type() {
        return rig_type;
    }

    public String getSa_fore() {
        return sa_fore;
    }

    public String getSpl() {
        return spl;
    }

    public String getTitle() {
        return title;
    }

    public String getWater() {
        return water;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getSa_disp() {
        return sa_disp;
    }

    public String getSa_list() {
        return sa_list;
    }

    public String getDraft_max() {
        return draft_max;
    }

    public String getTotal_calc() {
        return total_calc;
    }

    public String getLoa() {
        return loa;
    }

    public String getBal_disp() {
        return bal_disp;
    }

    public String getNum_built() {
        return num_built;
    }

    public String getBal_type() {
        return bal_type;
    }

    public String getFuel() {
        return fuel;
    }

    public String getType() {
        return type;
    }

    private String ballast ="";
    private String beam ="";
    private String builder ="";
    private String construct ="";
    private String designer ="";
    private String disp ="";

    private String draft_min ="";
    private String e ="";
    private String ey ="";
    private String first_built ="";
    private String hp ="";
    private String hull ="";
    private String i ="";
    private String[] images;
    private String isp ="";
    private String j ="";
    private String last_built ="";
    private String lwl ="";
    private String make ="";
    private String mast_height ="";
    private String model ="";
    private String more ="";

    private String p ="";
    private String py ="";
    private String rig_type ="";
    private String sa_fore ="";
    private String spl ="";
    private String title ="";
    private String water ="";
    private String displacement ="";
    private String sa_disp="";
    private String sa_list="";
    private String draft_max="";
    private String total_calc="";
    private String loa="";
    private String bal_disp="";
    private String num_built="";
    private String bal_type="";
    private String fuel="";
    private String type="";



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


            if(boat.has(TAG_BAL_DISP))
                bal_disp=boat.getString(TAG_BAL_DISP);

            if(boat.has(TAG_BAL_TYPE))
                bal_type=boat.getString(TAG_BAL_TYPE);

            if(boat.has(TAG_BALLAST))
                ballast=boat.getString(TAG_BALLAST);


            if(boat.has(TAG_BEAM))
                beam=boat.getString(TAG_BEAM);

            if(boat.has(TAG_CONSTRUCT))
                construct=boat.getString(TAG_CONSTRUCT);

            if(boat.has(TAG_DESIGNER))
                designer=boat.getString(TAG_DESIGNER);

            if(boat.has(TAG_DISPLACEMENT))
                displacement=boat.getString(TAG_DISPLACEMENT);

            if(boat.has(TAG_DRAFT_MAX))
                draft_max=boat.getString(TAG_DRAFT_MAX);
            if (boat.has(TAG_E))
                e=boat.getString(TAG_E);
            if(boat.has(TAG_FIRST_BUILD))
                first_built=boat.getString(TAG_FIRST_BUILD);

            if(boat.has(TAG_FUEL))
                fuel=boat.getString(TAG_FUEL);

            if(boat.has(TAG_HP))
                hp=boat.getString(TAG_HP);

            if(boat.has(TAG_HULL))
                hull=boat.getString(TAG_HULL);

            if(boat.has(TAG_I))
                i=boat.getString(TAG_I);

            if(boat.has(TAG_J))
                j=boat.getString(TAG_J);

            if(boat.has(TAG_LOA))
                loa=boat.getString(TAG_LOA);

            if(boat.has(TAG_LWL))
                lwl=boat.getString(TAG_LWL);

            if(boat.has(TAG_MAST_HEIGHT))
                mast_height=boat.getString(TAG_MAST_HEIGHT);

            if(boat.has(TAG_NUM_BUILT))
                num_built=boat.getString(TAG_NUM_BUILT);

            if(boat.has(TAG_P))
                p=boat.getString(TAG_P);

            if(boat.has(TAG_RIG_TYPE))
                rig_type=boat.getString(TAG_RIG_TYPE);

            if(boat.has(TAG_SA_DISPLACEMENT))
                sa_disp=boat.getString(TAG_SA_DISPLACEMENT);

            if(boat.has(TAG_SA_FORE))
                sa_fore=boat.getString(TAG_SA_FORE);
            if(boat.has(TAG_SA_LIST))
                sa_list=boat.getString(TAG_SA_LIST);

            if(boat.has(TAG_TITLE))
                title=boat.getString(TAG_TITLE);

            if(boat.has(TAG_TOTAL_CALC))
                total_calc=boat.getString(TAG_TOTAL_CALC);

            if(boat.has(TAG_TYPE))
                type=boat.getString(TAG_TYPE);

            if(boat.has(TAG_WATER))
                water=boat.getString(TAG_WATER);



        } catch (JSONException e1) {
            e1.printStackTrace();
        }




    }


    public String getImage(int position){

        if (images==null)
            return "";
        return images[position];
    }
}
