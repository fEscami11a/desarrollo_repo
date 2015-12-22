package mx.com.interware.spira.message.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;


public class FmtUtil {
	
	private static Logger log=Logger.getLogger(FmtUtil.class);
	
	private static Map builderMap;
	private static Map formattersCache;

	static {	
		formattersCache=new HashMap();
		builderMap=new HashMap();	
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_X), new XFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_VARCHAR), new CharFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_NINE), new NineFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_NINE_DECIMAL), new NineDecimalFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_OPER_NINE), new OperNineFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_OPER_NINE_DECIMAL), new OperNineDecimalFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_NUMBER), new NumberFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_NUMBER_DECIMAL), new NumberDecimalFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_OPER_NUMBER), new OperNumberFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_OPER_NUMBER_DECIMAL), new OperNumberDecimalFormatterBuilder());
		
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_VARIABLE),new VariableFormatterBuilder());
				
		//Formatos inconclusos		
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_DATE), new DateFormatterBuilder());
		builderMap.put(Pattern.compile(PatternConstant.PATTERN_TIME), new TimeFormatterBuilder());
		
		log.debug(builderMap.keySet());
	}
		
	public static synchronized Formatter getFormatter(String fmt) throws InvalidFormatException {
		fmt=fmt.toUpperCase();
		Formatter formatter=(Formatter)formattersCache.get(fmt);
		log.debug("Formatter for:"+fmt+" is:"+formatter);
		if (formatter==null) {
			for (Iterator iter = builderMap.keySet().iterator(); iter.hasNext();) {
				Pattern p=(Pattern)iter.next();
				if (p.matcher(fmt).matches()) {
					FormatterBuilder builder = (FormatterBuilder)builderMap.get(p);
					formatter = builder.create(fmt);
					if (formatter != null && !fmt.equalsIgnoreCase("Variable")) { // lo de tipo variable no deben ponerse en el cache!
						formattersCache.put(fmt,formatter);
						break;
					}
				}
			}
		}
		return formatter;
	}

	public static void main(String[] args) {
	}
}
