package com.home.allegro;

public enum HTMLClassName {
    CROSSED_OUT_STRING("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv"),
    PRODUCT_NAME("mgn2_14 m9qz_yp mqu1_16 mp4t_0 m3h2_0 mryx_0 munh_0"),
    RIGHT_PRICE("_1svub _lf05o"),
    FULL_PRICE_WITH_DELIVERY("mqu1_g3"),
    NUMBER_CUSTOMERS("mpof_ki m389_6m munh_56_l"),
    ADDITIONAL_INFO("mpof_uk mgmw_ag mp4t_0 m3h2_0 mryx_0 munh_0 mg9e_0 mvrt_0 mj7a_0 mh36_0 _9c44d_3hPFO"),
    REFERENCE_TO_PRODUCT_PAGE("_w7z6o _uj8z7 meqh_en mpof_z0 mqu1_16 _9c44d_2vTdY  ");

    private final String className;

    HTMLClassName(String className){
        this.className = className;
    }

    public String getClassName(){
        return className;
    }

}
