package domini.BoardCreator;

import domini.Basic.Cell;
import domini.KKBoard;

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
            this.mSizesWeights.add(mMaxRegionSize,0);
        }
        mTotalSizesWeight = 0;
        for (int i : mSizesWeights){
            mTotalSizesWeight += i;
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
        if (size > mTotalSizesWeight) throw new Exception("CBC: Tried to set the weight of a region size with a size " +
                "bigger than the maximum.");
        this.mSizesWeights.add(size-1, weight);
    }

    public CpuBoardCreator(int size){
        super(size);
        mMaxRegionSize = 5;
        mAddWeight = mSubsWeight = mProdWeight = mDivWeight = 10;
        mTotalOpWeight = 40;
        mSizesWeights = new ArrayList<Integer>(mMaxRegionSize);
    }

    /* 0 -> up
     * 1 -> down
     * 2 -> left
     * 3 -> right
     */
    Random mRand;
    ArrayList<ArrayList<Boolean>> visitedCells;
    int currentRegSize, currentCellCounter;
    ArrayList<Integer> currentRegI, currentRegJ;
    ArrayList<Cell> currentRegCells;

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

    private void createRegion(){

    }

    private void DFS(int i, int j){
        visitedCells.get(i).set(j,true);

        int rn = mRand.nextInt(4);
        for (int k=0; k<4; ++k){
            if ((rn+k)%4 == 0 && !visitedCells.get(i).get(j) && i - 1 >= 0) {
                DFS(i - 1, j);
            } else if ((rn+k)%4 == 1 && i + 1 < mSize) {
                DFS(i + 1, j);
            } else if ((rn+k)%4 == 2 && j - 1 >= 0) {
                DFS(i, j - 1);
            } else if (j + 1 < mSize) {
                DFS(i, j + 1);
            }
        }

        if (currentCellCounter < currentRegSize) { // Add Cell to Reg
            if (currentCellCounter == 0 || Math.abs(currentRegI.get(currentCellCounter-1) - i) +
                    Math.abs(currentRegJ.get(currentCellCounter - 1)) == 1){ // We can add the Cell
                currentRegI.add(currentCellCounter, i);
                currentRegJ.add(currentCellCounter, j);
                ++currentCellCounter;
            } else {
                // Create Region
                currentRegSize = currentCellCounter;

                // Start new Region
            }
        } else {
            // Create Region

            // Start new region
            currentCellCounter = 0;
            currentRegSize = getRandomRegionSize();
            currentRegI = new ArrayList<>(currentRegSize);
            currentRegJ = new ArrayList<>(currentRegSize);
        }
    }

    public void createBoard() throws Exception{
        if ((mDivWeight + mSubsWeight)/mTotalOpWeight > mSizesWeights.get(1) / mTotalSizesWeight) {
            throw new Exception("Division and subtraction weights cannot be lower than size=2 region weight.");
        }

        mRand = new Random();

        mBoard = new KKBoard(mSize);
        visitedCells = new ArrayList<>(mSize);
        for (int i=0; i<mSize; ++i){
            visitedCells.set(i, new ArrayList<>(mSize));
            for (int j=0; j<mSize; ++j){
                visitedCells.get(i).set(j,false);
            }
        }

        int i = mRand.nextInt(mSize), j = mRand.nextInt(mSize);
        currentCellCounter = 0;
        currentRegSize = getRandomRegionSize();
        currentRegI = new ArrayList<>(currentRegSize);
        currentRegJ = new ArrayList<>(currentRegSize);

        DFS(i,j);


    }

}
