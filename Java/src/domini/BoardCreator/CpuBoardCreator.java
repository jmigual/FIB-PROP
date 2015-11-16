package domini.BoardCreator;

import dades.Table;
import domini.Basic.Cell;
import domini.Basic.Region;
import domini.KKBoard;
import domini.KKRegion.KKRegion;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by arnau_000 on 05/11/2015.
 */
public class CpuBoardCreator extends BoardCreator {

    // Sizes weights
    private int mMaxRegionSize;
    private ArrayList<Integer> mSizesWeights;
    private int mTotalSizesWeight;

    // Operation Weights
    private int mAddWeight;
    private int mSubsWeight;
    private int mProdWeight;
    private int mDivWeight;
    private int mTotalOpWeight;


    public int getTotalOpWeight() { return mTotalOpWeight; }

    public int getTotalSizesWeight() { return mTotalSizesWeight; }

    public int getMaxRegionSize() {
        return mMaxRegionSize;
    }
    public void setMaxRegionSize(int MaxRegionSize) {
        while (mMaxRegionSize < MaxRegionSize){
            this.mSizesWeights.add(mMaxRegionSize, 0);
            ++mMaxRegionSize;
        }
        mMaxRegionSize = MaxRegionSize;
        mTotalSizesWeight = 0;
        for (int i = 0; i<mMaxRegionSize; ++i){
            mTotalSizesWeight += mSizesWeights.get(i);
        }
    }

    public int getAddWeight() {
        return mAddWeight;
    }
    public void setAddWeight(int AddWeight) {
        mTotalOpWeight += AddWeight - mAddWeight;
        this.mAddWeight = AddWeight;
    }

    public int getSubsWeight() {
        return mSubsWeight;
    }
    public void setSubsWeight(int SubsWeight) {
        mTotalOpWeight += SubsWeight - mSubsWeight;
        this.mSubsWeight = SubsWeight;
    }

    public int getProdWeight() {
        return mProdWeight;
    }
    public void setProdWeight(int ProdWeight) {
        mTotalOpWeight += ProdWeight - mProdWeight;
        this.mProdWeight = ProdWeight;
    }

    public int getDivWeight() {
        return mDivWeight;
    }
    public void setDivWeight(int DivWeight) {
        mTotalOpWeight += DivWeight - mDivWeight;
        this.mDivWeight = DivWeight;
    }

    public ArrayList<Integer> getSizesWeights() { return new ArrayList<>(mSizesWeights); }
    public void setSizeWeight(int size, int weight) throws Exception {
        if (size > mMaxRegionSize) {
            throw new Exception("CBC: Tried to set the weight of a region size with a size " +
                    "bigger than the maximum.");
        }
        mTotalSizesWeight += weight - mSizesWeights.get(size-1);
        this.mSizesWeights.set(size-1, weight);

    }

    public CpuBoardCreator(int size, Table<KKBoard> tableKKB){
        super(size, tableKKB);
        mMaxRegionSize = 5;
        mAddWeight = mSubsWeight = mProdWeight = mDivWeight = 10;
        mTotalOpWeight = 40;
        mSizesWeights = new ArrayList<Integer>(mMaxRegionSize);
        mSizesWeights.add(1-1,1);
        mSizesWeights.add(2-1,9);
        mSizesWeights.add(3-1,5);
        mSizesWeights.add(4-1,3);
        mSizesWeights.add(5-2,2);
        mTotalSizesWeight = 20;
    }

    /* 0 -> up
     * 1 -> down
     * 2 -> left
     * 3 -> right
     */
    private ArrayList<ArrayList<Boolean>> visitedCells;
    private int currentRegSize, currentCellCounter;
    private ArrayList<Cell> currentRegCells;
    private ArrayList<ArrayList<Cell>> regionsCells;

    private int getRandomRegionSize() {
        double r = mRand.nextDouble();
        double s=0;
        for (int i=0; i<mMaxRegionSize; ++i){
            s += mSizesWeights.get(i)/ (double) mTotalSizesWeight;
            if (r<s) return i;
        }
        try {
            throw new Exception("Internal CBC error: inconsistent mTotalSizesWeight");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 2;
    }

    private void DFS_v1(int i, int j) {
        if (visitedCells.get(i).get(j)) {
            return;
        }
        visitedCells.get(i).set(j,true);

        int rn = mRand.nextInt(4);
        for (int k=0; k<4; ++k){
            if ((rn+k)%4 == 0 && i - 1 >= 0) {
                DFS_v1(i - 1, j);
            } else if ((rn+k)%4 == 1 && i + 1 < mSize) {
                DFS_v1(i + 1, j);
            } else if ((rn+k)%4 == 2 && j - 1 >= 0) {
                DFS_v1(i, j - 1);
            } else if (j + 1 < mSize) {
                DFS_v1(i, j + 1);
            }
        }

        if (! (currentCellCounter <= currentRegSize && // to delete in v2
                (currentCellCounter == 0 ||
                Math.abs(currentRegCells.get(currentCellCounter - 1).getRow().getPos() - i) +
                Math.abs(currentRegCells.get(currentCellCounter - 1).getColumn().getPos() - j) == 1)
            )){
            // Create Region
            regionsCells.add(currentRegCells);
            // Start new region
            currentRegCells = new ArrayList<>();
            currentCellCounter = 0;
            currentRegSize = getRandomRegionSize(); // to delete in v2
        }
        currentRegCells.add(mBoard.getCell(i,j));
        ++currentCellCounter;

    }

    public void createBoard() throws Exception{
        if ((mDivWeight + mSubsWeight)/mTotalOpWeight > mSizesWeights.get(1) / mTotalSizesWeight) {
            throw new Exception("Division and subtraction relative weights cannot be higher than size=2 region weight.");
        }

        // Initialization
        mRand = new Random();
        newBoard();
        visitedCells = new ArrayList<>();
        for (int i=0; i<mSize; ++i){
            visitedCells.add(i, new ArrayList<>(mSize));
            for (int j=0; j<mSize; ++j){
                visitedCells.get(i).add(j, false);
            }
        }
        regionsCells = new ArrayList<>();
        int i = mRand.nextInt(mSize), j = mRand.nextInt(mSize);
        currentRegCells = new ArrayList<>();
        currentCellCounter = 0;
        currentRegSize = getRandomRegionSize();

        DFS_v1(i, j);
        // Create Region
        regionsCells.add(currentRegCells);

        // Fill the board with random numbers
        fillBoardWithRandomNumbers();

        // Create regions
        double s = mAddWeight / (double) (mAddWeight + mProdWeight);
        double d = mDivWeight / (double) mTotalOpWeight;
        double r = mSubsWeight / (double) mTotalOpWeight;
        double m2 = mSizesWeights.get(2 - 1) / (double) mTotalSizesWeight;
        mBoard.get_kkregions().remove(0);
        for (ArrayList<Cell> regCells : regionsCells) {
            double rand = mRand.nextDouble();
            int res;
            if (regCells.size() == 1){
                mBoard.createRegion(regCells, KKRegion.OperationType.NONE, regCells.get(0).getValue());
            } else if (regCells.size() == 2 && rand < r/m2){
                res = Math.max(regCells.get(0).getValue(), regCells.get(1).getValue()) -
                        Math.min(regCells.get(0).getValue(), regCells.get(1).getValue());
                mBoard.createRegion(regCells, KKRegion.OperationType.SUBTRACTION, res);
            } else if (regCells.size() == 2 && rand < d/m2){
                if (Math.max(regCells.get(0).getValue(), regCells.get(1).getValue()) %
                        Math.min(regCells.get(0).getValue(), regCells.get(1).getValue()) == 0) {
                    res = Math.max(regCells.get(0).getValue(), regCells.get(1).getValue()) /
                            Math.min(regCells.get(0).getValue(), regCells.get(1).getValue());
                    mBoard.createRegion(regCells, KKRegion.OperationType.DIVISION, res);
                }
            } else {
                rand = mRand.nextDouble();
                if (rand < s){
                    res = 0;
                    for (Cell c : regCells){
                        res += c.getValue();
                    }
                    mBoard.createRegion(regCells, KKRegion.OperationType.ADDITION, res);
                } else {
                    res = 1;
                    for (Cell c : regCells){
                        res *= c.getValue();
                    }
                    mBoard.createRegion(regCells, KKRegion.OperationType.PRODUCT, res);
                }
            }
        }

        // Erase the numbers... if not, which fun?
        clearBoard();
    }
}