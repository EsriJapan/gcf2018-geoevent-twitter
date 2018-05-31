package com.esrij.geoevent.solutions.processor.textanalysis;

public class CharacterUtil {
	private static final String KANSUJI =
			"一壱弌壹二弐弍貳貮三四五伍六七八九〇零十什拾百佰千仟万萬億";

		/**
	     * 全角アルファベットと半角アルファベットとの文字コードの差
	     */
	    private static final int DIFFERENCE = 'Ａ' - 'A';

	    /**
	     * 変換対象半角記号配列
	     */
	    private static char[] SIGNS1 =
	            {
	                    '!',
	                    '#',
	                    '$',
	                    '%',
	                    '&',
	                    '(',
	                    ')',
	                    '*',
	                    '+',
	                    ',',
	                    '-',
	                    '.',
	                    '/',
	                    ':',
	                    ';',
	                    '<',
	                    '=',
	                    '>',
	                    '?',
	                    '@',
	                    '[',
	                    ']',
	                    '^',
	                    '_',
	                    '{',
	                    '|',
	                    '}'
	                    };

	    private static final char[] HANKAKU_KATAKANA = { '｡', '｢', '｣', '､', '･',
	    	      'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ',
	    	      'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ',
	    	      'ﾀ', 'ﾁ', 'ﾂ', 'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ',
	    	      'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ', 'ﾘ', 'ﾙ',
	    	      'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ' };

	    private static final char[] ZENKAKU_KATAKANA = { '。', '「', '」', '、', '・',
	    	      'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー', 'ア', 'イ',
	    	      'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'サ', 'シ', 'ス', 'セ', 'ソ',
	    	      'タ', 'チ', 'ツ', 'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ',
	    	      'ヘ', 'ホ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ラ', 'リ', 'ル',
	    	      'レ', 'ロ', 'ワ', 'ン', '゛', '゜' };

	    private static final char HANKAKU_KATAKANA_FIRST_CHAR = HANKAKU_KATAKANA[0];

	    private static final char HANKAKU_KATAKANA_LAST_CHAR = HANKAKU_KATAKANA[HANKAKU_KATAKANA.length - 1];

		private CharacterUtil() {
		}

		/**
		 * @param word 文字列
		 * @return 文字列が漢字を含む場合true
		 */
		public static boolean containsKanji( String word ) {
			char[] chars = word.toCharArray();
			for ( int i = 0; i < chars.length; i++ ) {
				if ( isKanji( chars[i] ) ) return true;
			}
			return false;
		}

		/**
		 * @param c 文字
		 * @return 文字が漢字の場合true
		 */
		public static boolean isKanji( char c ) {
			if ( c >= '一' && c <= '龠' ) return true;
			if ( c >= '朗' && c <= '鶴' ) return true;
			return false;
		}

		/**
		 * @param c 文字
		 * @return 文字がかなの場合true
		 */
		public static boolean isKana( char c ) {
			if ( isHiragana( c ) ) return true;
			if ( isKatakana( c ) ) return true;
			return false;
		}

		/**
		 * @param c 文字
		 * @return 文字がカタカナの場合true
		 */
		public static boolean isKatakana( char c ) {
			return Character.UnicodeBlock.of( c ) ==
				Character.UnicodeBlock.HIRAGANA;
	    }

		/**
		 * @param c 文字
		 * @return 文字がひらがなの場合true
		 */
		public static boolean isHiragana( char c ) {
			return Character.UnicodeBlock.of( c ) ==
				Character.UnicodeBlock.KATAKANA;
	    }

		/**
		 * @param c 文字
		 * @return 文字列が漢数字である場合true
		 * <br />注) 小数点やカンマの場合はfalseとなります
		 */
//		public static boolean isKanSuji( char c ) {
//		    return StringUtils.contains( KANSUJI, c );
//	    }

		/**
		 * @param str 文字列
		 * @return 文字列が全角・半角を問わずアラビア数字である場合true
		 * <br />注) 小数点やカンマを含む場合はfalseとなります
		 */
		public static boolean isNumeric( String str ) {
			char[] chars = str.toCharArray();
			for ( char c : chars ) {
				if ( isNotNumeric( c ) ) return false;
			}
			return true;
		}

		/**
		 * @param c 文字
		 * @return 文字が全角・半角を問わずアラビア数字である場合true
		 * <br />注) 小数点やカンマの場合はfalseとなります
		 */
		public static boolean isNumeric( char c ) {
			return Character.isDigit( c );
		}

		/**
		 * @param c 文字
		 * @return 文字が全角・半角を問わずアラビア数字で<b>ない</b>場合true
		 * <br />注) 小数点やカンマの場合はtrueとなります
		 */
		public static boolean isNotNumeric( char c ) {
			return ! isNumeric( c );
		}

		/**
		 * @param str 文字列
		 * @return 文字列が全角・半角、大文字・小文字を問わずアルファベットの場合true
		 */
		public static boolean isAlpha( String str ) {
			char[] chars = str.toCharArray();
			for ( char c : chars ) {
				if ( isNotAlpha( c ) ) return false;
			}
			return true;
		}

		/**
		 * @param c 文字
		 * @return 文字が全角・半角、大文字・小文字を問わずアルファベットで<b>ない</b>
		 * 場合true
		 */
		public static boolean isNotAlpha( char c ) {
			return ! isAlpha( c );
		}

		/**
		 * @param c 文字
		 * @return 文字が全角・半角、大文字・小文字を問わずアルファベットの場合true
		 */
		public static boolean isAlpha( char c ) {
			if ( c >= 'a' && c <= 'z' ) return true;
			if ( c >= 'A' && c <= 'Z' ) return true;
			if ( c >= 'ａ' && c <= 'ｚ' ) return true;
			if ( c >= 'Ａ' && c <= 'Ｚ' ) return true;
			return false;
		}

		/**
	     * 変換対象半角記号かを判定する。
	     * @param pc
	     * @return
	     */
	    private static boolean is1Sign(char pc) {
	        for (char c : SIGNS1) {
	            if (c == pc) {
	                return true;
	            }
	        }
	        return false;
	    }

	    /**
	     * 文字列のアルファベット・数値を全角文字に変換する。
	     * @param str
	     * @return 変換された２バイト文字列
	     */
	    public static String convertZen(String str) {
	        char[] cc = str.toCharArray();
	        StringBuilder sb = new StringBuilder();
	        for (char c : cc) {
	            char newChar = c;
	            if ((('A' <= c) && (c <= 'Z')) || (('a' <= c) && (c <= 'z'))
	                    || (('0' <= c) && (c <= '9')) || is1Sign(c)) {
	                // 変換対象のcharだった場合に全角文字と半角文字の差分を足す
	                newChar = (char) (c + DIFFERENCE);
	            }

	            sb.append(newChar);
	        }
	        return sb.toString();
	    }

	    /**
	     * 半角カタカナから全角カタカナへ変換します。
	     * @param c 変換前の文字
	     * @return 変換後の文字
	     */
	    public static char hankakuKatakanaToZenkakuKatakana(char c) {
	      if (c >= HANKAKU_KATAKANA_FIRST_CHAR && c <= HANKAKU_KATAKANA_LAST_CHAR) {
	        return ZENKAKU_KATAKANA[c - HANKAKU_KATAKANA_FIRST_CHAR];
	      } else {
	        return c;
	      }
	    }
	    /**
	     * 2文字目が濁点・半濁点で、1文字目に加えることができる場合は、合成した文字を返します。
	     * 合成ができないときは、c1を返します。
	     * @param c1 変換前の1文字目
	     * @param c2 変換前の2文字目
	     * @return 変換後の文字
	     */
	    public static char mergeChar(char c1, char c2) {
	      if (c2 == 'ﾞ') {
	        if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
	          switch (c1) {
	          case 'ｶ': return 'ガ';
	          case 'ｷ': return 'ギ';
	          case 'ｸ': return 'グ';
	          case 'ｹ': return 'ゲ';
	          case 'ｺ': return 'ゴ';
	          case 'ｻ': return 'ザ';
	          case 'ｼ': return 'ジ';
	          case 'ｽ': return 'ズ';
	          case 'ｾ': return 'ゼ';
	          case 'ｿ': return 'ゾ';
	          case 'ﾀ': return 'ダ';
	          case 'ﾁ': return 'ヂ';
	          case 'ﾂ': return 'ヅ';
	          case 'ﾃ': return 'デ';
	          case 'ﾄ': return 'ド';
	          case 'ﾊ': return 'バ';
	          case 'ﾋ': return 'ビ';
	          case 'ﾌ': return 'ブ';
	          case 'ﾍ': return 'ベ';
	          case 'ﾎ': return 'ボ';
	          }
	        }
	      } else if (c2 == 'ﾟ') {
	        if ("ﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
	          switch (c1) {
	          case 'ﾊ': return 'パ';
	          case 'ﾋ': return 'ピ';
	          case 'ﾌ': return 'プ';
	          case 'ﾍ': return 'ペ';
	          case 'ﾎ': return 'ポ';
	          }
	        }
	      }
	      return c1;
	    }

	    /**
	     * 文字列中の半角カタカナを全角カタカナに変換します。
	     * @param s 変換前文字列
	     * @return 変換後文字列
	     */
	    public static String hankakuKatakanaToZenkakuKatakana(String s) {
	      if (s.length() == 0) {
	        return s;
	      } else if (s.length() == 1) {
	        return hankakuKatakanaToZenkakuKatakana(s.charAt(0)) + "";
	      } else {
	        StringBuffer sb = new StringBuffer(s);
	        int i = 0;
	        for (i = 0; i < sb.length() - 1; i++) {
	          char originalChar1 = sb.charAt(i);
	          char originalChar2 = sb.charAt(i + 1);
	          char margedChar = mergeChar(originalChar1, originalChar2);
	          if (margedChar != originalChar1) {
	            sb.setCharAt(i, margedChar);
	            sb.deleteCharAt(i + 1);
	          } else {
	            char convertedChar = hankakuKatakanaToZenkakuKatakana(originalChar1);
	            if (convertedChar != originalChar1) {
	              sb.setCharAt(i, convertedChar);
	            }
	          }
	        }
	        if (i < sb.length()) {
	          char originalChar1 = sb.charAt(i);
	          char convertedChar = hankakuKatakanaToZenkakuKatakana(originalChar1);
	          if (convertedChar != originalChar1) {
	            sb.setCharAt(i, convertedChar);
	          }
	        }
	        return sb.toString();
	         }

	    }

}
