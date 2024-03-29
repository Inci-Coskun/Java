package mastermind;

import java.awt.*;
import java.awt.event.*;

public class MasterMind implements WindowListener, ActionListener

{
	private Frame a;
	private Panel b;
	private TextField t1;
	private TextField t2;
	private Button d1;
	private int random;
	
	public MasterMind()

	{
		a = new Frame();
		b = new Panel();
		
		t1 = new TextField(25);
		t1.setText("0000");
		d1 = new Button("OK");

		t2 = new TextField(25);
		t2.setText("");
		t2.setEditable(false);
		
		a.add(b);

		b.add(t1);
		b.add(d1);
		b.add(t2);


		b.setBackground(Color.red);
		
		d1.addActionListener(this);

		a.addWindowListener(this);
		
		a.pack();
		a.setSize(400,200);
		a.setResizable(false);
		a.setVisible(true);


		
		do {
			random = (int) (1234 + (8642 * Math.random()));
		}
		while(!(random/1000 != random/100%10 && random/1000 != random/10%10 && random/1000 != random%10 && random/100%10 != random/10%10 && random/100%10 != random%10 && random/10%10 != random%10) );
		System.out.println(random);

	
	}

	public static void main(String args[])
	
	{
		MasterMind uygulama = new MasterMind();
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
	    String action = e.getActionCommand();
	    String s1 = t1.getText();

	    try {
	        int guess = Integer.parseInt(s1);
	        if (guess < 1234) {
	            t2.setText("Enter a value greater than 1234");
	        } else if (guess > 9876) {
	            t2.setText("Enter a value less than 9876");
	        } else if (hasUniqueDigits(guess)) {
	            String rand = String.valueOf(random);
	            String result = "";
	            int[] guessDigits = getDigits(guess);
	            int[] randomDigits = getDigits(random);

	            int rightPlace = 0;
	            int wrongPlace = 0;

	            for (int i = 0; i < 4; i++) {
	                for (int j = 0; j < 4; j++) {
	                    if (guessDigits[i] == randomDigits[j]) {
	                        if (i == j) {
	                            rightPlace++;
	                        } else {
	                            wrongPlace++;
	                        }
	                    }
	                }
	            }

	            if (rightPlace != 0) {
	                result += "+" + rightPlace + " ";
	            }

	            if (wrongPlace != 0) {
	                result += "-" + wrongPlace + " ";
	            }

	            if (wrongPlace == 0 && rightPlace == 0) {
	                result = "0";
	            }
	            t2.setText(result);
	        }
	    } catch (NumberFormatException ex) {
	        t2.setText("Please enter a number.");
	    }
	}

	private boolean hasUniqueDigits(int number) {
	    int[] digits = new int[10];
	    while (number > 0) {
	        int digit = number % 10;
	        if (digits[digit] == 1) {
	            return false;
	        }
	        digits[digit] = 1;
	        number /= 10;
	    }
	    return true;
	}

	private int[] getDigits(int number) {
	    int[] digits = new int[4];
	    for (int i = 3; i >= 0; i--) {
	        digits[i] = number % 10;
	        number /= 10;
	    }
	    return digits;
	}

	
	public void windowOpened(WindowEvent e) {}
	
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
	
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

}

