package Numeron;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class Numeron extends JFrame implements ActionListener{
	public static void main(String args[]) {
		new Numeron();
	}
	
	//初期化処理
	private int[] numbers = {0,1,2,3,4,5,6,7,8,9};
	private Integer[] comNum; 
	private int[] playerNum;
	private String[] player;
	private int digit;
	private ButtonGroup digits;
	private JTextField textField;
	private JButton start_btn, stop_btn, judge_btn, help_btn;
	private JLabel ans_msg, rule, rule_msg;
	
	//ラジオボタン作成
	private JRadioButton rb1 = new JRadioButton("3桁", true);
	private JRadioButton rb2 = new JRadioButton("４桁");
	private JRadioButton rb3 = new JRadioButton("5桁");
	
	Numeron(){
		//ゲーム画面全体の設定
		setTitle("Numeron");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,270);
		setResizable(false);
		
		//ゲーム画面表示の設定
		getContentPane().setLayout(null);
		start_btn = new JButton("Start");
		stop_btn = new JButton("Stop");
		textField = new JTextField(6);
		judge_btn = new JButton("Judge");
		ans_msg = new JLabel("桁数を決めて、「Start」を押してください");
		rule = new JLabel("= Rule =");
		rule_msg = new JLabel();
		rule_msg.setText("<html><body>・数字当てゲームです<br />・数字と桁が一致<br />　→　Eat<br />桁は不一致、数字は一致<br />　→　Bite<br />と表示されます<br />・結果の保存機能はないので、メモしてください");
		help_btn = new JButton("High&Low");
		digits = new ButtonGroup();
		digits.add(rb1);
		digits.add(rb2);
		digits.add(rb3);
		
		//座標でレイアウト setBounds(x,y,width,height)
		start_btn.setBounds(10,15,85,30);
		stop_btn.setBounds(95,15,85,30);
		help_btn.setBounds(180,15,100,30);
		rb1.setBounds(10,50,50,30);
		rb2.setBounds(85,50,50,30);
		rb3.setBounds(160,50,50,30);
		textField.setBounds(40,100,120,50);
		judge_btn.setBounds(160,100,85,50);
		ans_msg.setBounds(25,175,280,25);
		rule.setBounds(330,15,100,30);
		rule_msg.setBounds(285,20,140,180);
		
		//パネルに追加
		getContentPane().add(start_btn);
		getContentPane().add(stop_btn);
		getContentPane().add(rb1);
		getContentPane().add(rb2);
		getContentPane().add(rb3);
		getContentPane().add(textField);
		getContentPane().add(judge_btn);
		getContentPane().add(ans_msg);
		getContentPane().add(rule);
		getContentPane().add(rule_msg);
		getContentPane().add(help_btn);
		
		//ゲーム画面を表示
		setVisible(true);
		
		//リスナ設定
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
		judge_btn.addActionListener(this);
		help_btn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start_btn) {
			//桁数を決める
			if(rb1.isSelected()) {
				digit = 3;
			}else if(rb2.isSelected()) {
				digit = 4;
			}else if(rb3.isSelected()) {
				digit = 5;
			}
			ans_msg.setText(digit+"桁の数字を予測してください");
			//配列の長さ　決定
			comNum = new Integer[digit];
			player = new String[digit];
			playerNum = new int[digit];
			
			//comNum数字決め
			for(int i=0; i<digit; i++) {
				comNum[i] = new java.util.Random().nextInt(10);
				for(int j=i-1; j>=0; j--) {
					if(comNum[j]==comNum[i]) {
						comNum[i] = new java.util.Random().nextInt(10);
					}
				}
			}
			//comNum 確認用
			for(int i=0; i<comNum.length; i++) {
				System.out.print(comNum[i]);
			}
		}else if(e.getSource()==stop_btn) {
			ans_msg.setText("桁数を決めて、「Start」を押してください");
		}else if(e.getSource()==judge_btn) {
			//textFieldに入力された数字を取り出し、一文字ずつplayerに格納
			String data = textField.getText();
			player = data.split("");
			//取り出した数字の桁数があっているか確認、不一致ならばエラー表示
			if(player.length == digit) {
				//playerのString型をint型に変換して、playerNumに格納
				for(int i=0; i<player.length; i++) {
					playerNum[i] = Integer.parseInt(player[i]);
				}
				//judgeメソッド呼び出し
				judgeNum(comNum, playerNum);
			}else {
				JLabel err_msg = new JLabel("エラー："+digit+"桁の数字を入力してください"); 
				err_msg.setForeground(Color.RED);
				JOptionPane.showMessageDialog(this, err_msg);
			}
			//textFieldを空にする
			textField.setText("");
		}else if(e.getSource()==help_btn) {
			//helpNumメソッド呼び出し
			helpNum(comNum);
		}
	}
	
	//judgeNumメソッド
	public void judgeNum(Integer[] comNum, int[] playerNum) {
		//初期化
		int eat = 0;
		int bite = 0;
		//eat,bite判定
		for(int i=0; i<comNum.length; i++) {
			if(comNum[i]==playerNum[i]) {
				eat += 1;
			}else if(Arrays.asList(comNum).contains(playerNum[i])) {
				bite += 1;
			}
		}
		//returnの内容を決め、表示
		String clear = comNum.length+"EAT  Game Clear!　(^▽^)/";
		String no_clear = eat+"Eat"+bite+"Bite";
		if(eat==comNum.length) {
			//クリアはdialogで表示
			JOptionPane.showMessageDialog(this, clear);
		}else {
			//クリアでなければ、ans_msgで表示
			ans_msg.setText("result : "+Arrays.toString(playerNum)+" "+no_clear);
		}
	}
	
	//helpNumメソッド
	public void helpNum(Integer[] comNum0) {
		//5より大きければ「High」、小さければ「Low」とhelpに格納
		String[] help = new String[comNum.length];
		for(int i=0; i<comNum.length; i++) {
			if(comNum[i]>=5) {
				help[i] = "High";
			}else {
				help[i] = "Low";
			}
		}
		//ans_msgに表示
		ans_msg.setText("ヒント："+Arrays.toString(help));
	}
}
