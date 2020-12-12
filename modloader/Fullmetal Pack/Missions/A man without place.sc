SCRIPT_START
{
NOP
WAIT 0

LVAR_INT scplayer tblip[6] enem[6] area i yakmodel walkany task_status boat
LVAR_FLOAT x y z enemX enemY genX genY genX2 genY2
GET_PLAYER_CHAR 0 scplayer
i = 0
RESET_NUM_OF_MODELS_KILLED_BY_PLAYER 0
main_loop:
IF LOCATE_CHAR_ANY_MEANS_2D scplayer 2488.562 -1666.865 5.0 5.0 1
    GOTO cutscene1
ENDIF
GOTO main_loop

cutscene1:
DO_FADE 2000 0
LOAD_SCENE -6145.6128, -2817.6257, 25.1926
SET_PLAYER_CONTROL 0 FALSE
DISPLAY_HUD 0
DISPLAY_RADAR 0
SET_FIXED_CAMERA_POSITION -6144.8662, -2818.2834, 25.4476 0.0 0.0 0.0
POINT_CAMERA_AT_POINT -6145.6128, -2817.6257, 25.1926 2
CLEAR_HELP
WAIT 4000
DO_FADE 2000 1
PRINT_STRING_NOW "Your first mission as a mercenary in San Andreas:" 5000
WAIT 5000
PRINT_STRING_NOW "Dominate this artificial island!" 5000
WAIT 5000

barco:
DO_FADE 2000 0
    WAIT 2000
    LOAD_SCENE  -6210.00000, -2575.00000, 3.00000
    SET_CHAR_COORDINATES scplayer -7495.3467, -2107.3018, -100.0
    REQUEST_MODEL 358       //SNIPER
        REQUEST_MODEL 446       //SQUALO
        REQUEST_MODEL 355       //AK
        REQUEST_MODEL 347       //silenced
        REQUEST_MODEL 335       //FACA
        REQUEST_MODEL 14200
        REQUEST_MODEL 14201
        REQUEST_MODEL 14202
        LOAD_ALL_MODELS_NOW
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_PISTOL_SILENCED 100
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_SNIPERRIFLE 40
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_KNIFE 1
    CREATE_CAR 446 -7495.3467, -2107.3018, -100.0 boat
    SET_CAR_HEADING boat -90.0
    TASK_WARP_CHAR_INTO_CAR_AS_DRIVER scplayer boat
    ADD_SPRITE_BLIP_FOR_COORD -6501.1396, -2691.6292, 1.0342 13 tblip[0]
    SET_PLAYER_CONTROL 0 TRUE
    RESTORE_CAMERA_JUMPCUT
    DISPLAY_HUD 1
    DISPLAY_RADAR 1
    WAIT 2000
DO_FADE 2000 1


jumped:
WAIT 0
    //IF LOCATE_CHAR_ANY_MEANS_3D scplayer -6501.1396, -2691.6292, 1.0342 (15.0 15.0 10.0) TRUE
    IF IS_CHAR_IN_AREA_2D scplayer -6373.9746, -2723.2822 -6545.8320, -2570.4727 1
        REMOVE_BLIP tblip[0]
        GOTO taskzin
        WHILE LOCATE_CHAR_ANY_MEANS_3D scplayer -6501.1396, -2691.6292, 1.0342 (5.0 5.0 3.0) TRUE
            WAIT 0
        ENDWHILE
    ENDIF
GOTO jumped     //14200 14201 14202 YAKUZAS

taskzin:
    OPEN_SEQUENCE_TASK walkany

        
        SWITCH task_status
            DEFAULT
                TASK_LOOK_ABOUT -1 60000
                BREAK
            CASE 1
                GENERATE_RANDOM_FLOAT_IN_RANGE -6361.9932 -6178.9521 genX
                GENERATE_RANDOM_FLOAT_IN_RANGE -2799.3613 -2412.9363 genY
                TASK_GO_STRAIGHT_TO_COORD -1 genX genY -100.0 4 -2
                BREAK
            CASE 2
                SET_NEXT_DESIRED_MOVE_STATE 4
                TASK_GOTO_CHAR -1 scplayer -2 3.0
                BREAK
        ENDSWITCH
    CLOSE_SEQUENCE_TASK walkany
GOTO pousou

pousou:
WAIT 0

    IF i < 5
        genX = -6339.2778
        genY = -2762.0740
        genX2 = -6209.9048
        genY2 = -2635.0081
        area = 1
        GOSUB gerar_yakuza
    ENDIF
    
    IF i >= 5
        GOSUB spottloop
    ENDIF
GOTO pousou

area2:
WAIT 0
    IF i < 5
        genX = -6342.7031
        genY = -2628.0930
        genX2 = -6209.6919
        genY2 = -2506.4958
        area = 2
        GOSUB gerar_yakuza
    ENDIF

    IF i >= 5
        GOSUB spottloop
    ENDIF
GOTO area2

area3:
WAIT 0
    IF i < 5
        genX = -6341.3057
        genY = -2485.0156
        genX2 = -6238.0776
        genY2 = -2415.3289
        area = 3
        GOSUB gerar_yakuza
    ENDIF

    IF i >= 5
        GOSUB spottloop
    ENDIF
GOTO area3

area4:
    GOTO end
GOTO area4

GOTO end
//--------------------------------------------------------------
gerar_yakuza:
WAIT 0
GENERATE_RANDOM_FLOAT_IN_RANGE genX genX2 enemX
GENERATE_RANDOM_FLOAT_IN_RANGE genY genY2 enemY
GENERATE_RANDOM_INT_IN_RANGE 14200 14202 yakmodel
CREATE_CHAR PEDTYPE_GANG9 yakmodel enemX enemY -100.0 enem[i]
ADD_BLIP_FOR_CHAR enem[i] tblip[i]
GIVE_WEAPON_TO_CHAR enem[i] WEAPONTYPE_AK47 1000
SET_CHAR_ACCURACY enem[i] 70
GENERATE_RANDOM_INT_IN_RANGE 0 3 task_status
PERFORM_SEQUENCE_TASK enem[i] walkany
i = i + 1
RETURN

spottloop:
    i = 0
    WHILE i < 5
        IF HAS_CHAR_SPOTTED_CHAR_IN_FRONT enem[i] scplayer
            GET_SCRIPT_TASK_STATUS enem[i] 0x74D task_status
            IF IS_CHAR_HEALTH_GREATER enem[i] 0
            AND task_status = 7
                TASK_SHOOT_AT_CHAR enem[i] scplayer -2
            ENDIF
        ENDIF

        IF IS_CHAR_DEAD enem[0]
        AND IS_CHAR_DEAD enem[1]
        AND IS_CHAR_DEAD enem[2]
        AND IS_CHAR_DEAD enem[3]
        AND IS_CHAR_DEAD enem[4]
            MARK_CHAR_AS_NO_LONGER_NEEDED enem[0]
            MARK_CHAR_AS_NO_LONGER_NEEDED enem[1]
            MARK_CHAR_AS_NO_LONGER_NEEDED enem[2]
            MARK_CHAR_AS_NO_LONGER_NEEDED enem[3]
            MARK_CHAR_AS_NO_LONGER_NEEDED enem[4]
            REMOVE_CHAR_ELEGANTLY enem[0]
            REMOVE_CHAR_ELEGANTLY enem[1]
            REMOVE_CHAR_ELEGANTLY enem[2]
            REMOVE_CHAR_ELEGANTLY enem[3]
            REMOVE_CHAR_ELEGANTLY enem[4]
            area++
            i = 0
            SWITCH area
                CASE 2
                    GOTO area2
                BREAK
                CASE 3
                    GOTO area3
                BREAK
                CASE 4
                    GOTO area4
                BREAK
            ENDSWITCH
        ENDIF
        i = i + 1
    ENDWHILE
RETURN

end:
PRINT_STRING_NOW "END" 5000
}
SCRIPT_END