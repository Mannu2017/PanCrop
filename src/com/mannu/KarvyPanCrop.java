package com.mannu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.ImageStore;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class KarvyPanCrop extends JFrame {
	double w,h;
	JTable table;
	int dat;
	JLabel selectedAreaPanel,signa,re;
	String pdfpath;
	DefaultTableModel model;
	JButton bro,next,pri,auto,save;
	JTextField acknam,lotno;
	JRadioButton img;
	JRadioButton sig;
	String fnam;
	JLabel imgnam;
	JComboBox dtype;
	
    public KarvyPanCrop() {
        super("Karvy Pan Image Crop");
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowOpened(WindowEvent arg0) {
        		try {
					File ff=new File("C:\\pan\\");
					if(ff.exists()) {
						File[] listofFiles=ff.listFiles();
						for (int i = 0; i < listofFiles.length; i++) {
							File delf=listofFiles[i];
							delf.delete();
						//	System.out.println("fli "+delf);
						}
					}
				} catch (Exception e) {
					System.out.println("error: "+e);
				}
        	}
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(null);
        DrawRectanglePanel dr=new DrawRectanglePanel();
       
       Toolkit tk=Toolkit.getDefaultToolkit();
       w=tk.getDefaultToolkit().getScreenSize().getWidth();
       h=tk.getDefaultToolkit().getScreenSize().getHeight();
       JScrollPane imgscr=new JScrollPane();
       imgscr.setSize((int)w-550, (int)h-65);
       getContentPane().add(imgscr);
       imgscr.setViewportView(dr);
       
       JLabel scut=new JLabel("Shortcut Keys");
		scut.setBounds((int)w-200, 400, 200, 30);
		scut.setForeground(Color.BLUE);
		scut.setFont(new Font("Serif", Font.BOLD, 14));
		getContentPane().add(scut);
		
		JLabel ex=new JLabel("Browse: Alt+B");
		ex.setBounds((int)w-200, 440, 200, 30);
		ex.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(ex);
		
		JLabel nx=new JLabel("Next: Alt+N");
		nx.setBounds((int)w-200, 460, 200, 30);
		nx.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(nx);
		
		JLabel pr=new JLabel("Previous: Alt+P");
		pr.setBounds((int)w-200, 480, 200, 30);
		pr.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(pr);
		
//		JLabel au=new JLabel("Auto: Alt+A");
//		au.setBounds((int)w-200, 500, 200, 30);
//		au.setFont(new Font("Arial", Font.BOLD, 12));
//		getContentPane().add(au);
		
		JLabel sv=new JLabel("Save: Alt+S");
		sv.setBounds((int)w-200, 500, 200, 30);
		sv.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(sv);
		
		JLabel iq=new JLabel("Image Crop: Alt+I");
		iq.setBounds((int)w-200, 520, 200, 30);
		iq.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(iq);
		
		JLabel sq=new JLabel("Signature Crop: Alt+Q");
		sq.setBounds((int)w-200, 540, 200, 30);
		sq.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(sq);
		
		JScrollPane fnPane=new JScrollPane();
		fnPane.setBounds((int)w-540, 400, 300, 300);
		getContentPane().add(fnPane);
       
		table=new JTable();
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				File tt=new File("C:\\pan\\");
//					if (!tt.exists()) {
//						tt.mkdir();
//					}
//					acknam.setText(null);
//					img.isSelected();
//				dat = table.getSelectedRow();
//				
//           	// System.out.println("Row: "+dat);
//           	 if (dat == -1) {
//		    			selectedAreaPanel.setText("No File Name Select");
//		    		}else if(dat >= 0){
//		    		try {
//		    			PDDocument document = PDDocument.load(new File(pdfpath+"//"+model.getValueAt(dat, 0)));
//		    			PDFRenderer pdfRenderer = new PDFRenderer(document);
//		    			String[] valArray = model.getValueAt(dat, 0).toString().split("_");
//		    			if(valArray.length>1) {
//		    				fnam=valArray[1];
//		    			}else {
//		    				fnam=valArray[0];
//		    			}
//		    			fnam=fnam.replaceFirst("[.][^.]+$", "");
//		    			acknam.setText(fnam);
//		    			for (int i = 1; i < 4; i++) {
//		    				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
//		  		            ImageIOUtil.writeImage(bim,tt+"\\"+fnam+i+".png",1000);
//						}
//		    			document.close();
//		    			dr.setIcon(new ImageIcon(tt+"//"+fnam+"1.png"));
//		    			
//					} catch (Exception e3) {
//						e3.printStackTrace();
//					}
//		    	}
//			}
//		});
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"File Name"
				}
			));
		fnPane.setViewportView(table);
		
		bro=new JButton("Browse");
		bro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lotno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Lot No","Warning",JOptionPane.WARNING_MESSAGE);
				} else {
					File tt=new File("C:\\pan\\");
					if (!tt.exists()) {
						tt.mkdir();
					}
				
				model = (DefaultTableModel)table.getModel();
				model.setRowCount(0);
				JFileChooser fileChooser=new JFileChooser();
		        fileChooser.setDialogTitle("Select File Path");
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        if (fileChooser.showOpenDialog(fileChooser)==JFileChooser.APPROVE_OPTION) {
		        	File selectedFile = fileChooser.getSelectedFile();
		            pdfpath=selectedFile.getAbsolutePath();
		            File folder=new File(pdfpath);
		           if (dtype.getSelectedItem().equals("PDF")) {
		        	    File[] listofFiles=folder.listFiles();
			            Object[] row = new Object[1];
			            for (int i = 0; i < listofFiles.length; i++) {
			            	 if (listofFiles[i].getName().endsWith(".PDF")) {
									
				            		 row[0]=listofFiles[i].getName();
				                        model.addRow(row);
								}else if (listofFiles[i].getName().endsWith(".pdf")) {
									row[0]=listofFiles[i].getName();
			                        model.addRow(row);
								}
			            	 }
			            	 table.getSelectionModel().setSelectionInterval(0, 0);
			            	 dat = table.getSelectedRow();
			            //	 System.out.println("Row: "+dat);
			            	 if (dat == -1) {
					    			selectedAreaPanel.setText("No File Name Select");
					    		}else if(dat >= 0){
					    		try {
					    			PDDocument document = PDDocument.load(new File(pdfpath+"//"+model.getValueAt(dat, 0)));
					    			PDFRenderer pdfRenderer = new PDFRenderer(document);
					    			String[] valArray = model.getValueAt(dat, 0).toString().split("_");
					    			if(valArray.length>1) {
					    				fnam=valArray[1];
					    			}else {
					    				fnam=valArray[0];
					    			}
					    			fnam=fnam.replaceFirst("[.][^.]+$", "");
					    			acknam.setText(fnam);
					    			for (int i = 1; i < 4; i++) {
					    				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
					  		            ImageIOUtil.writeImage(bim,tt+"\\"+fnam+i+".png",1000);
									}
					    			document.close();
					    			dr.setIcon(new ImageIcon(tt+"//"+fnam+"1.png"));
					    			imgnam.setText(fnam+"1");
								} catch (Exception e) {
									e.printStackTrace();
								}
					    	}
		        	   
				} else if (dtype.getSelectedItem().equals("JPG")){
					 File[] listofFiles=folder.listFiles();
			            Object[] row = new Object[1];
					for (int i = 0; i < listofFiles.length; i++) {
		            	 if (listofFiles[i].getName().endsWith(".jpg") || listofFiles[i].getName().endsWith(".JPG") || listofFiles[i].getName().endsWith(".Jpg")) {
			            		 row[0]=listofFiles[i].getName();
			                        model.addRow(row);
							}
		            	 }
						table.getSelectionModel().setSelectionInterval(0, 0);
						dat = table.getSelectedRow();
						 if (dat == -1) {
				    			selectedAreaPanel.setText("No File Name Select");
				    		}else if(dat >= 0){
				    			String[] valArray = model.getValueAt(dat, 0).toString().split("_");
				    			if(valArray.length>1) {
				    				fnam=valArray[1];
				    			}else {
				    				fnam=valArray[0];
				    			}
				    			
				    			fnam=fnam.replaceFirst("[.][^.]+$", "");
				    			acknam.setText(fnam);
				    			Document doc=new Document(PageSize.A4, 0, 0, 0, 0);
				    			try {
				    				PdfWriter.getInstance(doc, new FileOutputStream("C:\\pan\\"+fnam+".pdf"));
				    				doc.open();
				    				com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance(pdfpath+"//"+model.getValueAt(dat, 0));
				    				doc.add(img);
				    		        doc.close();
				    		        
				    		        PDDocument document = PDDocument.load(new File("C:\\pan\\"+fnam+".pdf"));
					    			PDFRenderer pdfRenderer = new PDFRenderer(document);
					    			
					    			for (int i = 0; i < document.getNumberOfPages(); i++) {
					    				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 200, ImageType.RGB);
					  		            ImageIOUtil.writeImage(bim,"C:\\pan\\"+fnam+i+".png",1000);
									}
					    			document.close();
					    			dr.setIcon(new ImageIcon("C:\\pan\\"+fnam+0+".png"));
					    			
								} catch (FileNotFoundException | DocumentException e) {
									e.printStackTrace();
								} catch (MalformedURLException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
				    			
				    		}
	            	 
						}
		            }
				}
		      }
		});
		bro.setBounds((int)w-540, 350, 90, 30);
		bro.setMnemonic(KeyEvent.VK_B);
		getContentPane().add(bro);
		
		next=new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String lno=imgnam.getText().substring(imgnam.getText().length()-1);
				int nn=Integer.parseInt(lno)+1;
				
				String nexnam="C:\\pan\\"+fnam+nn+".png";
				File nexfil=new File(nexnam);
			//	System.out.println("Next Path: "+nexnam);
				
				if (!nexfil.exists()) {
					JOptionPane.showMessageDialog(null, "This is Last Page");
				} else if (nexfil.exists()) {
					dr.setIcon(new ImageIcon(nexnam));
					imgnam.setText(fnam+nn);
				}
				
			}
		});
		next.setBounds((int)w-340, 350, 90, 30);
		next.setMnemonic(KeyEvent.VK_N);
		getContentPane().add(next);
		
		pri=new JButton("Previous");
		pri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String lno=imgnam.getText().substring(imgnam.getText().length()-1);
				int nn=Integer.parseInt(lno)-1;
				
				String nexnam="C:\\pan\\"+fnam+nn+".png";
				File nexfil=new File(nexnam);
			//	System.out.println("Next Path: "+nexnam);
				
				if (!nexfil.exists()) {
					JOptionPane.showMessageDialog(null, "This is First Page");
				} else if (nexfil.exists()) {
					dr.setIcon(new ImageIcon(nexnam));
					imgnam.setText(fnam+nn);
				}
			}
		});
		
		pri.setBounds((int)w-440, 350, 90, 30);
		pri.setMnemonic(KeyEvent.VK_P);
		getContentPane().add(pri);
		
		save=new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File afile =new File(pdfpath+"\\Done\\");
				if(!afile.exists()) {
					afile.mkdir();
				}
				File orgf=new File(pdfpath+"\\"+table.getValueAt(dat, 0));
				orgf.renameTo(new File(afile+"\\"+table.getValueAt(dat, 0)));
			//	System.out.println("Rename "+orgf);
				model=(DefaultTableModel) table.getModel();
				model.removeRow(dat);
				signa.setIcon(null);
				selectedAreaPanel.setIcon(null);
				img.setSelected(true);
				File tt=new File("C:\\pan\\");
				if (!tt.exists()) {
					tt.mkdir();
				}
			table.getSelectionModel().setSelectionInterval(0, 0);
           	 dat = table.getSelectedRow();
         	 System.out.println("Row: "+dat);
           	 if (dat == -1) {
		    			selectedAreaPanel.setText("No File Name Select");
		    		}else if(dat >= 0){
           	 File nameoffile=new File((String) model.getValueAt(dat, 0));
           	 if (nameoffile.getName().endsWith(".PDF") || nameoffile.getName().endsWith(".pdf")) {
				
           		try {
	    			PDDocument document = PDDocument.load(new File(pdfpath+"//"+model.getValueAt(dat, 0)));
	    			PDFRenderer pdfRenderer = new PDFRenderer(document);
	    			String[] valArray = model.getValueAt(dat, 0).toString().split("_");
	    	//		System.out.println("Save: "+valArray.length);
	    			if(valArray.length>1) {
	    				fnam=valArray[1];
	    			}else {
						fnam=valArray[0];
					}			
	    			fnam=fnam.replaceFirst("[.][^.]+$", "");
	    			acknam.setText(fnam);
	    			for (int i = 1; i < 4; i++) {
	    				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
	  		            ImageIOUtil.writeImage(bim,tt+"\\"+fnam+i+".png",1000);
					}
	    			document.close();
	    			dr.setIcon(new ImageIcon(tt+"//"+fnam+"1.png"));
	    			imgnam.setText(fnam+"1");
				} catch (Exception e) {
					e.printStackTrace();
				}
           		 
			} else if (nameoffile.getName().endsWith(".JPG") || nameoffile.getName().endsWith(".jpg") || nameoffile.getName().endsWith(".Jpg")) {
				String[] valArray = model.getValueAt(dat, 0).toString().split("_");
    			if(valArray.length>1) {
    				fnam=valArray[1];
    			}else {
    				fnam=valArray[0];
    			}
    			fnam=fnam.replaceFirst("[.][^.]+$", "");
    			acknam.setText(fnam);
    			Document doc=new Document(PageSize.A4, 0, 0, 0, 0);
    			try {
    				PdfWriter.getInstance(doc, new FileOutputStream("C:\\pan\\"+fnam+".pdf"));
    				doc.open();
    				com.itextpdf.text.Image img=com.itextpdf.text.Image.getInstance(pdfpath+"//"+model.getValueAt(dat, 0));
    				doc.add(img);
    		        doc.close();
    		        
    		        PDDocument document = PDDocument.load(new File("C:\\pan\\"+fnam+".pdf"));
	    			PDFRenderer pdfRenderer = new PDFRenderer(document);
	    			
	    			for (int i = 0; i < document.getNumberOfPages(); i++) {
	    				BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
	  		            ImageIOUtil.writeImage(bim,"C:\\pan\\"+fnam+i+".png",1000);
					}
	    			document.close();
	    			dr.setIcon(new ImageIcon("C:\\pan\\"+fnam+0+".png"));
	    			
				} catch (FileNotFoundException | DocumentException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
			}
           	 		
		   }
			}
		});
		save.setBounds((int)w-240, 350, 90, 30);
		save.setMnemonic(KeyEvent.VK_S);
		getContentPane().add(save);
		
		img=new JRadioButton("Image Crop");
		img.setSelected(true);
		img.setBounds((int)w-440, 240, 90, 30);
		img.setMnemonic(KeyEvent.VK_I);
		getContentPane().add(img);
		
		sig=new JRadioButton("Signature Crop");
		sig.setMnemonic(KeyEvent.VK_Q);
		sig.setBounds((int)w-340, 240, 120, 30);
		
		getContentPane().add(sig);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(img);
		bg.add(sig);
		
		JLabel acnam=new JLabel("ACK No: ");
		acnam.setBounds((int)w-500, 280, 80, 20);
		getContentPane().add(acnam);
		
		acknam=new JTextField();
		acknam.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (acknam.getText().length() == 20)) {
		          event.consume();
		        }
			}
		});
		acknam.setBounds((int)w-440, 280, 180, 20);
		getContentPane().add(acknam);
		
		JLabel lot=new JLabel("Lot No: ");
		lot.setBounds((int)w-500, 310, 80, 20);
		getContentPane().add(lot);
		
		lotno=new JTextField();
		lotno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent event) {
				char vchar = event.getKeyChar();
		        if ((!Character.isDigit(vchar)) || (vchar == '\b') || (vchar == '') || 
		          (lotno.getText().length() == 20)) {
		          event.consume();
		        }
			}
		});
		lotno.setBounds((int)w-440, 310, 180, 20);
		getContentPane().add(lotno);
		
		selectedAreaPanel = new JLabel();
		selectedAreaPanel.setBounds((int)w-540, 20, 200, 212);
		getContentPane().add(selectedAreaPanel);
		
		imgscr=new JScrollPane();
		imgscr.setSize((int)w-550, (int)h-65);
		getContentPane().add(imgscr);
		
		signa = new JLabel("");
		signa.setBounds((int)w-330, 20, 222, 51);
		getContentPane().add(signa);
		
		re = new JLabel("");
		re.setBounds((int)w-540, 243, 390, 20);
		getContentPane().add(re);
		
		imgnam = new JLabel();
		imgnam.setFont(new Font("Arial", Font.BOLD, 12));
		imgnam.setBounds((int)w-200, 581, 200, 30);
		getContentPane().add(imgnam);
		
		JLabel lblDataType = new JLabel("Data Type:");
		lblDataType.setBounds(1116, 283, 80, 14);
		getContentPane().add(lblDataType);
		
		dtype = new JComboBox();
		dtype.setModel(new DefaultComboBoxModel(new String[] {"PDF", "JPG"}));
		dtype.setBounds(1192, 280, 73, 20);
		getContentPane().add(dtype);
		
        setVisible(true); 
    }
    
private class DrawRectanglePanel extends JLabel implements MouseListener,
            MouseMotionListener {
         int x1;
         int y1;
         int x2;
         int y2;
         int a2;
         int k2;
        public DrawRectanglePanel() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }
       
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.drawRect(x1, y1, x2, y2);   
        }
       public void mousePressed(MouseEvent e) {
    	   if(getIcon()==null) {
    		   JOptionPane.showMessageDialog(null, "Please Select PDF File");
    	   }
    	   if (acknam.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please Enter ACK Number");
		} else {
			x1 = e.getX();
            y1 = e.getY();
            x2 = 0;
            y2 = 0;
            a2 = 0;
            k2 = 0;
		} 
        }

        public void mouseReleased(MouseEvent e) {
        	try {
        		if (acknam.getText().isEmpty()) {
        			JOptionPane.showMessageDialog(null, "Please Enter ACK Number");
        		} else {
        			if (img.isSelected()) {
        				File pstr=new File("C:\\karvy\\"+lotno.getText()+"\\"+lotno.getText()+"_Photo\\");
					if (!pstr.exists()) {
						pstr.mkdirs();
					}
						File ph=new File(pstr+"\\"+acknam.getText()+"_Photo.jpg");
						String crimg=getIcon().toString();
						BufferedImage scr=ImageIO.read(new File(crimg));
						BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
						System.out.println("DD: "+x1+" "+y1+" "+x2+" "+y2);
						System.out.println("L: "+scr.getWidth());
						ImageIO.write(phocro, "jpg", ph);
						
						ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Photo.jpg");
						Image image=icon.getImage();
						Image image2=image.getScaledInstance(selectedAreaPanel.getWidth(), selectedAreaPanel.getHeight(), Image.SCALE_SMOOTH);
						selectedAreaPanel.setIcon(new ImageIcon(image2));
						
			            BufferedImage img = null;
			            BufferedImage tempJPG = null;
			            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
			            tempJPG = resizeImage(img, 204, 204);
			            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
			            
			            BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
						FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Photo.jpg"));
						JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
						JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
						jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
			            jpegEncodeParam.setXDensity(200);
			            jpegEncodeParam.setYDensity(200);
			            jpegEncoder.encode(im, jpegEncodeParam);
			            fos.close();
			            
						
        			} else if(sig.isSelected()) {
        				File pstr=new File("C:\\karvy\\"+lotno.getText()+"\\"+lotno.getText()+"_Sig\\");
					if (!pstr.exists()) {
						pstr.mkdirs();
					}
						File si=new File(pstr+"\\"+acknam.getText()+"_Sig.jpg");
						String crimg=getIcon().toString();
						BufferedImage scr=ImageIO.read(new File(crimg));
						BufferedImage phocro=scr.getSubimage(x1, y1, x2, y2);
						ImageIO.write(phocro, "jpg", si);
						ImageIcon icon=new ImageIcon(pstr+"\\"+acknam.getText()+"_Sig.jpg");
						Image image=icon.getImage();
						Image image2=image.getScaledInstance(signa.getWidth(), signa.getHeight(), Image.SCALE_SMOOTH);
						signa.setIcon(new ImageIcon(image2));
						
						 BufferedImage img = null;
				            BufferedImage tempJPG = null;
				            img = ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
				            tempJPG = resizeImage(img, 333, 137);
				            ImageIO.write(tempJPG, "jpg", new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
						
						BufferedImage im=ImageIO.read(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
						FileOutputStream fos = fos = new FileOutputStream(new File(pstr+"\\"+acknam.getText()+"_Sig.jpg"));
						JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
						JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
						jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
			            jpegEncodeParam.setXDensity(200);
			            jpegEncodeParam.setYDensity(200);
			            //jpegEncodeParam.setQuality(1.0f, true);
			            jpegEncoder.encode(im, jpegEncodeParam);
			            fos.close();
				}
			}
        	} catch (Exception e2) {
				System.out.println(e2);
			}
        }

        public BufferedImage resizeImage(final Image image, int i, int j) {
			final BufferedImage bufferedImage = new BufferedImage(i, j, BufferedImage.TYPE_INT_RGB);
		    final Graphics2D graphics2D = bufferedImage.createGraphics();
		    graphics2D.setComposite(AlphaComposite.Src);
		    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		    graphics2D.drawImage(image, 0, 0, i, j, null);
		    graphics2D.dispose();
			
			return bufferedImage;
		}

		public void mouseDragged(MouseEvent e) {
            x2 = e.getX() - x1;
            y2 = e.getY() - y1;
            a2=240+x1;
            k2=240+y1;
            
            repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    }

    public static void main(String[] args) {
        new KarvyPanCrop();
    }
};
