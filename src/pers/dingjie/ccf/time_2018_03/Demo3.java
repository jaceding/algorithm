package pers.dingjie.ccf.time_2018_03;

import java.util.ArrayList;
import java.util.Scanner;

/**
问题描述
　　URL 映射是诸如 Django、Ruby on Rails 等网页框架 (web frameworks) 的一个重要组件。对于从浏览器发来的 HTTP 请求，URL 映射模块会解析请求中的 URL 地址，并将其分派给相应的处理代码。现在，请你来实现一个简单的 URL 映射功能。
　　本题中 URL 映射功能的配置由若干条 URL 映射规则组成。当一个请求到达时，URL 映射功能会将请求中的 URL 地址按照配置的先后顺序逐一与这些规则进行匹配。当遇到第一条完全匹配的规则时，匹配成功，得到匹配的规则以及匹配的参数。若不能匹配任何一条规则，则匹配失败。
　　本题输入的 URL 地址是以斜杠 / 作为分隔符的路径，保证以斜杠开头。其他合法字符还包括大小写英文字母、阿拉伯数字、减号 -、下划线 _ 和小数点 .。例如，/person/123/ 是一个合法的 URL 地址，而 /person/123? 则不合法（存在不合法的字符问号 ?）。另外，英文字母区分大小写，因此 /case/ 和 /CAse/ 是不同的 URL 地址。
　　对于 URL 映射规则，同样是以斜杠开始。除了可以是正常的 URL 地址外，还可以包含参数，有以下 3 种：
　　字符串 <str>：用于匹配一段字符串，注意字符串里不能包含斜杠。例如，abcde0123。
　　整数 <int>：用于匹配一个不带符号的整数，全部由阿拉伯数字组成。例如，01234。
　　路径 <path>：用于匹配一段字符串，字符串可以包含斜杠。例如，abcd/0123/。
　　以上 3 种参数都必须匹配非空的字符串。简便起见，题目规定规则中 <str> 和 <int> 前面一定是斜杠，后面要么是斜杠，要么是规则的结束（也就是该参数是规则的最后一部分）。而 <path> 的前面一定是斜杠，后面一定是规则的结束。无论是 URL 地址还是规则，都不会出现连续的斜杠。
输入格式
　　输入第一行是两个正整数 n 和 m，分别表示 URL 映射的规则条数和待处理的 URL 地址个数，中间用一个空格字符分隔。
　　第 2 行至第 n+1 行按匹配的先后顺序描述 URL 映射规则的配置信息。第 i+1 行包含两个字符串 pi 和 ri，其中 pi 表示 URL 匹配的规则，ri 表示这条 URL 匹配的名字。两个字符串都非空，且不包含空格字符，两者中间用一个空格字符分隔。
　　第 n+2 行至第 n+m+1 行描述待处理的 URL 地址。第 n+1+i 行包含一个字符串 qi，表示待处理的 URL 地址，字符串中不包含空格字符。
输出格式
　　输入共 m 行，第 i 行表示 qi 的匹配结果。如果匹配成功，设匹配了规则 pj ，则输出对应的 rj。同时，如果规则中有参数，则在同一行内依次输出匹配后的参数。注意整数参数输出时要把前导零去掉。相邻两项之间用一个空格字符分隔。如果匹配失败，则输出 404。
样例输入
5 4
/articles/2003/ special_case_2003
/articles/<int>/ year_archive
/articles/<int>/<int>/ month_archive
/articles/<int>/<int>/<str>/ article_detail
/static/<path> static_serve
/articles/2004/
/articles/1985/09/aloha/
/articles/hello/
/static/js/jquery.js
样例输出
year_archive 2004
article_detail 1985 9 aloha
404
static_serve js/jquery.js
样例说明
　　对于第 1 个地址 /articles/2004/，无法匹配第 1 条规则，可以匹配第 2 条规则，参数为 2004。
　　对于第 2 个地址 /articles/1985/09/aloha/，只能匹配第 4 条规则，参数依次为 1985、9（已经去掉前导零）和 aloha。
　　对于第 3 个地址 /articles/hello/，无法匹配任何一条规则。
　　对于第 4 个地址 /static/js/jquery.js，可以匹配最后一条规则，参数为 js/jquery.js。
数据规模和约定
　　1 ≤ n ≤ 100，1 ≤ m ≤ 100。
　　所有输入行的长度不超过 100 个字符（不包含换行符）。
　　保证输入的规则都是合法的。
 */

/**
 * @description : URL映射
 * @author      : dingjie
 * @date 	    : 2018年8月28日 下午5:31:28 
 */
public class Demo3 {

	public static void main(String[] args) {
		int m,n;
		Scanner scanner = new Scanner(System.in);
		n = scanner.nextInt();
		m = scanner.nextInt();
		scanner.nextLine();
		Rule[] rules = new Rule[n];
		String[] requstStrArr = new String[m];
		
		for (int i = 0; i < n; i++) {
			String input = scanner.nextLine();
			String[] strArr = input.substring(1, input.length()).split(" ");
			rules[i]=new Rule(strArr[0],strArr[1]);
		}
		
		for (int i = 0; i < m; i++) {
			String input = scanner.nextLine();
			requstStrArr[i] = input.substring(1, input.length());
		}
		scanner.close();
		
		for (int i = 0; i < requstStrArr.length; i++) {
			ismatch(requstStrArr[i],rules);
		}
		
	}
	
	public static void ismatch(String requestStr,Rule[] rules) {
		String[] param1 = requestStr.split("/");  //解析请求
		boolean success = false;         //是否成功标志
		
		for (int j = 0; j < rules.length; j++) {
			if(success) {
				//以及和一个规则匹配，则无需继续匹配
				break;
			}
			
			ArrayList<String> paramStack = new ArrayList<String>(); //存储已匹配的字符串
			String[] param2 = rules[j].getMapping().split("/");   //解析请求
			
			int k = 0;			
			while(k<param1.length && k<param2.length) {
				if(param2[k].equals("<path>")) {
					//输出匹配信息 此情况比较特殊
					StringBuffer mapped = new StringBuffer();
					for (int l = k; l < param1.length; l++) {
						if(k == 0) {
							mapped.append("/");
						}
						if(l == param1.length-1) {
							mapped.append(param1[l]);
						}else {
							mapped.append(param1[l] + "/");
						}
					}
					System.out.println(rules[j].getResult()+" "+mapped.toString());
					success = true;
					break;
				}else if(param2[k].equals("<int>")) {
					try {
						paramStack.add(Integer.parseInt(param1[k])+"");
						if(k < param1.length-1) {
							//继续匹配
							k++;
							continue;
						}else {
							//输出匹配信息
							outputMessage(param1,rules[j],paramStack,k);
							success = true;
							break;
						}	
					}catch(Exception e) {
						if(j == rules.length-1) {
							System.out.println("404");
						}
						//不匹配
						break;
					}
				}else if(param2[k].equals("<str>")) {
					paramStack.add(param1[k]);
					if(k < param1.length-1) {
						//继续匹配
						k++;
						continue;
					}else {
						//输出匹配信息
						outputMessage(param1,rules[j],paramStack,k);
						success = true;
						break;
					}
				}else if(param2[k].equals(param1[k])) {
					if(k < param1.length-1) {
						//继续匹配
						k++;
						continue;
					}else {
						//输出匹配信息
						outputMessage(param1,rules[j],paramStack,k);
						success = true;
						break;
					}
				}else {
					//不匹配
					if(j == rules.length-1) {
						System.out.println("404");
					}
					break;
				}
			}
		}
	}
	
	public static void outputMessage(String[] param1,Rule rule,ArrayList<String> paramStack,int k) {
		StringBuffer mapped = new StringBuffer();
		
		for (int i = 0; i < paramStack.size(); i++) {
			mapped.append(paramStack.get(i)+" ");
		}
		
		for (int l = k+1; l < param1.length; l++) {
			if(k == 0) {
				mapped.append("/");
			}
			if(l == param1.length-1) {
				mapped.append(param1[l]);
			}else {
				mapped.append(param1[l] + "/");
			}
		}
		System.out.println(rule.getResult()+" "+mapped.toString());
	}
}



class Rule{
	
	private String mapping = "";
	
	private String result ="";
	
	public Rule() {
		
	}
	public Rule(String mapping,String result) {
		this.mapping = mapping;
		this.result = result;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}