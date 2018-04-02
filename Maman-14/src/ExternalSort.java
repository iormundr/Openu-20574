
/*
*
*	Author: 
*	Student ID : 
*
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ExternalSort {
	public static void main(String[] args) throws IOException {
		String inputname = args[0];
		String outputname = args[1];
		int buff_size = Integer.parseInt(args[2]);
		// String inputname = "C:\\tmp\\input2";
		// String outputname = "C:\\tmp\\output2";		
		Scanner input = new Scanner(new File(inputname));
		Scanner temp_v = new Scanner(new File(inputname));
		int number = 0;
		while (temp_v.hasNext()) {
			temp_v.next();
			number++;
		}
		temp_v = new Scanner(new File(inputname));
		int i = 0;
		String save[] = new String[buff_size];
		if (number <= buff_size) {
			while (temp_v.hasNext()) {
				save[i] = temp_v.next();
				i++;
			}
			in_sort(save);
			PrintWriter out = new PrintWriter(outputname);
			for (i = 0; i < number; i++)
				out.println(save[i]);
			out.close();
			return;
		}
		int count = process_part(input,buff_size);
		name_merge(count);
		num_merge("tmp_merg_cyc" + tempx1 + ".chunk_" + tempy1,"tmp_merg_cyc" + tempx2 + ".chunk_" + tempy2, outputname);
		System.out.println("Merged finished... please check the output file -->  " + outputname + " or the above line");
		temp_v.close();
	}
	public static int process_part(Scanner input, int buff_size) {
		int pass = 0;
		int chunk = 0;
		int count = 0;
		while (input.hasNext()) {
			String[] store = new String[buff_size];
			for (int i = 0; i < buff_size; i++) {
				if (input.hasNext()) {
					store[i] = input.next();
				}
			}
			in_sort(store);
			try {
				FileWriter fr = new FileWriter("tmp_merg_cyc" + String.format("%04d", pass) + ".chunk_" + String.format("%04d", chunk));
				BufferedWriter br = new BufferedWriter(fr);
				PrintWriter out = new PrintWriter(br);
				for (int i = 0; i < store.length; i++) {
					if (store[i] != null) {
						out.println(store[i]);
					} else {
						break;
					}
				}
				out.close();
			} catch (IOException e) {
				System.out.println(e);
			}
			chunk++;
			count++;
		}
		if ((chunk - 1) % 2 == 0) {
			tempx1 = "0000";
			tempy1 = String.format("%04d", chunk - 1);
		}
		return count;
	}
	static String tempx1 = null;
	static String tempy1 = null;
	static String tempx2 = null;
	static String tempy2 = null;

	
	public static void in_sort(String[] store) {
		String temp = "";
		int j = 0;
		for (int i = 1; i < store.length; i++) {
			temp = store[i];
			if (temp == null) {
				break;
			}
			j = i - 1;
			while (j >= 0 && store[j].compareTo(temp) > 0) {
				store[j + 1] = store[j];
				j--;
			}
			store[j + 1] = temp;
		}
	}
	

	public static void num_merge(String inputname1, String inputname2, String output) throws IOException {
		Scanner first = new Scanner(new File(inputname1));
		Scanner second = new Scanner(new File(inputname2));
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		while (first.hasNext()) {
			list1.add(first.next());
		}
		//
		System.out.println("Chunk content " + list1);
		//
		while (second.hasNext()) {
			list2.add(second.next());
		}
		//
		System.out.println("Chunk content " + list2);
		//
		int index1 = 0;
		int index2 = 0;
		String word1 = list1.get(index1);
		String word2 = list2.get(index2);
		PrintWriter out = new PrintWriter(output);
		for (int i = 0; i < list1.size() + list2.size(); i++) {
			if (index1 < list1.size() && index2 < list2.size()) {
				if (word1.compareTo(word2) <= 0) {
					out.println(word1);
					index1++;
					if (index1 < list1.size())
						word1 = list1.get(index1);
				} else {
					out.println(word2);
					index2++;
					if (index2 < list2.size())
						word2 = list2.get(index2);
				}
			}
			if (index1 == list1.size() && index2 < list2.size()) {
				while (index2 < list2.size()) {
					out.println(word2);
					index2++;
					if (index2 < list2.size())
						word2 = list2.get(index2);
				}
			}
			if (index1 < list1.size() && index2 == list2.size()) {
				while (index1 < list1.size()) {
					out.println(word1);
					index1++;
					if (index1 < list1.size())
						word1 = list1.get(index1);
				}
			}
		}
		out.close();
		first.close();
		second.close();
	}
	public static void name_merge(int count) throws IOException {
		int pass1 = 0;
		int pass2 = 1;
		int chunk1 = 0;
		int chunk2 = 0;
		String inputname1 = null;
		String inputname2 = null;
		String output = null;
		while (count > 1) {
			for (chunk1 = 0; chunk1 < count - 1; chunk1 += 2) {
				inputname1 = "tmp_merg_cyc" + String.format("%04d", pass1)
						+ ".chunk_" + String.format("%04d", chunk1);
				inputname2 = "tmp_merg_cyc" + String.format("%04d", pass1)
						+ ".chunk_" + String.format("%04d", chunk1 + 1);
				output = "tmp_merg_cyc" + String.format("%04d", pass2)
						+ ".chunk_" + String.format("%04d", chunk2);
				num_merge(inputname1, inputname2, output);
				
				System.out.print("Merging file " + inputname1 + " with file "+ inputname2);
				System.out.println("\n");
				chunk2++;
			}
			if (tempx1 != null && tempy1 != null && tempx2 != null
					&& tempy2 != null) {
				inputname1 = "tmp_merg_cyc" + tempx1 + ".chunk_" + tempy1;
				inputname2 = "tmp_merg_cyc" + tempx2 + ".chunk_" + tempy2;
				output = "tmp_merg_cyc" + String.format("%04d", pass2)
						+ ".chunk_" + String.format("%04d", chunk2);
				num_merge(inputname1, inputname2, output);
				tempx1 = null;
				tempy1 = null;
				tempx2 = null;
				tempy2 = null;
				if (chunk2 % 2 == 0) {
					tempx1 = String.format("%04d", pass2);
					tempy1 = String.format("%04d", chunk2);
				}
			}
			if ((chunk2 - 1) % 2 == 0) {
				if (tempx1 == null && tempy1 == null) {
					tempx1 = String.format("%04d", pass2);
					tempy1 = String.format("%04d", chunk2 - 1);

				} else {
					tempx2 = String.format("%04d", pass2);
					tempy2 = String.format("%04d", chunk2 - 1);
				}
			}
			pass1++;
			pass2++;
			chunk2 = 0;
			count = count / 2;
		}
	}
	

}