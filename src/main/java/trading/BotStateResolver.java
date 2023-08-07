package trading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotStateResolver {

    //Avoid using division to prevent type error if constant changes
    public static final int WIN_REWARD = 2;
    public static final int TIE_REWARD = 1;
    public static final int LOSS_REWARD = 0;

    private static final Logger logger = LogManager.getLogger();

    public static void resolveBids(int ownCash, int otherCash, BotState ownState, BotState otherState) {

        int ownAward = LOSS_REWARD;
        int otherAward = LOSS_REWARD;

        if(ownCash == otherCash){

            ownAward = TIE_REWARD;
            otherAward = TIE_REWARD;

        }else if(ownCash > otherCash){

            ownAward = WIN_REWARD;

        }else {

            otherAward = WIN_REWARD;

        }

        int totalPayout = ownAward + otherAward;

        try{
            ownState.update(
                    ownAward,
                    ownCash,
                    totalPayout
            );

            otherState.update(
                    otherAward,
                    otherCash,
                    totalPayout
            );

        }catch(IllegalArgumentException e){

            logger.error(e.getMessage());

        }

    }

}
