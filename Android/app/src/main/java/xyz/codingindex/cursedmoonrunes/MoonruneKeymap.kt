package xyz.codingindex.cursedmoonrunes

/*
 * Moonrunes acquired from this site: https://lingojam.com/JapaneseText
 */

object MoonruneKeymap {
    private val keymap = mapOf<Int, CharSequence>(
        Pair(113, "Ɋ"),
        Pair(119, "山"),
        Pair(101, "乇"),
        Pair(114, "尺"),
        Pair(116, "ㄒ"),
        Pair(121, "ㄚ"),
        Pair(117, "ㄩ"),
        Pair(105, "丨"),
        Pair(111, "ㄖ"),
        Pair(112, "卩"),
        Pair(97, "卂"),
        Pair(115, "丂"),
        Pair(100, "ᗪ"),
        Pair(102, "千"),
        Pair(103, "Ꮆ"),
        Pair(104, "卄"),
        Pair(106, "ﾌ"),
        Pair(107, "Ҝ"),
        Pair(108, "ㄥ"),
        Pair(122, "乙"),
        Pair(120, "乂"),
        Pair(99, "匚"),
        Pair(118, "ᐯ"),
        Pair(98, "乃"),
        Pair(110, "几"),
        Pair(109, "爪"),
        Pair(44, "，"),
        Pair(47, "／"),
        Pair(46, "．"),
        Pair(32, "  ")
    ).withDefault { key -> key.toChar().toString() }

    fun getSymbol(code: Int): CharSequence {
        return keymap.getValue(code);
    }
}