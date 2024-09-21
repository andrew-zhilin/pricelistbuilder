package com.gmail.dev.zhilin.pricelistbuilder.enums;

public enum Truck {

    FUSO_CANTER("Fuso Canter"),
    HINO_300("Hino 300 (Dutro)"),
    HINO_500("Hino 500 (Ranger)"),
    HINO_700("Hino 700 (Profia)"),
    HYUNDAI_HD35_HD78_MIGHTY("Hyundai HD35-HD78 / Mighty"),
    HYUNDAI_HD120_HD210_GOLD("Hyundai HD120-HD210 / Gold"),
    HYUNDAI_HD250_HD270("Hyundai HD250-HD270"),
    HYUNDAI_COUNTY("Hyundai County"),
    HYUNDAY_PORTER_H100("Hyundai Porter / H100"),
    ISUZU_C_E_SERIES("Isuzu C&E-series"),
    ISUZU_F_G_SERIES("Isuzu F&G-series"),
    ISUZU_N_SERIES("Isuzu N-series"),
    ISUZU_Q_SERIES("Isuzu Q-series"),
    JAC_N25("Jac N25"),
    JAC_N35("Jac N35"),
    JAC_N56("Jac N56"),
    JAC_N75("Jac N75"),
    JAC_N80("Jac N80"),
    JAC_N90("Jac N90"),
    JAC_N120("Jac N120"),
    JAC_N200("Jac N200"),
    JAC_N350("Jac N350"),
    KIA_BONGO("Kia Bongo"),
    MAZDA_TITAN("Mazda Titan"),
    NISSAN_ATLAS_CABSTAR_CONDOR("Nissan Atlas / Cabstar / Condor"),
    TOYOTA_DYNA_TOYOACE("Toyota Dyna / ToyoAce"),
    FAW_TRUCKS("Грузовики Faw"),
    HOWO_TRUCKS("Грузовики Howo"),
    IVECO_TRUCKS("Грузовики Iveco"),
    MAN_TRUCKS("Грузовики Man"),
    MERCEDES_TRUCKS("Грузовики Mercedes"),
    SCANIA_TRUCKS("Грузовики Scania"),
    SHACMAN_SHAANXI_TRUCKS("Грузовики Shacman (Shaanxi)"),
    SITRAK_TRUCKS("Грузовики Sitrak"),
    VOLVO_TRUCKS("Грузовики Volvo"),
    KAMAZ_COMPASS_5("Камаз Компас-5"),
    KAMAZ_COMPASS_9("Камаз Компас-9"),
    KAMAZ_COMPASS_12("Камаз Компас-12");

    private String name;

    Truck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
