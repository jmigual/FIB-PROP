package domini.BoardCreator;

import java.util.ArrayList;

/**
 * Created by arnau_000 on 05/11/2015.
 */
public class RegionShape {
    private int mId;
    private ArrayList<ArrayList<Boolean>> mShape;

    public RegionShape(int id){
        mId = id;
    }

    public int getId(){
        return  mId;
    }

    public ArrayList<ArrayList<Boolean>> getShape(){
        return mShape;
    }

    public void setShape(ArrayList<ArrayList<Boolean>> shape){
        mShape = shape;
    }
}
