import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		new Base_Window();
	}
}

class Profile {
	int score = 0;
	int caution = 0;
	
	Name_field name = new Name_field();
	Score_field score_button = new Score_field(score);
	Caution_field caution_area = new Caution_field(caution);
}

class Score_field extends JButton {
	Font score_font = new Font("³ª´® °íµñ",Font.PLAIN,80);
	
	Score_field(int argi) {
		new JButton();
		super.setFont(score_font);
		super.setText(Integer.toString(argi));
	}
}

class Name_field extends JTextField {
	Font name_font = new Font("³ª´® °íµñ",Font.BOLD,35);
	
	public Name_field() {
		new JTextField(20);
		
		super.setFont(name_font);
		super.setHorizontalAlignment(JTextField.CENTER);
	}
}

class Caution_field extends JPanel {
	ImageIcon ycard = new ImageIcon("D:\\Workspace\\Score Board\\src\\img\\Yellow Card.png");
	ImageIcon rcard = new ImageIcon("D:\\Workspace\\Score Board\\src\\img\\Red Card.png");
	
	public Caution_field(int argi) {
		new JPanel();
		
		super.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(new JLabel(" "));
	}
	
	void refresh(int argi) {
		removeAll();
		
		while(argi > 0) {
			if(argi >= 5) {
				add(new JLabel(rcard));
				argi = argi - 5;
				continue;
			}
			add(new JLabel(ycard));
			argi--;
		}
	}
}

class Result_field extends JDialog {
	JLabel winner = new JLabel();
	JButton close = new JButton("´Ý±â");
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	Result_field(JFrame content, Profile pf1, Profile pf2) {
		super(content, "Result");
		winner.setFont(new Font("°íµñ", Font.BOLD, 60));
		winner.setHorizontalAlignment(JLabel.CENTER);
		setLayout(new BorderLayout());
		setSize(320, 180);
		setLocation(320, 180);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		winner.setText(who_win_score(pf1.score, pf2.score));
		
		add("Center", winner);
		add("South", close);
	}
	
	void result_sound() {
		for(int i=0; i<5;i++) {
			tk.beep();
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {}
		}
	}
	
	public String who_win_score(int score1, int score2) {
		String result = new String("  ");
		if(score1 > score2) {
			result = new String(" È« ½Â ");
		}
		else if(score1 == score2) {
			result = new String(" ¹« ½Â ºÎ ");
		}
		else {
			result = new String(" Ã» ½Â ");
		}
		
		return result;
	}
	
	public String who_win_caution(int caution1, int caution2) {
		String result = new String("");
		
		if(caution1 == 10) {
			result = new String(" Ã» ½Â ");
		}
		else if(caution2 == 10) {
			result = new String(" È« ½Â ");
		}
		
		return result;
	}
}

class Base_Window extends JFrame {
	
	Profile red = new Profile();
	Profile blue = new Profile();
	Result_field result = new Result_field(this, red, blue);
	
	JButton init = new JButton("ÃÊ±âÈ­");
	JButton tStart = new JButton("½ÃÀÛ");
	JButton tPause = new JButton("ÀÏ½ÃÁ¤Áö");
	JButton rCaution = new JButton("È« °æ°í");
	JButton bCaution = new JButton("Ã» °æ°í");
	JLabel vtime = new JLabel("1 : 30");
	JDialog caution_winner = new JDialog(this, "Result");
	
	ThreadGroup tg = new ThreadGroup("TG");
	Runnable r = new Time(1,30);
	Thread t = new Thread(tg, r);

	public Base_Window() {
		setTitle("Score Board");
		setSize(960,540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		vtime.setHorizontalAlignment(JLabel.CENTER);
		vtime.setFont(new Font("³ª´® °íµñ", Font.BOLD, 40));
		
		caution_winner.setSize(320, 180);
		caution_winner.setLocation(320, 180);
		caution_winner.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		red.score_button.addActionListener(button_listener);
		blue.score_button.addActionListener(button_listener);
		rCaution.addActionListener(button_listener);
		bCaution.addActionListener(button_listener);
		init.addActionListener(button_listener);
		tStart.addActionListener(button_listener);
		tPause.addActionListener(button_listener);
		
		red.score_button.setBackground(new Color(255,32,0));
		blue.score_button.setBackground(new Color(0,96,255));
		
		JPanel layer= new JPanel();
		JPanel bt_layer = new JPanel();
		JPanel score_layer = new JPanel();
		JPanel caution_layer = new JPanel();
		JPanel caution_btlayer = new JPanel();
		JPanel util_layer = new JPanel();
		JPanel north_layer = new JPanel();
		JPanel south_layer = new JPanel();
		
		bt_layer.setLayout(new BorderLayout());
		bt_layer.add("Center", score_layer);
		bt_layer.add("South", caution_layer);
		
		score_layer.setLayout(new GridLayout(1,2));
		score_layer.add(red.score_button);
		score_layer.add(blue.score_button);
		
		caution_layer.setLayout(new GridLayout(1,2));
		caution_layer.add(red.caution_area);
		caution_layer.add(blue.caution_area);
		
		caution_btlayer.setLayout(new FlowLayout(FlowLayout.LEFT));
		caution_btlayer.add(rCaution);
		caution_btlayer.add(bCaution);
		
		north_layer.setLayout(new GridLayout(1,3,3,3));
		north_layer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		north_layer.add(red.name);
		north_layer.add(vtime);
		north_layer.add(blue.name);
		
		util_layer.setLayout(new FlowLayout(FlowLayout.RIGHT));
		util_layer.add(tStart);
		util_layer.add(tPause);
		util_layer.add(init);
		
		south_layer.setLayout(new GridLayout(1,2));
		south_layer.add(caution_btlayer);
		south_layer.add(util_layer);
		
		layer.setLayout(new BorderLayout());
		layer.add("North",north_layer);
		layer.add("Center",bt_layer);
		layer.add("South",south_layer);
		
		add(layer);
		setVisible(true);
	}
	
	ActionListener button_listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {		
			JButton bt = (JButton)e.getSource();
				
			if(bt.equals(red.score_button)) {
				red.score++;
				red.score_button.setText(Integer.toString(red.score));
			}
			else if(bt.equals(blue.score_button)) {
				blue.score++;
				blue.score_button.setText(Integer.toString(blue.score));
			}
			else if(bt.equals(rCaution)) {
				red.caution++;
				blue.score++;
				blue.score_button.setText(Integer.toString(blue.score));
				red.caution_area.setVisible(false);
				red.caution_area.refresh(red.caution);
				red.caution_area.setVisible(true);
				if(red.caution == 10) {
					result.winner.setText(result.who_win_caution(red.caution, blue.caution));
					result.setVisible(true);
				}
			}
			else if(bt.equals(bCaution)) {
				blue.caution++;
				red.score++;
				red.score_button.setText(Integer.toString(red.score));
				blue.caution_area.setVisible(false);
				blue.caution_area.refresh(blue.caution);
				blue.caution_area.setVisible(true);
				if(blue.caution == 10) {
					result.winner.setText(result.who_win_caution(red.caution, blue.caution));
					result.setVisible(true);
				}
			}
			else if(bt.equals(init)){
				Initialize();
			}
			else if(bt.equals(tStart)) {
				((ThreadHandle) r).start();
			}
			else if(bt.getText().equals("ÀÏ½ÃÁ¤Áö")) {
				tPause.setText("°è¼Ó");
				((ThreadHandle) r).suspend();
			}
			else if(bt.getText().equals("°è¼Ó")) {
				tPause.setText("ÀÏ½ÃÁ¤Áö");
				((ThreadHandle) r).resume();
			}
		}
	};
	
	void Initialize() {
		red.name.setText("");
		blue.name.setText("");
		red.score = blue.score = red.caution = blue.caution = 0;
		red.score_button.setText(Integer.toString(red.score));
		blue.score_button.setText(Integer.toString(blue.score));
		red.caution_area.removeAll();
		blue.caution_area.removeAll();
		vtime.setText("1 : 30");
		tPause.setText("ÀÏ½ÃÁ¤Áö");
		threadClear();
		((ThreadHandle) r).stop();
		r = new Time(1,30);
		t = new Thread(tg, r);
	}
	
	class Time extends ThreadHandle {
		private int minute = 0;
		private int second = 0;
		private long ftime;
		
		Time(int min, int sec) {
			minute = min;
			second = sec;
		}
		
		public void run() {
			long stime = 0;
			long atime = 0, btime = System.currentTimeMillis();
			ftime = System.currentTimeMillis() + (minute * 60 + second) *1000;
			
			while (stime <= ftime){
	            while (stateCode == STATE_SUSPENDED){
	                try {
	                	btime = System.currentTimeMillis();
	                    Thread.sleep(60*60*1000);
	                } catch (InterruptedException e) {
	                    if (stateCode != STATE_SUSPENDED){
	                        atime = System.currentTimeMillis();
	                        ftime = ftime + atime - btime;
	                    	break;
	                    }
	                }
	            }
	            
	            if (stateCode == STATE_STOPPED){
	                break;
	            }
	            
				stime = System.currentTimeMillis();
				long dtime = ftime - stime;

				vtime.setText(get_min(dtime) + " : " + get_sec(dtime));
					
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					break;
				}
				
				if(get_min(dtime).equals("0") && get_sec(dtime).equals("00")) {
					score_result();
				}
	        }
		}
	}
	
	void score_result() {
		result.winner.setText(result.who_win_score(red.score, blue.score));
		result.setVisible(true);
		result.result_sound();
	}
	
	void threadClear() {
		Thread[] trds = new Thread[tg.activeCount()];
		int i = tg.enumerate(trds);
		for(int j=0; j<i; j++) {
			trds[j].stop();
		}
	}
	
	String get_min(long millisec) {
		int input = (int)((millisec / 60000) % 60);
		
		return Integer.toString(input);
	}
	
	String get_sec(long millisec) {
		int input = (int)((millisec / 1000) % 60);
		
		if(input < 10) {
			return "0" + Integer.toString(input);
		}
		else {
			return Integer.toString(input);
		}
	}
}

class ThreadHandle implements Runnable {
    private Thread thisThread ;
    private String threadName ;

    final static int STATE_INIT = 0x1;
    final static int STATE_STARTED = 0x1 << 1;
    final static int STATE_SUSPENDED = 0x1 << 2;
    final static int STATE_STOPPED = 0x1 << 3;
    volatile int stateCode = STATE_INIT;

    public ThreadHandle(){ }

    public ThreadHandle(String threadName){
        this.threadName = threadName;
    }
    
    public void start(){
        synchronized (this){
            if (stateCode != STATE_INIT)
                throw new IllegalStateException("already started");
            
            thisThread = new Thread(this);

            if ( threadName != null) thisThread.setName(threadName);

            thisThread.start();
            stateCode = STATE_STARTED;
        }
    }

    public void stop() {
        synchronized (this){
            if (stateCode == STATE_STOPPED || stateCode == STATE_INIT)
                throw new IllegalStateException("already stopped");
            
            this.stateCode = STATE_STOPPED;
            thisThread.interrupt();
        }
    }

    public void resume() {
        synchronized (this){
            if ( stateCode == STATE_STARTED || stateCode == STATE_INIT) return;
            if ( stateCode == STATE_STOPPED)
                throw new IllegalStateException("already stopped");
            
            stateCode = STATE_STARTED;
            thisThread.interrupt(); // ²À ÇØÁà¾ß ÇÑ´Ù.
        }
    }
    
    public void suspend() {
        synchronized (this){
            if ( stateCode == STATE_SUSPENDED) return;
            if ( stateCode == STATE_INIT )
                throw new IllegalStateException("not started yet");
            if ( stateCode == STATE_STOPPED)
                throw new IllegalStateException("already stopped");
            stateCode = STATE_SUSPENDED;
        }    
    }
    
    public void run() {
        while ( true ){
            while ( stateCode == STATE_SUSPENDED){
                try {
                    System.out.println("[handle] suspending...");
                    Thread.sleep(24 * 60 * 60 * 1000);
                } catch (InterruptedException e) {
                    if ( stateCode != STATE_SUSPENDED){
                        System.out.println("[handle] resuming...");
                        break;
                    }
                }
            }
            if ( stateCode == STATE_STOPPED){
                System.out.println("[handle] stopping...");
                break;
            }
        }
    }	
}