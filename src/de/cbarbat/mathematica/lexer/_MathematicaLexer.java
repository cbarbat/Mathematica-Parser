/* The following code was generated by JFlex 1.4.3 on 21.07.15 13:48 */

package de.cbarbat.mathematica.lexer;

import java.util.LinkedList;
import de.cbarbat.mathematica.parser.MathematicaElementType;
import de.cbarbat.mathematica.parser.MathematicaElementTypes;

@SuppressWarnings("ALL")

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 21.07.15 13:48 from the specification file
 * <tt>/home/calin/IdeaProjects/Mathematica-Parser/src/de/cbarbat/mathematica/lexer/Mathematica.flex</tt>
 */
class _MathematicaLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int PUT_START = 6;
  public static final int GET_START = 10;
  public static final int IN_STRING = 4;
  public static final int YYINITIAL = 0;
  public static final int PUT_RHS = 8;
  public static final int GET_RHS = 12;
  public static final int IN_COMMENT = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  4, 4
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\1\1\0\1\3\1\2\22\0\1\56\1\45\1\32"+
    "\1\27\1\30\1\31\1\53\1\54\1\4\1\6\1\5\1\46\1\50"+
    "\1\26\1\24\1\41\1\15\1\23\1\17\1\20\1\21\1\21\1\21"+
    "\1\22\1\22\1\22\1\42\1\44\1\35\1\43\1\37\1\52\1\40"+
    "\32\55\1\12\1\11\1\13\1\25\1\51\1\14\32\16\1\33\1\36"+
    "\1\34\1\47\53\0\1\7\12\0\1\7\4\0\1\7\5\0\27\7"+
    "\1\0\37\7\1\0\u01ca\7\4\0\14\7\16\0\5\7\7\0\1\7"+
    "\1\0\1\7\201\0\5\7\1\0\2\7\2\0\4\7\10\0\1\7"+
    "\1\0\3\7\1\0\1\7\1\0\24\7\1\0\123\7\1\0\213\7"+
    "\10\0\236\7\11\0\46\7\2\0\1\7\7\0\47\7\110\0\33\7"+
    "\5\0\3\7\55\0\53\7\25\0\12\10\4\0\2\7\1\0\143\7"+
    "\1\0\1\7\17\0\2\7\7\0\2\7\12\10\3\7\2\0\1\7"+
    "\20\0\1\7\1\0\36\7\35\0\131\7\13\0\1\7\16\0\12\10"+
    "\41\7\11\0\2\7\4\0\1\7\5\0\26\7\4\0\1\7\11\0"+
    "\1\7\3\0\1\7\27\0\31\7\107\0\1\7\1\0\13\7\127\0"+
    "\66\7\3\0\1\7\22\0\1\7\7\0\12\7\4\0\12\10\1\0"+
    "\7\7\1\0\7\7\5\0\10\7\2\0\2\7\2\0\26\7\1\0"+
    "\7\7\1\0\1\7\3\0\4\7\3\0\1\7\20\0\1\7\15\0"+
    "\2\7\1\0\3\7\4\0\12\10\2\7\23\0\6\7\4\0\2\7"+
    "\2\0\26\7\1\0\7\7\1\0\2\7\1\0\2\7\1\0\2\7"+
    "\37\0\4\7\1\0\1\7\7\0\12\10\2\0\3\7\20\0\11\7"+
    "\1\0\3\7\1\0\26\7\1\0\7\7\1\0\2\7\1\0\5\7"+
    "\3\0\1\7\22\0\1\7\17\0\2\7\4\0\12\10\25\0\10\7"+
    "\2\0\2\7\2\0\26\7\1\0\7\7\1\0\2\7\1\0\5\7"+
    "\3\0\1\7\36\0\2\7\1\0\3\7\4\0\12\10\1\0\1\7"+
    "\21\0\1\7\1\0\6\7\3\0\3\7\1\0\4\7\3\0\2\7"+
    "\1\0\1\7\1\0\2\7\3\0\2\7\3\0\3\7\3\0\14\7"+
    "\26\0\1\7\25\0\12\10\25\0\10\7\1\0\3\7\1\0\27\7"+
    "\1\0\12\7\1\0\5\7\3\0\1\7\32\0\2\7\6\0\2\7"+
    "\4\0\12\10\25\0\10\7\1\0\3\7\1\0\27\7\1\0\12\7"+
    "\1\0\5\7\3\0\1\7\40\0\1\7\1\0\2\7\4\0\12\10"+
    "\1\0\2\7\22\0\10\7\1\0\3\7\1\0\51\7\2\0\1\7"+
    "\20\0\1\7\21\0\2\7\4\0\12\10\12\0\6\7\5\0\22\7"+
    "\3\0\30\7\1\0\11\7\1\0\1\7\2\0\7\7\72\0\60\7"+
    "\1\0\2\7\14\0\7\7\11\0\12\10\47\0\2\7\1\0\1\7"+
    "\2\0\2\7\1\0\1\7\2\0\1\7\6\0\4\7\1\0\7\7"+
    "\1\0\3\7\1\0\1\7\1\0\1\7\2\0\2\7\1\0\4\7"+
    "\1\0\2\7\11\0\1\7\2\0\5\7\1\0\1\7\11\0\12\10"+
    "\2\0\4\7\40\0\1\7\37\0\12\10\26\0\10\7\1\0\44\7"+
    "\33\0\5\7\163\0\53\7\24\0\1\7\12\10\6\0\6\7\4\0"+
    "\4\7\3\0\1\7\3\0\2\7\7\0\3\7\4\0\15\7\14\0"+
    "\1\7\1\0\12\10\6\0\46\7\1\0\1\7\5\0\1\7\2\0"+
    "\53\7\1\0\u014d\7\1\0\4\7\2\0\7\7\1\0\1\7\1\0"+
    "\4\7\2\0\51\7\1\0\4\7\2\0\41\7\1\0\4\7\2\0"+
    "\7\7\1\0\1\7\1\0\4\7\2\0\17\7\1\0\71\7\1\0"+
    "\4\7\2\0\103\7\45\0\20\7\20\0\125\7\14\0\u026c\7\2\0"+
    "\21\7\1\0\32\7\5\0\113\7\25\0\15\7\1\0\4\7\16\0"+
    "\22\7\16\0\22\7\16\0\15\7\1\0\3\7\17\0\64\7\43\0"+
    "\1\7\4\0\1\7\3\0\12\10\46\0\12\10\6\0\130\7\10\0"+
    "\51\7\1\0\1\7\5\0\106\7\12\0\35\7\51\0\12\10\36\7"+
    "\2\0\5\7\13\0\54\7\25\0\7\7\10\0\12\10\46\0\27\7"+
    "\11\0\65\7\53\0\12\10\6\0\12\10\15\0\1\7\135\0\57\7"+
    "\21\0\7\7\4\0\12\10\51\0\36\7\15\0\2\7\12\10\54\7"+
    "\32\0\44\7\34\0\12\10\3\0\3\7\12\10\44\7\153\0\4\7"+
    "\1\0\4\7\3\0\2\7\11\0\300\7\100\0\u0116\7\2\0\6\7"+
    "\2\0\46\7\2\0\6\7\2\0\10\7\1\0\1\7\1\0\1\7"+
    "\1\0\1\7\1\0\37\7\2\0\65\7\1\0\7\7\1\0\1\7"+
    "\3\0\3\7\1\0\7\7\3\0\4\7\2\0\6\7\4\0\15\7"+
    "\5\0\3\7\1\0\7\7\164\0\1\7\15\0\1\7\20\0\15\7"+
    "\145\0\1\7\4\0\1\7\2\0\12\7\1\0\1\7\3\0\5\7"+
    "\6\0\1\7\1\0\1\7\1\0\1\7\1\0\4\7\1\0\13\7"+
    "\2\0\4\7\5\0\5\7\4\0\1\7\64\0\2\7\u0a7b\0\57\7"+
    "\1\0\57\7\1\0\205\7\6\0\4\7\3\0\2\7\14\0\46\7"+
    "\1\0\1\7\5\0\1\7\2\0\70\7\7\0\1\7\20\0\27\7"+
    "\11\0\7\7\1\0\7\7\1\0\7\7\1\0\7\7\1\0\7\7"+
    "\1\0\7\7\1\0\7\7\1\0\7\7\120\0\1\7\u01d5\0\2\7"+
    "\52\0\5\7\5\0\2\7\4\0\126\7\6\0\3\7\1\0\132\7"+
    "\1\0\4\7\5\0\51\7\3\0\136\7\21\0\33\7\65\0\20\7"+
    "\u0200\0\u19b6\7\112\0\u51cd\7\63\0\u048d\7\103\0\56\7\2\0\u010d\7"+
    "\3\0\20\7\12\10\2\7\24\0\57\7\20\0\31\7\10\0\106\7"+
    "\61\0\11\7\2\0\147\7\2\0\4\7\1\0\4\7\14\0\13\7"+
    "\115\0\12\7\1\0\3\7\1\0\4\7\1\0\27\7\35\0\64\7"+
    "\16\0\62\7\34\0\12\10\30\0\6\7\3\0\1\7\4\0\12\10"+
    "\34\7\12\0\27\7\31\0\35\7\7\0\57\7\34\0\1\7\12\10"+
    "\46\0\51\7\27\0\3\7\1\0\10\7\4\0\12\10\6\0\27\7"+
    "\3\0\1\7\5\0\60\7\1\0\1\7\3\0\2\7\2\0\5\7"+
    "\2\0\1\7\1\0\1\7\30\0\3\7\2\0\13\7\7\0\3\7"+
    "\14\0\6\7\2\0\6\7\2\0\6\7\11\0\7\7\1\0\7\7"+
    "\221\0\43\7\15\0\12\10\6\0\u2ba4\7\14\0\27\7\4\0\61\7"+
    "\u2104\0\u016e\7\2\0\152\7\46\0\7\7\14\0\5\7\5\0\1\7"+
    "\1\0\12\7\1\0\15\7\1\0\5\7\1\0\1\7\1\0\2\7"+
    "\1\0\2\7\1\0\154\7\41\0\u016b\7\22\0\100\7\2\0\66\7"+
    "\50\0\14\7\164\0\5\7\1\0\207\7\23\0\12\10\7\0\32\7"+
    "\6\0\32\7\13\0\131\7\3\0\6\7\2\0\6\7\2\0\6\7"+
    "\2\0\3\7\43\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\4\0\1\2\1\3\1\4\1\5\1\6"+
    "\1\7\1\10\1\2\1\11\1\12\1\13\5\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\42\1\43\1\44\5\1"+
    "\2\45\1\46\1\47\1\2\1\47\1\50\1\51\1\52"+
    "\2\53\1\54\1\55\1\56\1\57\2\0\2\4\1\0"+
    "\1\60\1\0\2\14\1\0\1\61\1\0\1\62\1\63"+
    "\1\64\1\65\1\20\1\66\1\67\1\70\1\71\1\72"+
    "\1\73\1\74\1\75\1\76\1\77\1\100\1\101\1\102"+
    "\1\103\1\104\1\105\1\106\1\107\1\110\1\111\1\112"+
    "\1\113\1\114\1\115\1\0\1\116\1\117\1\120\1\121"+
    "\1\122\1\123\1\124\1\125\1\126\2\0\2\45\3\0"+
    "\1\14\2\0\1\127\1\130\1\131\1\132\1\133\1\134"+
    "\1\135\1\136\1\137\1\140\1\0\1\141\1\10\1\14"+
    "\1\0\2\14\1\0\1\14\1\142";

  private static int [] zzUnpackAction() {
    int [] result = new int[147];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\136\0\215\0\274\0\353\0\u011a\0\u0149"+
    "\0\u0178\0\u01a7\0\u01d6\0\u011a\0\u0205\0\u0234\0\u011a\0\u011a"+
    "\0\u0263\0\u0292\0\u02c1\0\u02f0\0\u031f\0\u034e\0\u037d\0\u03ac"+
    "\0\u03db\0\u040a\0\u0439\0\u011a\0\u011a\0\u011a\0\u0468\0\u0497"+
    "\0\u04c6\0\u04f5\0\u0524\0\u0553\0\u0582\0\u05b1\0\u05e0\0\u060f"+
    "\0\u063e\0\u011a\0\u066d\0\u011a\0\u069c\0\u011a\0\u06cb\0\u01a7"+
    "\0\u06fa\0\u011a\0\u0729\0\u0758\0\u0787\0\u011a\0\u011a\0\u07b6"+
    "\0\u07b6\0\u07e5\0\u0814\0\u011a\0\u011a\0\u07b6\0\u0843\0\u011a"+
    "\0\u011a\0\u011a\0\u0872\0\u08a1\0\u011a\0\u08d0\0\u08ff\0\u011a"+
    "\0\u092e\0\u095d\0\u098c\0\u09bb\0\u09ea\0\u0a19\0\u011a\0\u011a"+
    "\0\u011a\0\u011a\0\u0a48\0\u0a77\0\u0aa6\0\u011a\0\u011a\0\u011a"+
    "\0\u011a\0\u011a\0\u011a\0\u0ad5\0\u011a\0\u011a\0\u0b04\0\u011a"+
    "\0\u011a\0\u011a\0\u0b33\0\u011a\0\u011a\0\u011a\0\u011a\0\u011a"+
    "\0\u011a\0\u0b62\0\u0b91\0\u0bc0\0\u011a\0\u011a\0\u011a\0\u011a"+
    "\0\u011a\0\u011a\0\u0bef\0\u011a\0\u011a\0\u0c1e\0\u0c4d\0\u011a"+
    "\0\u0c7c\0\u0cab\0\u0cda\0\u0d09\0\u0d38\0\u0d67\0\u0d96\0\u011a"+
    "\0\u011a\0\u011a\0\u011a\0\u011a\0\u011a\0\u011a\0\u011a\0\u011a"+
    "\0\u011a\0\u0dc5\0\u011a\0\u0df4\0\u0e23\0\u0e23\0\u0e52\0\u0e81"+
    "\0\u0eb0\0\u0edf\0\u011a";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[147];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\7\2\10\1\11\1\12\1\13\1\14\1\15\1\7"+
    "\1\16\1\17\1\20\1\21\1\22\1\15\1\23\1\24"+
    "\2\25\1\26\1\27\1\30\1\31\1\32\1\15\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53"+
    "\1\54\1\55\1\56\1\15\1\11\4\57\1\60\1\61"+
    "\1\62\33\57\1\63\14\57\11\64\1\65\20\64\1\66"+
    "\24\64\1\67\1\70\1\71\1\72\52\67\1\72\1\73"+
    "\2\70\2\7\1\73\1\7\3\73\2\7\11\73\1\7"+
    "\1\73\1\7\1\73\1\7\1\74\6\7\2\73\2\7"+
    "\1\73\1\7\1\73\1\7\3\73\1\7\1\73\1\7"+
    "\1\75\1\70\1\76\1\77\52\75\1\77\60\0\2\10"+
    "\57\0\1\11\52\0\1\11\5\0\1\100\56\0\1\101"+
    "\35\0\1\102\22\0\2\15\1\103\2\0\1\104\7\15"+
    "\4\0\1\15\24\0\1\15\2\0\1\105\1\106\7\0"+
    "\1\107\53\0\1\15\1\0\1\103\2\0\1\110\1\0"+
    "\1\15\11\0\1\15\24\0\1\15\6\0\1\111\6\0"+
    "\1\112\1\22\1\0\5\22\1\113\37\0\1\111\6\0"+
    "\1\112\1\25\1\0\5\25\1\113\1\114\36\0\1\111"+
    "\6\0\1\112\1\25\1\0\3\25\1\22\1\25\1\113"+
    "\1\114\36\0\1\111\6\0\1\112\1\22\1\0\5\22"+
    "\1\113\1\114\36\0\1\111\6\0\1\112\1\25\1\0"+
    "\5\25\1\113\47\0\1\113\1\0\5\113\1\115\74\0"+
    "\1\116\1\117\41\0\1\120\10\0\1\121\3\0\1\122"+
    "\30\0\1\123\1\124\5\123\3\0\1\125\1\124\24\0"+
    "\1\124\32\0\1\33\62\0\1\126\1\127\1\130\3\0"+
    "\1\131\51\0\1\132\1\133\56\0\1\134\3\0\1\135"+
    "\20\0\1\136\32\0\1\137\23\0\1\140\16\0\1\141"+
    "\13\0\1\142\1\143\1\144\1\145\1\146\51\0\1\147"+
    "\2\0\1\150\1\151\37\0\1\152\16\0\1\153\1\0"+
    "\1\154\55\0\1\155\55\0\1\156\56\0\1\157\2\0"+
    "\1\160\57\0\1\161\33\0\1\162\24\0\1\163\60\0"+
    "\1\164\3\0\4\57\3\0\33\57\1\0\14\57\6\0"+
    "\1\165\112\0\1\166\12\0\1\167\1\0\11\64\1\0"+
    "\20\64\1\0\24\64\1\0\1\170\1\171\6\0\1\170"+
    "\20\0\1\170\25\0\2\70\57\0\1\72\52\0\1\72"+
    "\1\73\4\0\1\73\1\0\3\73\2\0\11\73\1\0"+
    "\1\73\1\0\1\73\10\0\2\73\2\0\1\73\1\0"+
    "\1\73\1\0\3\73\1\0\1\73\4\0\1\77\52\0"+
    "\1\77\12\0\1\107\53\0\1\15\1\0\1\103\4\0"+
    "\1\15\11\0\1\15\24\0\1\15\2\0\1\105\64\0"+
    "\1\172\6\0\1\172\11\0\1\172\24\0\1\172\26\0"+
    "\1\173\36\0\1\111\6\0\1\174\1\175\1\0\5\175"+
    "\1\176\37\0\1\111\6\0\1\112\1\113\1\0\5\113"+
    "\60\0\1\177\55\0\1\200\75\0\1\201\30\0\1\123"+
    "\1\0\5\123\50\0\7\124\4\0\1\124\24\0\1\124"+
    "\16\0\1\125\1\0\5\125\72\0\1\202\57\0\1\203"+
    "\42\0\1\204\13\0\1\205\33\0\1\206\1\0\5\206"+
    "\76\0\1\207\56\0\1\210\64\0\1\211\62\0\1\212"+
    "\17\0\1\167\23\0\1\213\12\0\2\167\1\0\1\170"+
    "\64\0\2\172\2\0\1\214\1\0\7\172\4\0\1\172"+
    "\24\0\1\172\16\0\1\215\1\0\5\215\2\0\1\216"+
    "\45\0\1\175\1\0\5\175\1\176\37\0\1\111\7\0"+
    "\1\175\1\0\5\175\1\217\47\0\1\217\1\0\5\217"+
    "\50\0\7\220\31\0\1\220\17\0\1\212\23\0\1\221"+
    "\12\0\1\212\10\0\1\15\1\0\1\103\2\0\1\104"+
    "\1\0\1\15\11\0\1\15\24\0\1\15\16\0\1\215"+
    "\1\0\5\215\40\0\1\111\7\0\1\217\1\0\5\217"+
    "\47\0\1\112\7\220\1\222\30\0\1\220\43\0\1\223"+
    "\30\0\1\112\7\222\31\0\1\222\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3854];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\1\4\0\1\11\4\1\1\11\2\1\2\11"+
    "\13\1\3\11\13\1\1\11\1\1\1\11\1\1\1\11"+
    "\3\1\1\11\3\1\2\11\4\1\2\11\2\1\3\11"+
    "\2\0\1\11\1\1\1\0\1\11\1\0\2\1\1\0"+
    "\1\1\1\0\4\11\3\1\6\11\1\1\2\11\1\1"+
    "\3\11\1\1\6\11\2\1\1\0\6\11\1\1\2\11"+
    "\2\0\1\11\1\1\3\0\1\1\2\0\12\11\1\0"+
    "\1\11\2\1\1\0\2\1\1\0\1\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[147];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */
  // This adds support for nested states. I'm no JFlex pro, so maybe this is overkill, but it works quite well.
  private final LinkedList<Integer> states = new LinkedList<Integer>();

  private void yypushstate(int state) {
  	states.addFirst(yystate());
  	yybegin(state);
  }

  private void yypopstate() {
  	final int state = states.removeFirst();
    yybegin(state);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  _MathematicaLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1686) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public MathematicaElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 6: 
          { return MathematicaElementTypes.TIMES;
          }
        case 99: break;
        case 4: 
          { return MathematicaElementTypes.WHITE_SPACE;
          }
        case 100: break;
        case 44: 
          { yybegin(GET_RHS); return MathematicaElementTypes.WHITE_SPACE;
          }
        case 101: break;
        case 39: 
          { yypushback(1); yybegin(PUT_RHS);
          }
        case 102: break;
        case 25: 
          { return MathematicaElementTypes.DIVIDE;
          }
        case 103: break;
        case 9: 
          { return MathematicaElementTypes.LEFT_BRACKET;
          }
        case 104: break;
        case 64: 
          { return MathematicaElementTypes.COMPOSITION;
          }
        case 105: break;
        case 79: 
          { return MathematicaElementTypes.UNEQUAL;
          }
        case 106: break;
        case 40: 
          { yybegin(PUT_RHS); return MathematicaElementTypes.WHITE_SPACE;
          }
        case 107: break;
        case 84: 
          { return MathematicaElementTypes.BLANK_SEQUENCE;
          }
        case 108: break;
        case 97: 
          { return MathematicaElementTypes.COMMENT_ANNOTATION;
          }
        case 109: break;
        case 70: 
          { return MathematicaElementTypes.TAG_SET;
          }
        case 110: break;
        case 60: 
          { return MathematicaElementTypes.OR;
          }
        case 111: break;
        case 31: 
          { return MathematicaElementTypes.INFIX_CALL;
          }
        case 112: break;
        case 1: 
          { return MathematicaElementTypes.COMMENT_CONTENT;
          }
        case 113: break;
        case 76: 
          { return MathematicaElementTypes.UNSET;
          }
        case 114: break;
        case 73: 
          { return MathematicaElementTypes.RULE_DELAYED;
          }
        case 115: break;
        case 50: 
          { return MathematicaElementTypes.UP_SET;
          }
        case 116: break;
        case 56: 
          { yybegin(GET_START); return MathematicaElementTypes.GET;
          }
        case 117: break;
        case 41: 
          { yybegin(YYINITIAL); return MathematicaElementTypes.STRINGIFIED_IDENTIFIER;
          }
        case 118: break;
        case 42: 
          { yybegin(YYINITIAL); yypushstate(IN_STRING); return MathematicaElementTypes.STRING_LITERAL_BEGIN;
          }
        case 119: break;
        case 82: 
          { return MathematicaElementTypes.STRING_EXPRESSION;
          }
        case 120: break;
        case 54: 
          { return MathematicaElementTypes.ASSOCIATION_SLOT;
          }
        case 121: break;
        case 17: 
          { return MathematicaElementTypes.OUT;
          }
        case 122: break;
        case 5: 
          { return MathematicaElementTypes.LEFT_PAR;
          }
        case 123: break;
        case 55: 
          { return MathematicaElementTypes.SLOT_SEQUENCE;
          }
        case 124: break;
        case 18: 
          { yypushstate(IN_STRING); return MathematicaElementTypes.STRING_LITERAL_BEGIN;
          }
        case 125: break;
        case 59: 
          { return MathematicaElementTypes.LESS_EQUAL;
          }
        case 126: break;
        case 33: 
          { return MathematicaElementTypes.BLANK;
          }
        case 127: break;
        case 22: 
          { return MathematicaElementTypes.ALTERNATIVE;
          }
        case 128: break;
        case 67: 
          { return MathematicaElementTypes.REPLACE_ALL;
          }
        case 129: break;
        case 52: 
          { return MathematicaElementTypes.RULE;
          }
        case 130: break;
        case 87: 
          { return MathematicaElementTypes.REPEATED_NULL;
          }
        case 131: break;
        case 83: 
          { return MathematicaElementTypes.DEFAULT;
          }
        case 132: break;
        case 28: 
          { return MathematicaElementTypes.SEMICOLON;
          }
        case 133: break;
        case 16: 
          { return MathematicaElementTypes.SLOT;
          }
        case 134: break;
        case 29: 
          { return MathematicaElementTypes.EXCLAMATION_MARK;
          }
        case 135: break;
        case 15: 
          { return MathematicaElementTypes.MINUS;
          }
        case 136: break;
        case 43: 
          { yypushback(1); yybegin(GET_RHS);
          }
        case 137: break;
        case 12: 
          { return MathematicaElementTypes.NUMBER;
          }
        case 138: break;
        case 63: 
          { return MathematicaElementTypes.GREATER_EQUAL;
          }
        case 139: break;
        case 90: 
          { return MathematicaElementTypes.APPLY1;
          }
        case 140: break;
        case 10: 
          { return MathematicaElementTypes.RIGHT_BRACKET;
          }
        case 141: break;
        case 68: 
          { return MathematicaElementTypes.MAP;
          }
        case 142: break;
        case 61: 
          { return MathematicaElementTypes.RIGHT_ASSOCIATION;
          }
        case 143: break;
        case 86: 
          { yypopstate(); return MathematicaElementTypes.COMMENT_END;
          }
        case 144: break;
        case 34: 
          { return MathematicaElementTypes.QUESTION_MARK;
          }
        case 145: break;
        case 20: 
          { return MathematicaElementTypes.RIGHT_BRACE;
          }
        case 146: break;
        case 75: 
          { return MathematicaElementTypes.SET_DELAYED;
          }
        case 147: break;
        case 74: 
          { return MathematicaElementTypes.DOUBLE_COLON;
          }
        case 148: break;
        case 19: 
          { return MathematicaElementTypes.LEFT_BRACE;
          }
        case 149: break;
        case 88: 
          { return MathematicaElementTypes.UP_SET_DELAYED;
          }
        case 150: break;
        case 36: 
          { return MathematicaElementTypes.DERIVATIVE;
          }
        case 151: break;
        case 3: 
          { return MathematicaElementTypes.LINE_BREAK;
          }
        case 152: break;
        case 65: 
          { return MathematicaElementTypes.APPLY;
          }
        case 153: break;
        case 21: 
          { return MathematicaElementTypes.LESS;
          }
        case 154: break;
        case 7: 
          { return MathematicaElementTypes.RIGHT_PAR;
          }
        case 155: break;
        case 81: 
          { return MathematicaElementTypes.INCREMENT;
          }
        case 156: break;
        case 45: 
          { yypushstate(IN_COMMENT); return MathematicaElementTypes.COMMENT_START;
          }
        case 157: break;
        case 71: 
          { return MathematicaElementTypes.DIVIDE_BY;
          }
        case 158: break;
        case 77: 
          { return MathematicaElementTypes.EQUAL;
          }
        case 159: break;
        case 62: 
          { yybegin(PUT_START); return MathematicaElementTypes.PUT;
          }
        case 160: break;
        case 24: 
          { return MathematicaElementTypes.PREFIX_CALL;
          }
        case 161: break;
        case 80: 
          { return MathematicaElementTypes.ADD_TO;
          }
        case 162: break;
        case 2: 
          { return MathematicaElementTypes.BAD_CHARACTER;
          }
        case 163: break;
        case 57: 
          { return MathematicaElementTypes.LEFT_ASSOCIATION;
          }
        case 164: break;
        case 14: 
          { return MathematicaElementTypes.POWER;
          }
        case 165: break;
        case 26: 
          { return MathematicaElementTypes.COLON;
          }
        case 166: break;
        case 93: 
          { yypushback(2); return MathematicaElementTypes.SET;
          }
        case 167: break;
        case 69: 
          { return MathematicaElementTypes.POSTFIX;
          }
        case 168: break;
        case 11: 
          { return MathematicaElementTypes.BACK_TICK;
          }
        case 169: break;
        case 58: 
          { return MathematicaElementTypes.STRING_JOIN;
          }
        case 170: break;
        case 89: 
          { yybegin(PUT_START); return MathematicaElementTypes.PUT_APPEND;
          }
        case 171: break;
        case 13: 
          { return MathematicaElementTypes.POINT;
          }
        case 172: break;
        case 91: 
          { return MathematicaElementTypes.REPLACE_REPEATED;
          }
        case 173: break;
        case 96: 
          { return MathematicaElementTypes.BLANK_NULL_SEQUENCE;
          }
        case 174: break;
        case 85: 
          { return MathematicaElementTypes.AND;
          }
        case 175: break;
        case 46: 
          { return MathematicaElementTypes.NON_COMMUTATIVE_MULTIPLY;
          }
        case 176: break;
        case 51: 
          { return MathematicaElementTypes.DECREMENT;
          }
        case 177: break;
        case 38: 
          { yypopstate(); return MathematicaElementTypes.STRING_LITERAL_END;
          }
        case 178: break;
        case 66: 
          { return MathematicaElementTypes.RIGHT_COMPOSITION;
          }
        case 179: break;
        case 48: 
          { return MathematicaElementTypes.ACCURACY;
          }
        case 180: break;
        case 23: 
          { return MathematicaElementTypes.GREATER;
          }
        case 181: break;
        case 47: 
          { return MathematicaElementTypes.TIMES_BY;
          }
        case 182: break;
        case 95: 
          { return MathematicaElementTypes.UNSAME_Q;
          }
        case 183: break;
        case 37: 
          { return MathematicaElementTypes.STRING_LITERAL;
          }
        case 184: break;
        case 98: 
          { return MathematicaElementTypes.COMMENT_SECTION;
          }
        case 185: break;
        case 72: 
          { return MathematicaElementTypes.CONDITION;
          }
        case 186: break;
        case 49: 
          { return MathematicaElementTypes.REPEATED;
          }
        case 187: break;
        case 32: 
          { return MathematicaElementTypes.COMMA;
          }
        case 188: break;
        case 27: 
          { return MathematicaElementTypes.SET;
          }
        case 189: break;
        case 53: 
          { return MathematicaElementTypes.SUBTRACT_FROM;
          }
        case 190: break;
        case 78: 
          { return MathematicaElementTypes.SPAN;
          }
        case 191: break;
        case 8: 
          { return MathematicaElementTypes.IDENTIFIER;
          }
        case 192: break;
        case 92: 
          { return MathematicaElementTypes.MAP_ALL;
          }
        case 193: break;
        case 94: 
          { return MathematicaElementTypes.SAME_Q;
          }
        case 194: break;
        case 30: 
          { return MathematicaElementTypes.PLUS;
          }
        case 195: break;
        case 35: 
          { return MathematicaElementTypes.FUNCTION;
          }
        case 196: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
