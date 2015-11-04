package domini.Basic;

import dades.Table;

/**
 * Shows the different users ordered by different parameters
 */
public class Ranking {
    Table<Match> _matches;

    Ranking(Table<Match> m) {
        this._matches = m;
    }
}
