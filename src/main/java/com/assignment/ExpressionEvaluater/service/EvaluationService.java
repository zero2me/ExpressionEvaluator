package com.assignment.ExpressionEvaluater.service;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
	int close_parenthesis=0;
	int joincount=0;
	int concatcount=0;
	int randomcount=0;
	//private String exp="";
	 static Random random = new Random();
	//PERFORM FUNCTION-->evaluate expression
	  private static String performFunction(Stack<String> function, Stack<String> literals) {
	        String result="";
	        String functionName = function.pop();
	        List<String> literalsList = new ArrayList<>();
	        while(!literals.isEmpty() && !literals.peek().equalsIgnoreCase("("))
	        {
	            literalsList.add(literals.pop());
	        }
	        if (functionName.equalsIgnoreCase("concatenate")) {
	            Collections.reverse(literalsList);
	            for (String x : literalsList)
	                result += x;
	        }
	        else if(functionName.equalsIgnoreCase("pickRandom"))
	        {
	            result = literalsList.get(random.nextInt(literalsList.size()));
	        }
	        else if(functionName.equalsIgnoreCase("join"))
	        {
	            String delimiter = literalsList.remove(0);
	            System.out.println("delimiter - "+ delimiter);
	            result = String.join(delimiter, literalsList);
	        }
	        literals.pop();
	        return result;
	    }
	
	
	
	
	
	public boolean checkExpression(String exp)
	{
		if(exp=="'''")
			return false;
		else
		{
			if(exp.charAt(0)=='\'' && exp.charAt(exp.length()-1)=='\'')
			{
				return true;
			}
			else 
			{//counting methods and validating
				
				joincount=countMatches(exp,"join(");
				concatcount=countMatches(exp,"concatenate(");
				randomcount=countMatches(exp,"pickrandom(");
				close_parenthesis=countMatches(exp,")");
				if(joincount+concatcount+randomcount==close_parenthesis)
					return true;
				return false;
			}
			
		
		}
	}
	public String evaluateExpression(String exp)
		{
		if(checkExpression(exp))
		{
			if(exp.charAt(0)=='\'' && exp.charAt(exp.length()-1)=='\'')
			{
				return exp;
			}
			List<String> array = List.of(exp.split(","));
	        System.out.println(exp.toString());

	        Stack<String> function = new Stack<String>();//stack for function call store
	        Stack<String> literals = new Stack<String>();//stack to hold parameters
	        String ans="";//variable to store evaluated value
	        int indexOfOpenBracket=0;
	        String functionName="", literal="";

	        try {
	            for (String token : array) {
	                token = token.trim();
	                if (token.contains("(")) {
	                    indexOfOpenBracket = token.indexOf('(');
	                    functionName = token.substring(0, indexOfOpenBracket);
	                    literal = token.substring(indexOfOpenBracket + 2, token.length() - 1);

	                    function.push(functionName);
	                    literals.push(String.valueOf(token.charAt(indexOfOpenBracket)));
	                    literals.push(literal);
	                } else if (token.contains(")")) {
	                    int numOfClosingBrackets = token.length() - token.lastIndexOf("\'") - 1;
	                    if (token.length() - numOfClosingBrackets > 0) {
	                        String onlyLiteral = token.substring(0, token.length() - numOfClosingBrackets);
	                        if (onlyLiteral.length() == 1)
	                            literals.push(",");
	                        else
	                            literals.push(onlyLiteral.substring(1, onlyLiteral.length() - 1));
	                    }
	                    for (int i = 0; i < numOfClosingBrackets; i++) {
	                        String result = performFunction(function, literals);
	                        literals.push(result);
	                    }
	                } else if (token.length() > 2) {
	                    literals.push(token.substring(1, token.length() - 1));
	                }
	            }
	          ans=ans+literals.pop();
	            //System.out.println(literals.pop()+"test");
	        }
	       
	        catch (Exception e)
	        {
	            return "Unable to perform operation - "+ e.getMessage();
	        }
	        return ans;
			
		}
		
		return "Invalid Format";
	}

	public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
 
    /* Counts how many times the substring appears in the larger string. */
    public static int countMatches(String text, String str)
    {
        if (isEmpty(text) || isEmpty(str)) {
            return 0;
        }
 
        int index = 0, count = 0;
        while (true)
        {
            index = text.indexOf(str, index);
            if (index != -1)
            {
                count ++;
                index += str.length();
            }
            else {
                break;
            }
        }
 
        return count;
    }

}
