package com.assignment.ExpressionEvaluater.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import com.assignment.ExpressionEvaluater.service.EvaluationService;

@RestController
@ResponseBody

@RequestMapping("/api")
public class ExpressionController {
	@Autowired
	EvaluationService eval;
	
	@GetMapping(value="/evaluate/{expr}")
	//@ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public String evalExpr(@PathVariable(value = "expr") String expression )
	{
		//eval.setExp(expression);
		String input_exp=expression;//.substring(1,expression.length()-1);
		String res=eval.evaluateExpression(input_exp);
		return res;
	}
	

}
