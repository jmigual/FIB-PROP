package domini;

import domini.Basic.Board;
import domini.Basic.Region;
import domini.KKRegion.KKRegion;

import java.util.ArrayList;

/**
 * Created by Joan on 20/10/2015.
 */
public class KKBoard extends Board {

    KKBoard(int size) { super(size); }

    @Override
    public Board getSolution() {
        return null;
    }

    @Override
    public void solve() {

    }

    @Override
    public boolean hasSolution() {
        return false;
    }

    public ArrayList<Region> get_regions() {
        return _regions;
    }
    public void set_regions(ArrayList<KKRegion> regions) {
        this._regions = (ArrayList<Region>) ((ArrayList<?>) regions);
    }
    private ArrayList<Region> _regions;
}
