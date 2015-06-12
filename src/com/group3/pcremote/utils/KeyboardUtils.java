package com.group3.pcremote.utils;

import com.group3.pcremote.constant.KeyboardConstant;

public class KeyboardUtils {

	public static int toKeycode(char character) {
		int keycode = -1;
		switch (character) {
		/** Key code constant: '0' key. */
		case '0':
			keycode = KeyboardConstant.VK_0;
			break;
		/** Key code constant: '1' key. */
		case '1':
			keycode = KeyboardConstant.VK_1;
			break;
		/** Key code constant: '2' key. */
		case '2':
			keycode = KeyboardConstant.VK_2;
			break;
		/** Key code constant: '3' key. */
		case '3':
			keycode = KeyboardConstant.VK_3;
			break;
		/** Key code constant: '4' key. */
		case '4':
			keycode = KeyboardConstant.VK_4;
			break;
		/** Key code constant: '5' key. */
		case '5':
			keycode = KeyboardConstant.VK_5;
			break;
		/** Key code constant: '6' key. */
		case '6':
			keycode = KeyboardConstant.VK_6;
			break;
		/** Key code constant: '7' key. */
		case '7':
			keycode = KeyboardConstant.VK_7;
			break;
		/** Key code constant: '8' key. */
		case '8':
			keycode = KeyboardConstant.VK_8;
			break;
		/** Key code constant: '9' key. */
		case '9':
			keycode = KeyboardConstant.VK_9;
			break;
		case 'a':
			keycode = KeyboardConstant.VK_A;
			break;
		case 'b':
			keycode = KeyboardConstant.VK_B;
			break;
		case 'c':
			keycode = KeyboardConstant.VK_C;
			break;
		case 'd':
			keycode = KeyboardConstant.VK_D;
			break;
		case 'e':
			keycode = KeyboardConstant.VK_E;
			break;
		case 'f':
			keycode = KeyboardConstant.VK_F;
			break;
		case 'g':
			keycode = KeyboardConstant.VK_G;
			break;
		case 'h':
			keycode = KeyboardConstant.VK_H;
			break;
		case 'i':
			keycode = KeyboardConstant.VK_I;
			break;
		case 'j':
			keycode = KeyboardConstant.VK_J;
			break;
		case 'k':
			keycode = KeyboardConstant.VK_K;
			break;
		case 'l':
			keycode = KeyboardConstant.VK_L;
			break;
		case 'm':
			keycode = KeyboardConstant.VK_M;
			break;
		case 'n':
			keycode = KeyboardConstant.VK_N;
			break;
		case 'o':
			keycode = KeyboardConstant.VK_O;
			break;
		case 'p':
			keycode = KeyboardConstant.VK_P;
			break;
		case 'q':
			keycode = KeyboardConstant.VK_Q;
			break;
		case 'r':
			keycode = KeyboardConstant.VK_R;
			break;
		case 's':
			keycode = KeyboardConstant.VK_S;
			break;
		case 't':
			keycode = KeyboardConstant.VK_T;
			break;
		case 'u':
			keycode = KeyboardConstant.VK_U;
			break;
		case 'v':
			keycode = KeyboardConstant.VK_V;
			break;
		case 'w':
			keycode = KeyboardConstant.VK_W;
			break;
		case 'x':
			keycode = KeyboardConstant.VK_X;
			break;
		case 'y':
			keycode = KeyboardConstant.VK_Y;
			break;
		case 'z':
			keycode = KeyboardConstant.VK_Z;
			break;
		case ' ':
			keycode = KeyboardConstant.VK_SPACE;
			break;
		case '\n':
			keycode = KeyboardConstant.VK_ENTER;
			break;
		case '\b':
			keycode = KeyboardConstant.VK_BACK_SPACE;
			break;
		case '\t':
			keycode = KeyboardConstant.VK_TAB;
			break;
		case ',':
			keycode = KeyboardConstant.VK_COMMA;
			break;
		case '-':
			keycode = KeyboardConstant.VK_MINUS;
			break;
		case '*':
			keycode = KeyboardConstant.VK_MULTIPLY;
			break;
		case '+':
			keycode = KeyboardConstant.VK_PLUS;
			break;
		case '.':
			keycode = KeyboardConstant.VK_PERIOD;
			break;
		case '/':
			keycode = KeyboardConstant.VK_SLASH;
			break;
		case ';':
			keycode = KeyboardConstant.VK_SEMICOLON;
			break;
		case '=':
			keycode = KeyboardConstant.VK_EQUALS;
			break;
		case '[':
			keycode = KeyboardConstant.VK_OPEN_BRACKET;
			break;
		case ']':
			keycode = KeyboardConstant.VK_CLOSE_BRACKET;
			break;
		case '\'':
			keycode = KeyboardConstant.VK_QUOTE;
			break;
		case '`':
			keycode = KeyboardConstant.VK_BACK_QUOTE;
			break;
		case '!':
			keycode = KeyboardConstant.VK_EXCLAMATION_MARK;
			break;
		case '(':
			keycode = KeyboardConstant.VK_LEFT_PARENTHESIS;
			break;
		case ')':
			keycode = KeyboardConstant.VK_RIGHT_PARENTHESIS;
			break;
		case '\\':
			keycode = KeyboardConstant.VK_BACK_SLASH;
			break;
		case ':':
			keycode = KeyboardConstant.VK_COLON;
			break;
		case '^':
			keycode = KeyboardConstant.VK_CIRCUMFLEX;
			break;
		case '$':
			keycode = KeyboardConstant.VK_DOLLAR;
			break;
		case '@':
			keycode = KeyboardConstant.VK_AT;
			break;
		case '€':
			keycode = KeyboardConstant.VK_EURO_SIGN;
			break;
		case '#':
			keycode = KeyboardConstant.VK_NUMBER_SIGN;
			break;
		case '_':
			keycode = KeyboardConstant.VK_UNDERSCORE;
			break;
		}

		return keycode;
	}

}
