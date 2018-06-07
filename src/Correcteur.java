import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Correcteur {
	JPopupMenu popup = new JPopupMenu();
	
	public void reset_popupMenu()
	{
		popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Remplacer");
		popup.add(menuItem);
		popup.addSeparator();
		menuItem = new JMenuItem("Ignorer");
		popup.add(menuItem);
		
	}
	public class MotFaux{
		public class PopupActionListener implements ActionListener {
			  public void actionPerformed(ActionEvent actionEvent) {
			    System.out.println("Selected: " + actionEvent.getActionCommand()+pos);
			    try {
			    	txt.remove(pos, motFaux.length());
					txt.insertString(pos, actionEvent.getActionCommand(), null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			}
		private int nb_max_match=5;
		private String motFaux="";
		private String matchs[]=new String[nb_max_match];
		private int pos=0;
		public MotFaux(String mFaux)
		{
			motFaux=mFaux;
		}

		public MotFaux(String mFaux,int max_mtch)
		{
			motFaux=mFaux;
			nb_max_match=max_mtch;
		}
		public MotFaux(int p,String mFaux)
		{
			motFaux=mFaux;
			pos=p;
		}
		public MotFaux(String mFaux,int max_mtch,int p)
		{
			motFaux=mFaux;
			nb_max_match=max_mtch;
			pos=p;
		}
		public void getMatchs()
		{
			int j=0;
			
			for(int i=0;i<dictionnaire.length;i++)
			{
				if(dictionnaire[i].contains(motFaux))
				{
					matchs[j]=dictionnaire[i];
					j++;
				}
				if(j>4)
				{
					for(int k=0;k<nb_max_match;k++)
					{
						System.out.println(matchs[k]+" bis");
						
					}
					break;
				}
			}
		}
		public void setMatch()
		{
			//textPane.remove(popup);
			popup = new JPopupMenu();
			JMenuItem menuItem = new JMenuItem("Remplacer");
			popup.add(menuItem);
			getMatchs();
			for(int i=0;i<nb_max_match;i++)
			{
				System.out.println(matchs[i]);
				menuItem = new JMenuItem(matchs[i]);
				menuItem.addActionListener(new PopupActionListener());
				popup.add(menuItem);
			}
			popup.addSeparator();
			menuItem = new JMenuItem("Ignorer");
			popup.add(menuItem);
			
		}
	}
	private JFrame frame;
	private String dictionnaire[]; 
	private int nb_max_match=5;
	String matchs[]=new String[nb_max_match];
	JTextPane textPane = new JTextPane();
	private ArrayList<MotFaux> tab_motfaux= new ArrayList<MotFaux>();
	Document txt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Correcteur window = new Correcteur();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Correcteur() {
		initialize();
	}
	public boolean inDictionnaire(String s)
	{
		for(int i=0;i<dictionnaire.length;i++)
		{
			if(dictionnaire[i].equals(s))
			{
				System.out.println(s+" trouvé");
				return true;
			}
			
			
		}
			System.out.println(s+" pas trouvé");
		return false;	
	}
	public void getMatchs(String mot)
	{
		int j=0;
		
		for(int i=0;i<dictionnaire.length;i++)
		{
			if(dictionnaire[i].contains(mot))
			{
				//System.out.println(dictionnaire[i]);
				matchs[j]=dictionnaire[i];
				j++;
			}
			if(j>4)
			{
				for(int k=0;k<nb_max_match;k++)
				{
					System.out.println(matchs[k]);
					
				}
				break;
			}
		}
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String pathFichier=this.getClass().getResource("/liste_francais.txt").toString().substring(5);
		//String pathFichier=this.getClass().getResource("test.txt").toString().substring(5);

		//String pathFichier="liste_francais.txt";
		File fichier=new File(pathFichier);
		FileInputStream IOdico=null;
		String dico= "";
		
		//byte[] buf = new byte[8];
		  
		  //byte val_é=(byte),val_è,val_û,val_ï,val_î
		  try {
			  IOdico=new FileInputStream(fichier);
			  byte[] buf = new byte[IOdico.available()];
			  IOdico.read(buf);
			  dico=new String(buf);
			  IOdico.close();
			  dictionnaire=dico.split("\\R");
			  
			  //for(int i=0;i<dictionnaire.length;i++)
				  //System.out.print(dictionnaire[i]);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println(dico);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		textPane.setBounds(50, 65, 200, 100);
		
		frame.getContentPane().add(textPane);
		
		txt=textPane.getDocument();
		txt.addDocumentListener(new DocumentListener() {
			private String mots_txt[];

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				textPane.getHighlighter().removeAllHighlights();
				int pos=0;
				if(textPane.getText().toCharArray()[0]!=' ')
				{
					mots_txt=textPane.getText().split(" ");
				}
				
				for(int i=0;i<mots_txt.length;i++)
				{
					if(!inDictionnaire(mots_txt[i])&&(textPane.getText().toCharArray()[textPane.getText().length()-1]==' '))
					{
						tab_motfaux.add(new MotFaux(pos,mots_txt[i]));
						try {
							textPane.getHighlighter().addHighlight(pos, pos + mots_txt[i].length(),DefaultHighlighter.DefaultPainter);
							tab_motfaux.get(tab_motfaux.size()-1).setMatch();
							getMatchs(mots_txt[i]);
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//textPane.setFont(new Font("Arial",Font.BOLD,10));
					}
					else
					{
						textPane.setFont(new Font("Arial",Font.PLAIN,10));
						reset_popupMenu();
					}
					pos+=mots_txt[i].length()+1;

						//textPane.setText(mots_txt[i]);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				textPane.getHighlighter().removeAllHighlights();
				int pos=0;
				if(textPane.getText().toCharArray()[0]!=' ')
				{
					mots_txt=textPane.getText().split(" ");
				}
				
				for(int i=0;i<mots_txt.length;i++)
				{
					if(!inDictionnaire(mots_txt[i])&&(textPane.getText().toCharArray()[textPane.getText().length()-1]==' '))
					{
						tab_motfaux.add(new MotFaux(pos,mots_txt[i]));
						try {
							textPane.getHighlighter().addHighlight(pos, pos + mots_txt[i].length(),DefaultHighlighter.DefaultPainter);
							tab_motfaux.get(tab_motfaux.size()-1).setMatch();
							getMatchs(mots_txt[i]);
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//textPane.setFont(new Font("Arial",Font.BOLD,10));
					}
					else
					{
						textPane.setFont(new Font("Arial",Font.PLAIN,10));
						reset_popupMenu();
					}
					pos+=mots_txt[i].length()+1;

						//textPane.setText(mots_txt[i]);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				textPane.getHighlighter().removeAllHighlights();
				int pos=0;
				if(textPane.getText().toCharArray().length>0)
				{
					mots_txt=textPane.getText().split(" ");
				}
				
				for(int i=0;i<mots_txt.length;i++)
				{
					if(!inDictionnaire(mots_txt[i])&&(textPane.getText().toCharArray().length>0))
					{
						tab_motfaux.add(new MotFaux(pos,mots_txt[i]));
						try {
							textPane.getHighlighter().addHighlight(pos, pos + mots_txt[i].length(),DefaultHighlighter.DefaultPainter);
							tab_motfaux.get(tab_motfaux.size()-1).setMatch();
							getMatchs(mots_txt[i]);
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//textPane.setFont(new Font("Arial",Font.BOLD,10));
					}
					else
					{
						textPane.setFont(new Font("Arial",Font.PLAIN,10));
						reset_popupMenu();
					}
					pos+=mots_txt[i].length()+1;

						//textPane.setText(mots_txt[i]);
				}
				
			}
			
		});
		/*
		JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Remplacer");
		popup.add(menuItem);
		
		
		for(int i=0;i<nb_max_match;i++)
		{
			System.out.println(matchs[i]);
			menuItem = new JMenuItem(matchs[i]);
			popup.add(menuItem);
		}
		popup.addSeparator();
		menuItem = new JMenuItem("Ignorer");
		popup.add(menuItem);
		*/
		//
		textPane.add(popup);
		MouseAdapter menu = new MouseAdapter() {
			   public void mouseReleased(MouseEvent e) {
			      if(SwingUtilities.isRightMouseButton(e))
			    	  popup.show(textPane, e.getX(), e.getY());
			   }
			};
		textPane.addMouseListener(menu);
		/*
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 68, 121, 23);
		comboBox.addItem("Bonjour");
		comboBox.addItem("Bon");
		comboBox.addItem("jour");
		frame.getContentPane().add(comboBox);
		*/
		
		
		
		
		
	}
}
