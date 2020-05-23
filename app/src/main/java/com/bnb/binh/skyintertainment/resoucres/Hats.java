package com.bnb.binh.skyintertainment.resoucres;

import com.bnb.binh.skyintertainment.R;
import com.bnb.binh.skyintertainment.models.Hat;

import java.math.BigDecimal;

public class Hats {
    public static Hat[] getHats(){
        return SNAPBACKS;
    }

    public static final Hat SNAPBACK_BLACK = new Hat("Black Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377376);
    public static final Hat SNAPBACK_CAMO = new Hat("Camo Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377377);
    public static final Hat SNAPBACK_GREY = new Hat("Grey Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377378);
    public static final Hat SNAPBACK_NAVY = new Hat("Navy Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377379);
    public static final Hat SNAPBACK_RED = new Hat("Red Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377380);
    public static final Hat SNAPBACK_TEAL = new Hat("Teal Snapback", R.drawable.ic_snapback_black,
            new BigDecimal(20.99), 9377381);

    public static final Hat[] SNAPBACKS = {SNAPBACK_NAVY, SNAPBACK_BLACK, SNAPBACK_CAMO, SNAPBACK_GREY, SNAPBACK_RED, SNAPBACK_TEAL};
}
