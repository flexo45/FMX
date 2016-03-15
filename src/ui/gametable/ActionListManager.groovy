package ui.gametable

class ActionListManager {

    final static String OPEN_DOOR = 'Open Door'
    final static String GET_DOOR = 'Get Door'
    final static String SELECT_RACE = 'Select Race'
    final static String SELECT_CLASS = 'Select Class'
    final static String NEXT_TURN = 'Next Turn'
    final static String SAVE = 'Save game'
    final static String GET_CARD_FROM_STACK = 'Get card to hand'
    final static String USE_ITEM = 'Use item'
    final static String CAST_SPELL = 'Cast spell'
    final static String NEXT_BATTLE_ROUND = 'Continue battle'
    final static String TRY_RUN = 'Try to run'
    final static String GET_CURSE = 'Get curse'
    final static String FIGHT_WITH_HAND_MON = 'Fight with monster from hand'
    final static String NEXT = 'Continue'
    final static String GET_GOLD = 'Winner actions'
    final static String GET_FAIL = 'Loser actions'
    final static String EQUIP_ITEM = 'Equip item'
    final static String WAIT = 'Wait!'

    final static int FIRST_ROUND_BEGIN = 100;
    final static int FIRST_ROUND_END = 120;

    final static int FIGHT = 300;
    final static int FIGHT_WIN = 310;
    final static int FIGHT_FAIL = 320;
    final static int FIGHT_END = 340;

    final static int CURSE = 400;

    final static int SECOND_ROUND_BEGIN = 200;
    final static int SECOND_ROUND_END = 220;

    final static int OPPONENT_ROUND_BEGIN = 900;
    final static int OPPONENT_CONTINUE = 1000;

    public static List<String> getActions(int situation){
        List<String> list = []

        switch (situation){
            case FIRST_ROUND_BEGIN:
                list.add(OPEN_DOOR)
                list.add(SELECT_RACE)
                list.add(SELECT_CLASS)
                list.add(EQUIP_ITEM)
                list.add(SAVE)
                break
            case FIGHT:
                list.add(USE_ITEM)
                list.add(CAST_SPELL)
                list.add(NEXT_BATTLE_ROUND)
                list.add(TRY_RUN)
                break
            case CURSE:
                list.add(USE_ITEM)
                list.add(CAST_SPELL)
                list.add(GET_CURSE)
                break
            case FIRST_ROUND_END:
                list.add(GET_CARD_FROM_STACK)
                break
            case SECOND_ROUND_BEGIN:
                list.add(GET_DOOR)
                list.add(FIGHT_WITH_HAND_MON)
                list.add(SELECT_RACE)
                list.add(SELECT_CLASS)
                list.add(EQUIP_ITEM)
                break
            case SECOND_ROUND_END:
                list.add(NEXT_TURN)
                list.add(SELECT_RACE)
                list.add(SELECT_CLASS)
                list.add(EQUIP_ITEM)
                break
            case FIGHT_WIN:
                list.add(GET_GOLD)
                break
            case FIGHT_FAIL:
                list.add(GET_FAIL)
                break
            case FIGHT_END:
                list.add(NEXT_TURN)
                list.add(SELECT_RACE)
                list.add(SELECT_CLASS)
                list.add(EQUIP_ITEM)
                break
            case OPPONENT_ROUND_BEGIN:
                list.add(WAIT)
                break
            case OPPONENT_CONTINUE:
                list.add(NEXT)
                break
        }

        return list
    }
}
