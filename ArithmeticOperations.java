//Aditya Bilawar

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;

public class ArithmeticOperations {

	Stack<Double> number;
	Stack<Character> operator;
	public ArithmeticOperations(String operationNotReversed) {	
		//first reverse string
		String operation = "";
		for(int i=operationNotReversed.length(); i>0; i--) {
			if(operationNotReversed.substring(i-1, i).equals("("))
				operation+=")";
			else if(operationNotReversed.substring(i-1, i).equals(")"))
				operation+="(";
			else 
				operation+=operationNotReversed.substring(i-1, i);
		}
		//	System.out.println(operation);
		ArrayList<Character> orderOfOperations = new ArrayList<Character>();
		orderOfOperations.add('-'); //have the same presedence
		orderOfOperations.add('+'); //have the same presendence

		orderOfOperations.add('*');//have the same presedence
		orderOfOperations.add('/');//have the same presendence

		orderOfOperations.add('^');//greatest presedence
		String nums = "124356789";
		String open = "(";
		String close = ")";
		String operators = "^/*+-";
		number = new Stack<Double>();
		operator = new Stack<Character>();
		/*Compare the operator of the one you are going to add to peek.
		 * If needed to, remove everything from stack, order them, then place back in stack
		 * do the same with numbers
		 */
		int j = 0;
		for(int i=0;i<operation.length();i++) {
			j = 0;
			if(nums.contains(operation.substring(i, i+1)))
				number.push(Double.valueOf(operation.substring(i, i+1)));
			else if(open.contains(operation.substring(i, i+1)))
				operator.push(operation.charAt(i));
			else if(operators.contains(operation.substring(i, i+1))) {
				while(!operator.isEmpty() && checkPresedence(operator.peek(), operation.charAt(i))) {  //orderOfOperations.indexOf(operator.peek()) > orderOfOperations.indexOf(operation.charAt(i))
					Calculate();
				}
				operator.push(operation.charAt(i));
			}
			else if(close.contains(operation.substring(i, i+1))) {
				while(!operator.peek().equals('(')) {
					Calculate();
				}
				operator.pop(); //does this pop the ( ?
			}

		}
		while(!operator.isEmpty()) {
			Calculate();
		}


	}

	public Boolean checkPresedence(char popped, char incoming) {
		//returns if popped has a greater presedence than incoming using PEDMAS order of operations
		ArrayList<String> orderOfOperations = new ArrayList<String>();
		orderOfOperations.add("+-");
		orderOfOperations.add("/*");
		orderOfOperations.add("^");
		int poppedI = 0;
		for(int i=0; i<orderOfOperations.size();i++) {
			if(orderOfOperations.get(i).contains(String.valueOf(popped))) {
				poppedI = i;
			}
		}
		int incomingI = 0;
		for(int i=0; i<orderOfOperations.size();i++) {
			if(orderOfOperations.get(i).contains(String.valueOf(incoming))) {
				incomingI = i;
			}
		}
		if(poppedI > incomingI)
			return true;

		return false;
	}

	public void Calculate() {
		ArrayList<Character> orderOfOperations = new ArrayList<Character>();
		orderOfOperations.add('/');
		orderOfOperations.add('*');
		orderOfOperations.add('+');
		orderOfOperations.add('-');
		orderOfOperations.add('^');
		Double result = 0.0;
		if(operator.peek()==orderOfOperations.get(0)) {
			//	int lower = number.pop();
			//int higher = number.pop();
			result=( number.pop() /  number.pop());
		}
		if(operator.peek()==orderOfOperations.get(1)) {
			//	int lower = number.pop();
			//	int higher = number.pop();
			result=( number.pop() *  number.pop());
		}
		if(operator.peek()==orderOfOperations.get(2))
		{
			//int lower = number.pop();
			//int higher = number.pop();
			result=( number.pop() +  number.pop());
		}
		if(operator.peek()==orderOfOperations.get(3)) {
			//	int lower = number.pop();
			//	int higher = number.pop();
			result=( number.pop() -  number.pop());
		}
		if(operator.peek()==orderOfOperations.get(4)) {
			//int lower = number.pop();
			//int higher = number.pop();
			result=Math.pow( number.pop(),  number.pop());
		}
		number.push(result);
		operator.pop();
	}

	public Double result() {
		while(number.size()>1) {
			Calculate();
		}
		return number.peek();
	}
	public String toString() {
		Stack<Double> tempNumber = new Stack<Double>();
		Stack<Character> tempOperator = new Stack<Character>();
		String output = "";
		while(!number.isEmpty()) {
			tempNumber.push(number.peek());
			output+=number.pop() + " ";
			if(!operator.isEmpty()) {
				tempOperator.push(operator.peek());
				output+=operator.pop() + " ";
			}
		}
		while(!tempNumber.isEmpty()) {
			number.push(tempNumber.pop());
		}
		while(!tempOperator.isEmpty()) {
			operator.push(tempOperator.pop());
		}
		return output;
	}


	public static void main(String[] args) {
		//ONLY WORKS FOR SINGLE INTEGER NUMBERS 1-9
		Scanner scan = new Scanner(System.in); 
		System.out.println("Enter an Arithmetic Operation: ");
		String operation = scan.nextLine();  

		ArithmeticOperations a = new ArithmeticOperations(operation);
		System.out.println("Your Operation:");
		System.out.println(operation);
		System.out.println("Result");
		System.out.println(a.result());
	}

}