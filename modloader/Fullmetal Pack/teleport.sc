SCRIPT_START
{

LVAR_INT scplayer onMission
LVAR_FLOAT x y z

GET_PLAYER_CHAR 0 scplayer

main_loop:
WAIT 0

READ_MEMORY 0x96ABA0 1 0 onMission

IF onMission = FALSE
//AND NOT IS_CHAR_IN_ANY_CAR scplayer
    IF LOCATE_CHAR_ANY_MEANS_3D scplayer 2471.3259, -1686.9136, 12.5049 (2.0 2.0 2.0) TRUE
        //IF GOSUB iniciar
            //TERMINATE_THIS_CUSTOM_SCRIPT
        //ENDIF
        LOAD_SCENE -2417.495117, -2478.440918, 3.000000
        SET_AREA_VISIBLE 9
        SET_CHAR_AREA_VISIBLE scplayer 9
        SET_CHAR_COORDINATES scplayer -6580.0254, -2585.6079, 702.1843  //-6210.000000, -2575.000000, 6.000000
        GIVE_WEAPON_TO_CHAR scplayer 46 1
    ENDIF
ENDIF

IF IS_KEY_PRESSED VK_KEY_0
    SET_AREA_VISIBLE 0
    SET_CHAR_AREA_VISIBLE scplayer 0
ENDIF

IF IS_KEY_PRESSED VK_KEY_9
    SET_AREA_VISIBLE 9
    SET_CHAR_AREA_VISIBLE scplayer 9
ENDIF

GOTO main_loop

iniciar:
    LOAD_AND_LAUNCH_CUSTOM_MISSION "MISSION_Furgon"
    DUMP
    00
    ENDDUMP
RETURN

}
SCRIPT_END