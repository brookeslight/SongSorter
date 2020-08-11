package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BearSongSorter extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7727485180935933277L;
	private boolean running;
	private Thread thread;
	private JFrame frame;
	private JButton b1;
	private JButton b2;
	private JScrollPane jsp;
	private JList<String> list;
	private int toggle; //0 = false //1 = true // -1 = waiting
	private String[] songs = {
			"NYLA (old)",
			"Califormula", "Grey L.A.", "Hotel Andrea", "The Lobby", "N.Y.E.", "Valley Girls", //The Afterglow
			"4U", "I Needed You" , "Ain't Trippin" , "90210 (feat. G-Eazy)" , "Ain't Love" , "IDFC" , "Waste Away (feat. Devon Baldwin)" , "Dirty Laundry" , "My Heart is Lost" , "Deadroses", //Deadroses
			"4U (Acoustic)", "90210 (Acoustic)" , "Dirty Laundry (Acoustic)" , "IDFC (Acoustic)" , "Weak When Ur Around (Acoustic)", //Dead (Acoustic)
			"Different Hos", "Don't Stop" , "Help (feat. Maejor)" , "Hustler" , "Nervous" , "Oh Lord" , "Paragraphs" , "Slide Thru (feat. Jerry Good)" , "Verbatim" , "Where was U?", //Help
			"Girls Like U", "Obvious (feat. Mike Posner)" , "Something Real" , "Shake Ya Ass (feat. P-Lo)" , "Suckerz", //Drink Bleach
			"Sniffing Vicodin in Paris", "Spent all my money on Rick Ownen's Cargo Pants" , "Rly Real" , "Sometimes I Want 2 Die" , "Princess Complex" , "My Bad Luv" , "Flirt Right Back" , "Wanderlust", //Cashmere Noose
			"hell is where i dreamt of you and woke up alone", "moodz (feat. 24hrs)" , "i miss the old u" , "wish u the best" , "juicy sweatsuits (feat. Juicy J)" , "double" , "if i could i would feel nothing" , "chateau" , "make daddy pround", //digital druglord
			"gucci linen (feat. 2 Chainz)", "bright pink tims (feat. Cam'ron)" , "playboy shit (feat. lil aaron)" , "e.z. (feat. Machine Gun Kelly)" , "up in this (with Tinashe)" , "anxiety (with FRND)" , "thursday/froze over - \"interlude\"" , "i hope your whole life sux" , "down 4 u (feat. T-Pain)" , "glo_up (feat. Rick Ross)" , "top priority (with Ne-Yo)" , "g2g ttly (feat. THEY.)" , "candyapple (feat. Paul Wall & RiFF RAFF)", "santa monica & la brea", //cybersex
			"PINK ROLEX", "HATE MY GUTS" , "DRUG DEALER" , "SWEAR TO GOD" , "MAKE A MESS" , "SICK OF IT ALL" , "CHANGES" , "HIGH1X" , "DOWN" , "BURNT AF" , "HEARTBROKEN" , "1 SIDED LOVE" , "LOSING YOU", "ITS ALL GONNA BURN", "DEAD BALLOONS", "TOO CLOSE", "DEAD TO ME", "NYLA", //ANONYMOUS
			"hot girl bummer", "me & ur ghost" , "queen of broken hearts" , "i feel bad" , "i feel 2 much" , "i felt that", //Everything Means Nothing
	};

	public static void main(String[] args) {
		new BearSongSorter().start();
	}
	
	public synchronized void start() {
		if(this.running == true) {
			return;
		}
		this.thread = new Thread(this);
		this.thread.start();
		this.running = true;
	}
	
	public synchronized void stop() {
		this.running = false;
		//clean up
	}
	
	@Override
	public void run() {
		this.init();
		this.sort(this.songs, 0, this.songs.length-1);
		this.b1.setText("DONE!");
		this.b1.setEnabled(false);
		this.b2.setText("DONE!");
		this.b2.setEnabled(false);
	}
	
	private void init() {
		this.frame = new JFrame("Blackbear Song Sorter");
		this.frame.setSize(960, 800);
		
		this.b1 = new JButton("Button 1");
		this.b1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.b1.setBackground(Color.DARK_GRAY);
		this.b1.setForeground(Color.white);
		this.b1.setFocusable(false);
		this.b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggle = 1;
			}
		});
		
		this.b2 = new JButton("Button 2");
		this.b2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.b2.setBackground(Color.DARK_GRAY);
		this.b2.setForeground(Color.white);
		this.b2.setFocusable(false);
		this.b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggle = 0;
				
			}
		});
		
		this.list = new JList<String>(this.songs);
		this.list.setBackground(Color.DARK_GRAY);
		this.list.setForeground(Color.white);
		this.list.setFocusable(false);
		this.list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		this.jsp = new JScrollPane(this.list);
		this.jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.jsp.setBackground(Color.DARK_GRAY);
		this.jsp.setForeground(Color.white);
		
		this.setLayout(new GridLayout(2, 1));
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.add(this.b1);
		this.add(this.b2);
		

		this.frame.add(this, BorderLayout.CENTER);
		this.frame.add(this.jsp, BorderLayout.EAST);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.requestFocus();
	}
	
	private void sort(String arr[], int start, int end) {
		if (start < end) {
			int pIndex = partition(arr, start, end);
			this.sort(arr, start, pIndex - 1);
			this.sort(arr, pIndex + 1, end);
		}
	}

	private int partition(String arr[], int start, int end) {
		String pivot = arr[end];
		int pIndex = start;
		for (int i = start; i < end; i++) {
			
			this.b1.setText(arr[i]);
			this.b2.setText(pivot);
			this.toggle = -1;
			while(this.toggle == -1) {
				//waiting
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (this.toggle == 1) {
				this.swap(arr, i, pIndex);
				pIndex++;
			}
		}
		this.swap(arr, pIndex, end);
		return pIndex;
	}

	private void swap(String arr[], int x, int y) {
		String temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
		this.list.setListData(this.songs);
	}

}