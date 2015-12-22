/*
 * Created on Jul 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.message.util;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PatternConstant {
	// Espacio en blanco.
	public static char SPACE = '\u0020';
	// Expresioon regular para XFormatter --> X(##)							
	protected static String PATTERN_X = "X\\([0-9]+\\)";	//OK			
	// Expresion regular para CharFormatter --> ABCD
	protected static String PATTERN_VARCHAR = "[X]+";
	// Expresion regular para NineFormatter	--> 9(99)									
	protected static String PATTERN_NINE = "9\\([0-9]+\\)";
	// Expresion regular para NineDecimalFormatter --> 9(99).99									
	protected static String PATTERN_NINE_DECIMAL = "9\\([0-9]+\\).[9]+";
	// Expresion regular para OperNineFormatter	--> +9(99)									
	protected static String PATTERN_OPER_NINE = "[\\+\\-]9\\([0-9]+\\)";
	// Expresion regular para OperNineDecimalFormatter --> +9(99).99										
	protected static String PATTERN_OPER_NINE_DECIMAL = "[\\+\\-]9\\([0-9]+\\).[9]+";
	// Expresion regular utilizada para determinar el tamaño --> 99
	protected static String PATTERN_NUMBER = "[0-9]+"; 
	//	Expresion regular para numberDecimalFormatter --> 99.99							
	protected static String PATTERN_NUMBER_DECIMAL = "[9]+.[9]+";	 	
	// Expresion regular para OperNumberFormatter --> +99									
	protected static String PATTERN_OPER_NUMBER = "[\\+\\-][9]+";
	// Expresion regular para OperNumberDecimalFormatter --> +99.99										
	protected static String PATTERN_OPER_NUMBER_DECIMAL = "[\\+\\-][9]+\\.[9]+";
	// Expresion regular para DateFormatter
	protected static final String PATTERN_DATE = "YYYYMMDD";
	// Expresion regular para TimeFormatter	
	protected static final String PATTERN_TIME = "HHMMSS";
	// Expresion regular para VariableFormatter	
	protected static final String PATTERN_VARIABLE = "VARIABLE";
	
}
