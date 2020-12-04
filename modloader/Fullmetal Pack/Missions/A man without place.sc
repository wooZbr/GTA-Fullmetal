SCRIPT_START
{
NOP
WAIT 0

LVAR_INT scplayer tblip[6] enem[6] area i yakmodel rped task_status
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

aviao:
DO_FADE 1000 0
    WAIT 2000
    //SET_AREA_VISIBLE 9
    SET_CHAR_AREA_VISIBLE scplayer 9
    LOAD_SCENE -6555.7251 -2599.7314 995.7842
    SET_CHAR_COORDINATES scplayer -6555.7251 -2599.7314 995.7842
    SET_FIXED_CAMERA_POSITION -6552.6548, -2601.7776, 996.5732 0.0 0.0 0.0
    POINT_CAMERA_AT_POINT -6553.5361, -2601.3079, 996.4183 2
        REQUEST_MODEL 46        //parachute
        REQUEST_MODEL 358       //SNIPER
        REQUEST_MODEL 355       //AK
        REQUEST_MODEL 347       //silenced
        REQUEST_MODEL 335       //FACA
        REQUEST_MODEL 14200
        REQUEST_MODEL 14201
        REQUEST_MODEL 14202
    LOAD_ALL_MODELS_NOW
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_PARACHUTE 1   //paraquedas
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_PISTOL_SILENCED 100
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_SNIPERRIFLE 40
    GIVE_WEAPON_TO_CHAR scplayer WEAPONTYPE_KNIFE 1
    SET_CURRENT_CHAR_WEAPON scplayer 0
    SET_PLAYER_CONTROL 0 TRUE
    RESTORE_CAMERA_JUMPCUT
    DISPLAY_HUD 1
    DISPLAY_RADAR 1
DO_FADE 1000 1

waiting_jump:
WAIT 0
GET_CHAR_COORDINATES scplayer x y z

IF z < 882.0000
    SET_AREA_VISIBLE 0
    SET_CHAR_AREA_VISIBLE scplayer 0
    ADD_SPRITE_BLIP_FOR_COORD -6374.8394, -2686.3398, 6.1074 13 tblip[0]
    GOTO jumped
ENDIF

GOTO waiting_jump

jumped:
WAIT 0
IF LOCATE_CHAR_ANY_MEANS_3D scplayer -6364.7793, -2706.0269, 1.6384 (15.0 15.0 10.0) TRUE
    GOTO pousou
    REMOVE_BLIP tblip[0]
    WHILE LOCATE_CHAR_ANY_MEANS_3D scplayer -6374.8394, -2686.3398, 6.1074 (5.0 5.0 3.0) TRUE
        WAIT 0
    ENDWHILE
ENDIF
GOTO jumped     //14200 14201 14202 YAKUZAS

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
GENERATE_RANDOM_FLOAT_IN_RANGE -6339.2778 -6209.9048 x
GENERATE_RANDOM_FLOAT_IN_RANGE -2762.0740 -2635.0081 y
TASK_LOOK_AT_COORD enem[i] x y -100.0 10
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