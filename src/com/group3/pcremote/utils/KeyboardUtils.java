package com.group3.pcremote.utils;

import com.group3.pcremote.constant.KeyboardConstant;

public class KeyboardUtils {

	public static int toKeycode(char character) {
		int keycode = -1;
		switch (character) {
		case '0':
			keycode = KeyboardConstant.VK_0;
			break;
		case '1':
			keycode = KeyboardConstant.VK_1;
			break;
		case '2':
			keycode = KeyboardConstant.VK_2;
			break;
		case '3':
			keycode = KeyboardConstant.VK_3;
			break;
		case '4':
			keycode = KeyboardConstant.VK_4;
			break;
		case '5':
			keycode = KeyboardConstant.VK_5;
			break;
		case '6':
			keycode = KeyboardConstant.VK_6;
			break;
		case '7':
			keycode = KeyboardConstant.VK_7;
			break;
		case '8':
			keycode = KeyboardConstant.VK_8;
			break;
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
		case '&':
			keycode = KeyboardConstant.VK_AMPERSAND;
			break;
		case '_':
			keycode = KeyboardConstant.VK_UNDERSCORE;
			break;
		case '%':
			keycode = KeyboardConstant.VK_PERCENT;
			break;
		case '~':
			keycode = KeyboardConstant.VK_TILDE;
			break;
		case '|':
			keycode = KeyboardConstant.VK_VERTICAL_BAR;
			break;
		case '{' :
			keycode = KeyboardConstant.VK_BRACELEFT;
			break;
		case '}':
			keycode = KeyboardConstant.VK_BRACERIGHT;
			break;
		case '<':
			keycode = KeyboardConstant.VK_LESS;
			break;
		case '>':
			keycode = KeyboardConstant.VK_GREATER;
			break;
		case '\"':
			keycode = KeyboardConstant.VK_DOUBLE_QUOTE;
			break;
		case '?':
			keycode = KeyboardConstant.VK_QUESTION;
			break;
		}

		return keycode;
	}

}
